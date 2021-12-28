package agh.ics.oop.simulation;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.CustomHashMap;
import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.objects.*;

import java.util.ArrayList;

// Class that runs a simulation
public class SimulationEngine implements Runnable {
//    Define properties
    private final ArrayList<IMapElement> animals;
    private final AbstractWorldMap map;
    private final ArrayList<IMapChangeObserver> observers = new ArrayList<>();
    private final Integer moveDelay;
    private boolean isSuspended = false;
    private int epoch = 0;
    private final int plantEnergy;
    private final int moveCost;
    private final int startEnergy;
    private final boolean isMagical;
    private int magicSpawns = 0;
    private final StatsManager statsManager;
    private final AnimalStatsTracker animalStatsTracker;

//    Sets up the engine
    public SimulationEngine(AbstractWorldMap map, int startAnimals, Integer moveDelay,int startGrass, int startEnergy,
                            int moveCost, int plantEnergy, boolean isMagical, StatsManager statsManager,
                            AnimalStatsTracker animalStatsTracker) {
        this.moveDelay = moveDelay;
        this.animals = new ArrayList<>();
        this.map = map;
        this.plantEnergy = plantEnergy;
        this.moveCost = moveCost;
        this.startEnergy = startEnergy;
        this.statsManager = statsManager;
        this.animalStatsTracker = animalStatsTracker;
        this.isMagical = isMagical;

//        Places animals at random non-colliding positions
        for (int i = 0; i < startAnimals; i++) {
            Animal newAnimal  = generateAnimalAtRandomPos(map, startEnergy, moveCost, null, null, 0, null);
            if (newAnimal != null){
                statsManager.readDataOnAnimalBirth(newAnimal);
                animals.add(newAnimal);
                map.place(newAnimal);
            }
        }
        //        Add the initial grass to the map and increment the grass counter
        statsManager.addPlantCount(map.placeGrassSteppe(startGrass/2));
        statsManager.addPlantCount(map.placeGrassJungle(startGrass/2));


    }


