package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
//Testy są praktycznie takie same jak dla rectangular map, ale biorą pod uwagę inne warunki panujące na GrassField
public class GrassFieldTest {
    //Test kolizji zwierzaków
    @Test
    void canMoveToTest1(){
        ArrayList<MoveDirection> moveArray = new OptionsParser().parse(new String[]{"r", "f", "r", "r", "l", "r", "f", "r", "f", "f", "r", "f", "f", "f", "f"});
        IWorldMap testMap = new GrassField(15);
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
        IWorldMap testMap = new GrassField(15);
        Vector2d[] initialPositions = {
                new Vector2d(2,1),
                new Vector2d(2,3),
                new Vector2d(1,2),
                new Vector2d(3,2)
        };
        Vector2d[] afterPositions = {
                new Vector2d(2,-2),
                new Vector2d(2,8),
                new Vector2d(-3,2),
                new Vector2d(7,2)
        };
        SimulationEngine engine = new SimulationEngine(testMap, initialPositions,1);
        engine.setMoveArray(moveArray);
        ArrayList<Animal> animals = new ArrayList<>();
        for (Vector2d position : initialPositions){
            animals.add((Animal) testMap.objectAt(position));
        }
        engine.run();
        for (int i = 0; i < animals.size(); i++) {
            assertEquals(afterPositions[i],animals.get(i).getPosition());
        }
    }
    @Test
    void placeTest(){
//        Test czy zwierze nie zostanie położone na inne zwierze i czy nie można położyć zwierzęcia poza mapą
        IWorldMap testMap = new GrassField(10);
        Animal sid = new Animal(testMap, new Vector2d(2,2));
        Animal maniek = new Animal(testMap, new Vector2d(2,2));
        Animal diego = new Animal(testMap, new Vector2d(100,100));
        testMap.place(sid);
        try{
            testMap.place(maniek);
        }catch (IllegalArgumentException e){
            assertEquals("Pole (2,2) nie jest dobrym polem dla zwierzaka!",e.getMessage());
        };
        testMap.place(diego);
        assertEquals(testMap.objectAt(new Vector2d(2,2)), sid);
        assertEquals(diego, testMap.objectAt(new Vector2d(100, 100)));
        sid.move(MoveDirection.FORWARD);
        assertFalse(testMap.objectAt(new Vector2d(2, 2)) instanceof Animal);
    }
    @Test
    void testIsOccupied(){
        IWorldMap testMap = new GrassField(15);
        Animal sid = new Animal(testMap, new Vector2d(2,2));
        testMap.place(sid);
        for (int i = 0; i < 15; i++) {
            for (int j =0; j<15; j++){
                if ((i!=2 || j!=2) && testMap.isOccupied(new Vector2d(i,j)))
                    assertTrue(testMap.objectAt(new Vector2d(i,j)) instanceof Grass);
            }
        }
        assertTrue(testMap.isOccupied(new Vector2d(2,2)));
    }
    @Test
    void testObjectAt(){
        IWorldMap testMap = new GrassField(10);
        Animal sid = new Animal(testMap, new Vector2d(2,2));
        testMap.place(sid);
        for (int i = 0; i < 10; i++) {
            for (int j =0; j<10; j++){
                if ((i!=2 || j!=2) && testMap.objectAt(new Vector2d(i,j)) != null)
                    assertTrue(testMap.objectAt(new Vector2d(i,j)) instanceof Grass);

            }
        }
        assertEquals(sid, testMap.objectAt(new Vector2d(2,2)));
    }
    @Test
    void testToString(){
        //Sprawdzam czy widoczna jest zadana ilość trawy
        IWorldMap testMap = new GrassField(10);
        String mapString = testMap.toString();
        int star_cnt = 0;
        for (int i = 0; i < mapString.length(); i++) {
            if(mapString.charAt(i)=='*')
                star_cnt ++;
        }
        assertEquals(10, star_cnt);
    }
}
