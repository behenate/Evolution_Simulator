package agh.ics.oop;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GrassField extends AbstractWorldMap implements IWorldMap{
    public GrassField(int n){
        for (int i = 0; i < n; i++) {
            placeGrass(n);
        }
    }
    private void placeGrass(int n){
        int posX = ThreadLocalRandom.current().nextInt(0, (int)Math.sqrt(n*10) + 1);
        int posY = ThreadLocalRandom.current().nextInt(0, (int)Math.sqrt(n*10) + 1);
        while (isOccupied(new Vector2d(posX, posY))){
            posX = ThreadLocalRandom.current().nextInt(0, (int)Math.sqrt(n*10) + 1);
            posY = ThreadLocalRandom.current().nextInt(0, (int)Math.sqrt(n*10) + 1);
        }
        Vector2d grassPos = new Vector2d(posX, posY);
        Grass newGrass = new Grass(grassPos);
        newGrass.addObserver(boundary);
        mapElements.put(grassPos, newGrass);
        boundary.positionChanged(new Vector2d(0,0), grassPos);

    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        IMapElement objAtNew = mapElements.get(newPosition);
        super.positionChanged(oldPosition, newPosition);
        if (objAtNew instanceof Grass)
            placeGrass(1);
    }

    @Override
    Vector2d[] calculateBounds() {
        Vector2d lowerLeft = new Vector2d(999999,999999);
        Vector2d upperRight = new Vector2d(-999999,-999999);
        for (IMapElement iMapElement : mapElements.values()) {
            lowerLeft = lowerLeft.lowerLeft(iMapElement.getPosition());
            upperRight = upperRight.upperRight(iMapElement.getPosition());
        }
        return new Vector2d[] {lowerLeft, upperRight};
    }
}