    @Override
    public void run() {
        int animalIndex = 0;
        int movesCnt = 1;
//      Runs the simulation in an infinite loop
        while(true) {
//            Reads the data for stats manager
            statsManager.resetEpochStats();
//              Murders the animals with less than 5 energy, checks for magical condition
            if (animals.size() > 0 && movesCnt % animals.size() == 0) {
                animalsDie(epoch);
                checkMagical();
            }
//              Moves the alive animals
            if (animals.size() > 0) {
                movesCnt += 1;
                ((Animal) animals.get(animalIndex % animals.size())).genotypeMove();
                animalIndex = (animalIndex + 1) % animals.size();
            }

            // What to do on epoch update:
            if (animals.size() == 0 || movesCnt % animals.size() == 0) {
                if (animals.size() > 0) {
                    animalsEat();
                    animalsReproduce();
                    checkMagical();
                }
//                Place the grass and add to the counter
                statsManager.addPlantCount(map.placeGrassJungle(1));
                statsManager.addPlantCount(map.placeGrassSteppe(1));

//                Read data from alive animals
                for (IMapElement element: animals) {
                    statsManager.readAliveAnimalData((Animal) element);
                }
                statsManager.chartUpdate(epoch);
                epoch += 1;
                try {
                    Thread.sleep(moveDelay);
                    for (IMapChangeObserver observer : observers) {
                        observer.mapChanged();
                    }
//                    Implements a pause for the engine
                    synchronized (this){
                        while (isSuspended){
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

//    Method to murder all the animals that we decide are not worthy of living anymore
    public void animalsDie(int epoch) {
        ArrayList<Vector2d> keysToRemove = new ArrayList<>();
        ArrayList<IMapElement> animalsToRemove = new ArrayList<>();
        for (IMapElement element : animals) {
            if (element instanceof Animal && ((Animal) element).getEnergy() <= 0) {
                keysToRemove.add(element.getPosition());
                animalsToRemove.add(element);
            }
        }
        map.getMapElements().removeAll(keysToRemove, animalsToRemove);
        for (IMapElement animal : animalsToRemove) {
            animals.remove(animal);
            statsManager.readDataOnAnimalDeath((Animal) animal, epoch);
            animalStatsTracker.updateOnDeath((Animal) animal, epoch);
        }
    }
//    Feed the animals that are worthy of eating (Have the most energy on a provided field with a tuft of grass)
    public void animalsEat() {
        ArrayList<Vector2d> keysToRemove = new ArrayList<>();
        ArrayList<IMapElement> grassToRemove = new ArrayList<>();
        CustomHashMap mapElements = map.getMapElements();
        for (Vector2d key : mapElements.keySet()) {
            Grass grassAtKey = mapElements.grassAt(key);
            if (grassAtKey != null) {
                ArrayList<Animal> animals = mapElements.getStrongest(key);
                for (Animal animal : animals) {
                    animal.addEnergy(plantEnergy / animals.size());
                }
                if (animals.size() > 0) {
                    keysToRemove.add(key);
                    grassToRemove.add(grassAtKey);
                    statsManager.addPlantCount(-1);
                }
            }
        }
        mapElements.removeAll(keysToRemove, grassToRemove);
    }
//  If two anials with enough energy meet, they reproduce
    public void animalsReproduce() {
        ArrayList<Vector2d> newAnimalsPositions = new ArrayList<>();
        ArrayList<IMapElement> newAnimals = new ArrayList<>();
        CustomHashMap mapElements = map.getMapElements();
        for (Vector2d key : mapElements.keySet()) {
            ArrayList<Animal> twoStrongest = mapElements.getTwoStrongest(key);
            if (twoStrongest.get(1) == null || twoStrongest.get(1).getEnergy() <= startEnergy / 2)
                continue;
            newAnimalsPositions.add(key);
            Animal newAnimal = new Animal(map, key, startEnergy, moveCost, twoStrongest.get(0), twoStrongest.get(1), epoch, null);
            statsManager.readDataOnAnimalBirth(newAnimal);
            animalStatsTracker.updateOnNewborn(twoStrongest.get(0), twoStrongest.get(1), newAnimal);
            newAnimals.add(newAnimal);
        }
//        Add new animals to the simulation
        for (IMapElement animal : newAnimals) {
            animals.add(animal);
            map.place((Animal) animal);
        }
    }

//    Checks for the magical condition and executes it
    public void checkMagical(){
        if (animals.size() == 5 && isMagical && magicSpawns < 3){
            ArrayList<Animal> newAnimals = new ArrayList<>();
            for (IMapElement obj: animals) {
                Animal animal = (Animal) obj;
                Animal newAnimal = generateAnimalAtRandomPos(map,startEnergy, moveCost, animal.getFather(), animal.getMother(), epoch, animal.getGenome());
                if (newAnimal != null){
                    newAnimals.add(newAnimal);
                }
            }
            for (Animal newAnimal: newAnimals) {
                statsManager.readDataOnAnimalBirth( newAnimal);
                animals.add(newAnimal);
                map.place( newAnimal);
            }
            magicSpawns += 1;
        }
    }

//    Methods for managing the pause and continue button
    public void suspend() {
        this.isSuspended = true;
    }
    public boolean isSuspended() {
        return isSuspended;
    }


    public void resume() {
        synchronized (this){
            notify();
        }
        this.isSuspended = false;
    }

    public void addObserver(IMapChangeObserver observer) {
        observers.add(observer);
    }

//  Generates an animal with provided parameters on a provided position
    private Animal generateAnimalAtRandomPos(AbstractWorldMap map, int startEnergy, int moveCost, Animal father,
                                             Animal mother, int birthEpoch, Genome genome){
        int x = Utils.getRandomNumber(0, map.getWidth());
        int y = Utils.getRandomNumber(0, map.getHeight());
        int tryCounter = 0;
//        Try to get a random unoccupied position 15 times
        while (map.isOccupiedByAnimal(new Vector2d(x, y)) && tryCounter < 15){
            x = Utils.getRandomNumber(0, map.getWidth());
            y = Utils.getRandomNumber(0, map.getHeight());
            tryCounter++;
        }
//        If failed to find one get the first free postition
        if (tryCounter == 15){
            for (int i = 0; i < map.getHeight(); i++) {
                for (int j = 0; j < map.getWidth(); j++) {
                    if (!map.isOccupiedByAnimal(new Vector2d(i,j))){
                        return new Animal(map, new Vector2d(j, i), startEnergy, moveCost, father, mother, birthEpoch, genome);
                    }
                }
            }
        }else
            return new Animal(map, new Vector2d(x, y), startEnergy, moveCost, father, mother, birthEpoch, genome);
        return null;
    }
    public int getMagicSpawns(){
        return magicSpawns;
    }
    public boolean isMagical(){
        return isMagical;
    }
    public ArrayList<IMapElement> getAnimals(){return animals;}
}

