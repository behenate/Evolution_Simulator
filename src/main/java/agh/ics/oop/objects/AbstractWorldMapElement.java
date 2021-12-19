package agh.ics.oop.objects;

import agh.ics.oop.simulation.IPositionChangeObserver;
import agh.ics.oop.dataTypes.Vector2d;

import java.util.ArrayList;

public abstract class AbstractWorldMapElement implements IMapElement {
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
    protected void positionChanged(IMapElement element,Vector2d newPosition ){
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(element, newPosition);
        }
    }

}
