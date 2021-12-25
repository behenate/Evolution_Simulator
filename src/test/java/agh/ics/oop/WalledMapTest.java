package agh.ics.oop;

import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.WallMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class WalledMapTest {
    private WallMap map = new WallMap(10,10,0,20);
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
            map, new Vector2d(9,9),
            8,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );
    @Test
    void wallsTest(){
        while (testAnimal1.getMapDirection() != 6){
            testAnimal1.move(1);
        }
        while (testAnimal2.getMapDirection() != 0){
            testAnimal2.move(1);
        }
        testAnimal1.move(0);
        testAnimal2.move(0);
        assertEquals(new Vector2d(0,0), testAnimal1.getPosition());
        assertEquals(new Vector2d(9,9), testAnimal2.getPosition());

        testAnimal1.move(6);
        testAnimal1.move(0);

        testAnimal2.move(3);
        testAnimal2.move(0);
        assertEquals(new Vector2d(0,0), testAnimal1.getPosition());
        assertEquals(new Vector2d(9,9), testAnimal2.getPosition());
    }
}
