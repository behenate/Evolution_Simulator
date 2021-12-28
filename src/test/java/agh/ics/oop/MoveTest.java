package agh.ics.oop;
import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.maps.WallMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {
    private final int[][] directionVectors = {{0,1}, {1,1}, {1,0}, {1,-1}, {0, -1}, {-1,-1},{-1,0}, {-1,1}};
    private WallMap map = new WallMap(100,100,0,20);
    private Animal testAnimal1 = new Animal(
            map,
            new Vector2d(50,50),
            24,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );

    @Test
    void moveTest(){
        for (int i = 0; i < 8; i++) {
            int prevOrientation = testAnimal1.getMapDirection();
            Vector2d oldPosition = testAnimal1.getPosition();
            testAnimal1.move(i);
            int newOrientation = testAnimal1.getMapDirection();
            Vector2d newPosition = testAnimal1.getPosition();
            if (i != 0 && i != 4){
                assertEquals((prevOrientation+i)%8,newOrientation);
            }else if (i == 0){
                assertEquals(prevOrientation, newOrientation);
                assertEquals(oldPosition.add(new Vector2d(directionVectors[newOrientation][0], directionVectors[newOrientation][1])), newPosition);
            }else {
                assertEquals(prevOrientation, newOrientation);
                assertEquals(oldPosition.add(new Vector2d(-directionVectors[newOrientation][0], -directionVectors[newOrientation][1])), newPosition);
            }
        }
    }

}
