package agh.ics.oop.objects;

import agh.ics.oop.dataTypes.Vector2d;

public class WallMap extends AbstractWorldMap{
    public WallMap(int width,int height,float jungleRatio, int size){
        super(width, height, jungleRatio, size);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(position.x >= width || position.x < 0 || position.y >= height || position.y < 0);
    }
}
