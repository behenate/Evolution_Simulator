package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Lab4IntegrationTests {
    private final OptionsParser testParser = new OptionsParser();
    @Test
    void IntegrationTest1(){
//        TEST 1
        String[] input = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        ArrayList<Vector2d> outputPos = new ArrayList<>(Arrays.asList(new Vector2d(2,0), new Vector2d(3,4)));
        ArrayList<MapDirection> outputDir = new ArrayList<>(Arrays.asList(MapDirection.SOUTH, MapDirection.NORTH));
        ArrayList<MoveDirection> directions = testParser.parse(input);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(map, positions,1);
        engine.setMoveArray(directions);
        ArrayList<Animal> animals = new ArrayList<Animal>(Arrays.asList(
                (Animal) map.objectAt(new Vector2d(2,2)),
                (Animal) map.objectAt(new Vector2d(3, 4))
        ));
        engine.run();
        for (int i = 0; i < animals.size(); i++) {
            assertEquals(outputPos.get(i), animals.get(i).getPosition());
            assertEquals(outputDir.get(i), animals.get(i).getDirection());
        }
    }
    @Test
    void IntegrationTest2(){
        String[] input = {};
        ArrayList<Vector2d> outputPos = new ArrayList<>(Arrays.asList(
                new Vector2d(2, 2),
                new Vector2d(2,1)
        ));

        ArrayList<MapDirection> outputDir = new ArrayList<>(Arrays.asList(
                MapDirection.NORTH,
                MapDirection.NORTH
        ));
        ArrayList<MoveDirection> directions = testParser.parse(input);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(2,1)};
        IEngine engine = new SimulationEngine( map, positions,1);
        engine.setMoveArray(directions);
        ArrayList<Animal> animals = new ArrayList<Animal>(Arrays.asList(
                (Animal) map.objectAt(new Vector2d(2,2)),
                (Animal) map.objectAt(new Vector2d(2,1))
        ));
        engine.run();
//        Czy pusta tablica ruchów nie psuje programu?
        for (int i = 0; i < animals.size(); i++) {
            assertEquals(outputPos.get(i), animals.get(i).getPosition());
            assertEquals(outputDir.get(i), animals.get(i).getDirection());
        }
    }
    //    Test z kolizją zwierzaków
    @Test
    void IntegrationTest3(){
        String[] input = {"f", "f", "r", "r" ,"f", "f", "f", "f", "r", "r", "r", "f","r","f","r"};
        ArrayList<Vector2d> outputPos = new ArrayList<>(Arrays.asList(new Vector2d(2,1), new Vector2d(2,2)));
        ArrayList<MapDirection> outputDir = new ArrayList<>(Arrays.asList(MapDirection.EAST, MapDirection.SOUTH));
        ArrayList<MoveDirection> directions = testParser.parse(input);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(0,1),  new Vector2d(0,2)};
        IEngine engine = new SimulationEngine(map, positions,1);
        engine.setMoveArray(directions);
        ArrayList<Animal> animals = new ArrayList<Animal>(Arrays.asList(
                (Animal) map.objectAt(new Vector2d(0,1)),
                (Animal) map.objectAt(new Vector2d(0, 2))
        ));
        engine.run();

        for (int i = 0; i < animals.size(); i++) {
            assertEquals(outputPos.get(i), animals.get(i).getPosition());
            assertEquals(outputDir.get(i), animals.get(i).getDirection());
        }
    }
}
