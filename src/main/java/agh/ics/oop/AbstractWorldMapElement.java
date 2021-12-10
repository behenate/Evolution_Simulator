package agh.ics.oop;

import javafx.scene.control.Label;

import java.util.ArrayList;

public abstract class AbstractWorldMapElement implements IMapElement{
    protected ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    protected Vector2d position;
    public Vector2d getPosition(){
        return position;
    }
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    protected void positionChanged(Vector2d oldPosition,Vector2d newPosition ){
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

}
