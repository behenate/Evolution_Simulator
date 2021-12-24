package agh.ics.oop.objects;

import agh.ics.oop.dataTypes.Vector2d;

public class RolledMap extends AbstractWorldMap{
    public RolledMap(int width,int height,float jungleRatio, int size){
        super(width, height, jungleRatio, size);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
