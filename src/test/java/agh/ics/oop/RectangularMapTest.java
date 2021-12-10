package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    //Test kolizji zwierzaków
    @Test
    void canMoveToTest1(){
        ArrayList<MoveDirection> moveArray = new OptionsParser().parse(new String[]{"r", "f", "r", "r", "l", "r", "f", "r", "f", "f", "r", "f", "f", "f", "f"});
        IWorldMap testMap = new RectangularMap(5,5);
        Vector2d[] initialPositions = {
                new Vector2d(2,2),
                new Vector2d(2,1),
                new Vector2d(2,3),
                new Vector2d(1,2),
                new Vector2d(3,2)
        };
        SimulationEngine engine = new SimulationEngine(testMap, initialPositions,1);
        engine.setMoveArray(moveArray);
        ArrayList<Animal> animals = new ArrayList<>();
        for (Vector2d position : initialPositions){
            animals.add((Animal) testMap.objectAt(position));
        }
        engine.run();
        for (int i = 0; i < animals.size(); i++) {
            assertEquals(animals.get(i).getPosition(), initialPositions[i]);
        }
    }
    @Test
    void canMoveToTest2(){
//        4 zwierzaki idą w 4 strony świata aż do granic mapy i próbują wyjść
        ArrayList<MoveDirection> moveArray = new OptionsParser().parse(new String[]{"r", "f" , "l" , "r" , "r" , "f" , "f" , "f" , "f" , "f" , "f" , "f" , "f" , "f" , "f" , "f" , "f" , "f" , "f" ,
                "f"});
        IWorldMap testMap = new RectangularMap(5,5);
        Vector2d[] initialPositions = {
                new Vector2d(2,1),
                new Vector2d(2,3),
                new Vector2d(1,2),
                new Vector2d(3,2)
        };
        Vector2d[] afterPositions = {
                new Vector2d(2,0),
                new Vector2d(2,4),
                new Vector2d(0,2),
                new Vector2d(4,2)
        };
        SimulationEngine engine = new SimulationEngine(testMap, initialPositions,1);
        engine.setMoveArray(moveArray);
        ArrayList<Animal> animals = new ArrayList<>();
        for (Vector2d position : initialPositions){
            animals.add((Animal) testMap.objectAt(position));
        }
        engine.run();
        for (int i = 0; i < animals.size(); i++) {
            assertEquals(animals.get(i).getPosition(), afterPositions[i]);
        }
    }
    @Test
    void placeTest(){
//        Test czy zwierze nie zostanie położone na inne zwierze i czy nie można położyć zwierzęcia poza mapą
        IWorldMap testMap = new RectangularMap(5,5);
        Animal sid = new Animal(testMap, new Vector2d(2,2));
        Animal maniek = new Animal(testMap, new Vector2d(2,2));
        Animal diego = new Animal(testMap, new Vector2d(100,100));
        testMap.place(sid);
        try{
            testMap.place(maniek);
        }catch (IllegalArgumentException e){
            assertEquals("Pole (2,2) nie jest dobrym polem dla zwierzaka!",e.getMessage());
        };
        try{
            testMap.place(diego);
        }catch (IllegalArgumentException e){
            assertEquals("Pole (100,100) nie jest dobrym polem dla zwierzaka!",e.getMessage());
        };
        assertEquals(testMap.objectAt(new Vector2d(2,2)), sid);
        assertNull(testMap.objectAt(new Vector2d(100, 100)));
        sid.move(MoveDirection.FORWARD);
        assertNull(testMap.objectAt(new Vector2d(2, 2)));
    }
    @Test
    void testIsOccupied(){
        IWorldMap testMap = new RectangularMap(5,5);
        Animal sid = new Animal(testMap, new Vector2d(2,2));
        testMap.place(sid);
        for (int i = 0; i < 5; i++) {
            for (int j =0; j<5; j++){
                if (i!=2 || j!=2)
                    assertFalse(testMap.isOccupied(new Vector2d(i,j)));
            }
        }
        assertTrue(testMap.isOccupied(new Vector2d(2,2)));
    }
    @Test
    void testObjectAt(){
        IWorldMap testMap = new RectangularMap(5,5);
        Animal sid = new Animal(testMap, new Vector2d(2,2));
        testMap.place(sid);
        for (int i = 0; i < 5; i++) {
            for (int j =0; j<5; j++){
                if (i!=2 || j!=2)
                    assertNull(testMap.objectAt(new Vector2d(i,j)));
            }
        }
        assertEquals(sid, testMap.objectAt(new Vector2d(2,2)));
    }

}
