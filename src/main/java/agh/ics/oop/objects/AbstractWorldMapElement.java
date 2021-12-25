package agh.ics.oop.objects;

import agh.ics.oop.gui.GuiElementBox;
import agh.ics.oop.simulation.IPositionChangeObserver;
import agh.ics.oop.dataTypes.Vector2d;

import java.util.ArrayList;

// Abstract class holding common properties and method for all map elements
public abstract class AbstractWorldMapElement implements IMapElement {

    protected ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    protected Vector2d position;
    protected AbstractWorldMap map;
    protected GuiElementBox guiElementBox;

    public AbstractWorldMapElement (Vector2d position, AbstractWorldMap map){
        this.position = position;
        this.map = map;
    }

    public Vector2d getPosition(){
        return position;
    }

    public GuiElementBox getGuiElementBox(){
        this.guiElementBox.update();
        return this.guiElementBox;
    }
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    protected void positionChanged(IMapElement element,Vector2d newPosition ){
        for (IPositionChangeObserver observer: observers) {
            observer.positionChanged(element, newPosition);
        }
    }

}
