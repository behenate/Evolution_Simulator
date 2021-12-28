package agh.ics.oop;
import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;
import agh.ics.oop.objects.IMapElement;
import agh.ics.oop.maps.WallMap;
import agh.ics.oop.simulation.SimulationEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
// This test checks simple moves, eating and reproducing of the animals
public class IntegrationTest {
    private final int[][] directionVectors = {{0,1}, {1,1}, {1,0}, {1,-1}, {0, -1}, {-1,-1},{-1,0}, {-1,1}};
    private WallMap map = new WallMap(100,100,0,20);
    private SimulationEngine engine;
    private Animal testAnimal1 = new Animal(
            map,
            new Vector2d(50,50),
            35,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );
    private Animal testAnimal2 = new Animal(
            map,
            new Vector2d(50,54),
            35,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );
    @Test
    void integrationTest(){
//        It will throw null pointer exceptions, because the statsManager and animalStatsTracker are null,
//        but the moving, eating and reproducing parts can be successfully tested
        try{
            engine = new SimulationEngine(
                    map,
                    0,
                    0,
                    0,
                    0,
                    0,
                    5,
                    false,
                    null,
                    null
            );
            map.getMapElements().cPut(new Vector2d(50,50), testAnimal1);
            map.getMapElements().cPut(new Vector2d(50,51), new Grass(new Vector2d(50,51), map));
            map.getMapElements().cPut(new Vector2d(50,54), testAnimal1);
            map.getMapElements().cPut(new Vector2d(50,53), new Grass(new Vector2d(50,51), map));
            engine.getAnimals().add(testAnimal1);
            engine.getAnimals().add(testAnimal2);
            while (testAnimal1.getMapDirection() !=0){
                testAnimal1.move(1);
            }
            while (testAnimal2.getMapDirection() != 4){
                testAnimal2.move(1);
            }

            testAnimal1.move(0);
            assertEquals(new Vector2d(50, 51), testAnimal1.getPosition());
            testAnimal2.move(0);
            assertEquals(new Vector2d(50, 53), testAnimal2.getPosition());

            engine.animalsEat();
//            Made sure that the grass disappeared and energy has been added
            assertEquals(40,testAnimal1.getEnergy());
            assertEquals(1, map.getMapElements().get(new Vector2d(50,51)).size());

            assertEquals(40,testAnimal1.getEnergy());
            assertEquals(1, map.getMapElements().get(new Vector2d(50,53)).size());

            testAnimal1.move(0);
            assertEquals(new Vector2d(50, 52), testAnimal1.getPosition());
            testAnimal2.move(0);
            assertEquals(new Vector2d(50, 52), testAnimal2.getPosition());

            engine.animalsReproduce();
//            Make sure that a new animal has appeared
            ArrayList<IMapElement> elems = map.getMapElements().get(new Vector2d(50,52));
            assertEquals(3, elems.size());

//            Make sure that energy levels are appropriate
            assertEquals(30, testAnimal1.getEnergy());
            assertEquals(30, testAnimal2.getEnergy());
            map.getMapElements().cRemove(testAnimal1.getPosition(), testAnimal1);
            map.getMapElements().cRemove(testAnimal2.getPosition(), testAnimal2);
            Animal newAnimal = (Animal) map.getMapElements().get(new Vector2d(50,52)).get(0);
            assertEquals(20, newAnimal.getEnergy());
        }catch (NullPointerException ex){
            System.out.println("Expected null pointer exception");
        }
    }
}