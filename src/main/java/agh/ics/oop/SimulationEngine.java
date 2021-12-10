package agh.ics.oop;

import javafx.application.Platform;

import java.util.ArrayList;

public class SimulationEngine implements IEngine, Runnable{
    private ArrayList<MoveDirection> moveArray = new ArrayList<>();
    private final ArrayList<Animal> animals;
    private final IWorldMap map;
    private ArrayList<IMapChangeObserver> observers = new ArrayList<>();
    private final Integer moveDelay;
    public SimulationEngine(IWorldMap map, Vector2d[] initialPositions, Integer moveDelay){
        this.moveDelay = moveDelay;
        this.animals = new ArrayList<>();
        this.map = map;
        for (Vector2d position: initialPositions) {
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }
    }

    @Override
    public void run() {
        int animalIndex = 0;
        for (MoveDirection move: moveArray) {
            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            animals.get(animalIndex).move(move);
            animalIndex = (animalIndex + 1) % animals.size();
            for (IMapChangeObserver observer: observers) {
                observer.mapChanged();
            }
        }


    }
    public void addObserver(IMapChangeObserver observer){
        observers.add(observer);
    }
    public void setMoveArray(ArrayList<MoveDirection> moveArray){
        this.moveArray = moveArray;
    }
}
