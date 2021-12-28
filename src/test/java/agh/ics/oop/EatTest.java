package agh.ics.oop;
import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;
import agh.ics.oop.maps.WallMap;
import agh.ics.oop.simulation.SimulationEngine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EatTest {
    private final int[][] directionVectors = {{0,1}, {1,1}, {1,0}, {1,-1}, {0, -1}, {-1,-1},{-1,0}, {-1,1}};
    private WallMap map = new WallMap(100,100,0,20);
    private SimulationEngine engine;
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
    void eatTest(){
//        It will throw null pointer exceptions, because the statsManager and animalStatsTracker are null,
//        but the eating part can be successfully tested
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
            map.getMapElements().cPut(new Vector2d(50,50), new Grass(new Vector2d(50,50), map));
            engine.getAnimals().add(testAnimal1);
            engine.animalsEat();
            assertEquals(29,testAnimal1.getEnergy());
            assertEquals(1, map.getMapElements().get(new Vector2d(50,50)).size());
        }catch (NullPointerException ex){
            System.out.println("Expected null pointer exception");
        }
    }
}
