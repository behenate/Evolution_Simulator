package agh.ics.oop;

import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.RolledMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class RolledMapTest {
    private RolledMap map = new RolledMap(10,10,0,20);
    private Animal testAnimal1 = new Animal(
            map, new Vector2d(0,0),
            24,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );
    private Animal testAnimal2 = new Animal(
            map, new Vector2d(0,0),
            24,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );
    private Animal testAnimal3 = new Animal(
            map, new Vector2d(9,9),
            8,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );
    private Animal testAnimal4 = new Animal(
            map, new Vector2d(9,9),
            8,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );

    private final int[][] directionVectors = {{0,1}, {1,1}, {1,0}, {1,-1}, {0, -1}, {-1,-1},{-1,0}, {-1,1}};
    @Test
    void wallsTest(){
        while (testAnimal1.getMapDirection() != 6){
            testAnimal1.move(1);
        }
        while (testAnimal2.getMapDirection() != 4){
            testAnimal2.move(1);
        }
        while (testAnimal3.getMapDirection() != 0){
            testAnimal3.move(1);
        }
        while (testAnimal4.getMapDirection() != 2){
            testAnimal4.move(1);
        }
        testAnimal1.move(0);
        testAnimal2.move(0);
        testAnimal3.move(0);
        testAnimal4.move(0);
        assertEquals(new Vector2d(9,0), testAnimal1.getPosition());
        assertEquals(new Vector2d(0,9), testAnimal2.getPosition());
        assertEquals(new Vector2d(9,0), testAnimal3.getPosition());
        assertEquals(new Vector2d(0, 9), testAnimal4.getPosition());

    }
}
