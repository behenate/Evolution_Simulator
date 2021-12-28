package agh.ics.oop.maps;

import agh.ics.oop.dataTypes.Vector2d;

// A map that blocks animal movement when it tries to go through a wall
public class WallMap extends AbstractWorldMap{
    public WallMap(int width,int height,float jungleRatio, int size){
        super(width, height, jungleRatio, size, "WallMap");
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(position.x >= width || position.x < 0 || position.y >= height || position.y < 0);
    }
}
