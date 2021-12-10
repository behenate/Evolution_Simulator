package agh.ics.oop;

import javafx.application.Platform;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    TreeSet<Vector2d> xMap = new TreeSet<>((a, b) -> {
        if(a.x == b.x){
            return a.y - b.y;
        }
        return a.x - b.x;
    });
    TreeSet<Vector2d> yMap = new TreeSet<>((a, b) -> {
        if(a.y == b.y){
            return a.x - b.x;
        }
        return a.y - b.y;
    });

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        if (!xMap.isEmpty()){
            xMap.remove(oldPosition);
            yMap.remove(oldPosition);
        }
        xMap.add(newPosition);
        yMap.add(newPosition);

    }
    public Vector2d lowerLeft(){
        if (xMap.isEmpty())
            return new Vector2d(0,0);
        return new Vector2d(xMap.first().x, yMap.first().y);
    }
    public Vector2d upperRight(){
        if (xMap.isEmpty())
            return new Vector2d(0,0);
        return new Vector2d(xMap.last().x, yMap.last().y);
    }
}
