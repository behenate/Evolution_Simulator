package agh.ics.oop.maps;

import agh.ics.oop.dataTypes.Vector2d;

//A map that rolls animals to the other side if they try to go through a wall
public class RolledMap extends AbstractWorldMap{
    public RolledMap(int width,int height,float jungleRatio, int size){
        super(width, height, jungleRatio, size, "RolledMap");
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
