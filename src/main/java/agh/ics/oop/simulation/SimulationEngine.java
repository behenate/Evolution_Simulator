package agh.ics.oop.simulation;

import agh.ics.oop.dataTypes.CustomHashMap;
import agh.ics.oop.dataTypes.MoveDirection;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.AnimalStatsTracker;
import agh.ics.oop.gui.StatsChartManager;
import agh.ics.oop.objects.*;

import java.util.ArrayList;

public class SimulationEngine implements IEngine, Runnable {
    private ArrayList<MoveDirection> moveArray = new ArrayList<>();
    private final ArrayList<IMapElement> animals;
    private final AbstractWorldMap map;
    private ArrayList<IMapChangeObserver> observers = new ArrayList<>();
    private final Integer moveDelay;
    private boolean isSuspended = false;
    private int epoch = 0;
    private int plantEnergy;
    private int moveCost;
    private int startEnergy;
    private StatsChartManager statsChartManager;
    private AnimalStatsTracker animalStatsTracker;
    public SimulationEngine(AbstractWorldMap map, Vector2d[] initialPositions, Integer moveDelay, int startEnergy,
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
        for (Vector2d position : initialPositions) {
            Animal animal = new Animal(map, position, startEnergy, moveCost, null, null, 0);
            animals.add(animal);
            map.place(animal);
        }
//        animals.add(new Animal(map, new Vector2d(0,0), 100,1, (Animal) animals.get(0), (Animal) animals.get(1)));
    }

    @Override
    public void run() {
        int animalIndex = 0;
        int movesCnt = 1;

        for (int i = 0; i < 30000; i++) {
            statsChartManager.resetEpochStats();

            if (animals.size() > 0 && movesCnt % animals.size() == 0) {
                animalsDie(epoch);
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
                }
                map.placeGrassJungle(1);
                map.placeGrassSteppe(1);

                for (ArrayList<IMapElement> elements: map.getMapElements().values()) {
                    for (IMapElement element: elements) {
                        if (element instanceof Animal)
                            statsChartManager.readAliveAnimalData((Animal) element);
                        else if (element instanceof Grass)
                            statsChartManager.addPlantCount();
                    }
                }
                statsChartManager.chartUpdate(epoch);
                epoch += 1;
                try {
                    Thread.sleep(moveDelay);
                    for (IMapChangeObserver observer : observers) {
                        observer.mapChanged();
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
            Animal newAnimal = new Animal(map, key, -1, moveCost, twoStrongest.get(0), twoStrongest.get(1), epoch);
            animalStatsTracker.updateOnNewborn(twoStrongest.get(0), twoStrongest.get(1), newAnimal);
            newAnimals.add(newAnimal);
        }
        for (IMapElement animal : newAnimals) {
            animals.add(animal);
            map.place((Animal) animal);
        }
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void suspend() {
        this.isSuspended = true;
    }

    public void resume() {
        this.isSuspended = false;
    }

    public void addObserver(IMapChangeObserver observer) {
        observers.add(observer);
    }

    public void setMoveArray(ArrayList<MoveDirection> moveArray) {
        this.moveArray = moveArray;
    }
}

