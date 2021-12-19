package agh.ics.oop.objects;

import agh.ics.oop.dataTypes.Vector2d;

public class RolledMap extends AbstractWorldMap{
    public RolledMap(int width,int height,float jungleRatio){
        super(width, height, jungleRatio);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

}
