package agh.ics.oop;

import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Animal extends AbstractWorldMapElement {
    private MapDirection mapDirection = MapDirection.NORTH;
    private final IWorldMap map;
    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
    }
    @Override
    public String toString() {
        return switch (mapDirection){
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }
    public void move(MoveDirection direction){
        Vector2d newPos = position;
        switch (direction){
            case LEFT -> mapDirection = mapDirection.previous();
            case RIGHT -> mapDirection = mapDirection.next();
            case FORWARD -> newPos = position.add(mapDirection.toUnitVector());
            case BACKWARD -> newPos = position.add(mapDirection.toUnitVector().opposite());
        }
        if (map.canMoveTo(newPos)){
            positionChanged(position, newPos);
            position = newPos;
        }
    }
    public MapDirection getDirection(){
        return mapDirection;
    }
    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    @Override
    public String getImagePath() {
        return switch (this.mapDirection){
            case NORTH -> "src/main/resources/up.png";
            case SOUTH ->  "src/main/resources/down.png";
            case WEST -> "src/main/resources/left.png";
            default ->  "src/main/resources/right.png";
        };
    }
}
