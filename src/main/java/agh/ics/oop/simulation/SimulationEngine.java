package agh.ics.oop.simulation;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.CustomHashMap;
import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.MoveDirection;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.AnimalStatsTracker;
import agh.ics.oop.gui.StatsChartManager;
import agh.ics.oop.objects.*;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;

public class SimulationEngine implements IEngine, Runnable {
    private ArrayList<MoveDirection> moveArray = new ArrayList<>();
    private final ArrayList<IMapElement> animals;
    private final AbstractWorldMap map;
    private ArrayList<IMapChangeObserver> observers = new ArrayList<>();
    private final Integer moveDelay;
    private boolean isSuspended = false;
    private int epoch = 0;
    private final int plantEnergy;
    private final int moveCost;
    private final int startEnergy;
    private final boolean isMagical;
    private int magicSpawns = 0;
    private final StatsChartManager statsChartManager;
    private final AnimalStatsTracker animalStatsTracker;
    public SimulationEngine(AbstractWorldMap map, int startAnimals, Integer moveDelay,int startGrass, int startEnergy,
                            int moveCost, int plantEnergy, boolean isMagical, StatsChartManager statsChartManager,
                            AnimalStatsTracker animalStatsTracker) {
        this.moveDelay = moveDelay;
        this.animals = new ArrayList<>();
        this.map = map;
        this.plantEnergy = plantEnergy;
        this.moveCost = moveCost;
        this.startEnergy = startEnergy;
        this.statsChartManager = statsChartManager;
        this.animalStatsTracker = animalStatsTracker;
        this.isMagical = isMagical;

        for (int i = 0; i < startAnimals; i++) {
            Animal newAnimal  = generateAnimalAtRandomPos(map, startEnergy, moveCost, null, null, 0, null);
            if (newAnimal != null){
                statsChartManager.readDataOnAnimalBirth(newAnimal);
                animals.add(newAnimal);
                map.place(newAnimal);
            }
        }
//        Add the initial grass to the map and increment the grass counter
        statsChartManager.addPlantCount(map.placeGrassSteppe(startGrass/2));
        statsChartManager.addPlantCount(map.placeGrassJungle(startGrass/2));
    }

    @Override
    public void run() {
        int animalIndex = 0;
        int movesCnt = 1;

        while(true) {
            statsChartManager.resetEpochStats();

            if (animals.size() > 0 && movesCnt % animals.size() == 0) {
                animalsDie(epoch);
                checkMagical();
            }

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
                statsChartManager.addPlantCount(map.placeGrassJungle(1));
                statsChartManager.addPlantCount(map.placeGrassSteppe(1));

//                Read data from alive animals
                for (IMapElement element: animals) {
                    statsChartManager.readAliveAnimalData((Animal) element);
                }
                statsChartManager.chartUpdate(epoch);
                epoch += 1;
                try {
                    Thread.sleep(moveDelay);
                    for (IMapChangeObserver observer : observers) {
                        observer.mapChanged();
                    }
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
            ((Animal) animal).die(epoch);
            animals.remove(animal);
            statsChartManager.readDataOnAnimalDeath((Animal) animal, epoch);
            animalStatsTracker.updateOnDeath((Animal) animal, epoch);
        }
    }

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
                    statsChartManager.addPlantCount(-1);
                }
            }
        }
        mapElements.removeAll(keysToRemove, grassToRemove);
    }

    public void animalsReproduce() {
        ArrayList<Vector2d> newAnimalsPositions = new ArrayList<>();
        ArrayList<IMapElement> newAnimals = new ArrayList<>();
        CustomHashMap mapElements = map.getMapElements();
        for (Vector2d key : mapElements.keySet()) {
            ArrayList<Animal> twoStrongest = mapElements.getTwoStrongest(key);
//            TODO: Zmienic tooooooooo
            if (twoStrongest.get(1) == null || twoStrongest.get(1).getEnergy() < startEnergy / 1.3)
                continue;
            newAnimalsPositions.add(key);
//            Energia -1 oznacza aby podkraść energię rodzicom
            Animal newAnimal = new Animal(map, key, startEnergy, moveCost, twoStrongest.get(0), twoStrongest.get(1), epoch, null);
            statsChartManager.readDataOnAnimalBirth(newAnimal);
            animalStatsTracker.updateOnNewborn(twoStrongest.get(0), twoStrongest.get(1), newAnimal);
            newAnimals.add(newAnimal);
        }
        for (IMapElement animal : newAnimals) {
            animals.add(animal);
            map.place((Animal) animal);
        }
    }
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
                statsChartManager.readDataOnAnimalBirth( newAnimal);
                animals.add(newAnimal);
                map.place( newAnimal);
            }
            magicSpawns += 1;
        }
    }
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

    public void setMoveArray(ArrayList<MoveDirection> moveArray) {
        this.moveArray = moveArray;
    }

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
                    if (!map.isOccupiedByAnimal(new Vector2d(i,j)));{
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
}

