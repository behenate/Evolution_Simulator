package agh.ics.oop;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {
    private RectangularMap map = new RectangularMap(5,5);
    private Animal testAnimal = new Animal(map, new Vector2d(2,2));
    private final OptionsParser testParser = new OptionsParser();
    @Test
    void integrationTests(){
//        Test poprawnej orientacji
        map.place(testAnimal);
        String[] input1 = {"r", "r","r","r", "l","l","l","l"};
        ArrayList<MoveDirection> pInput1 = testParser.parse(input1);
        MapDirection[] output1 = {MapDirection.NORTH, MapDirection.EAST, MapDirection.SOUTH, MapDirection.WEST, MapDirection.NORTH, MapDirection.WEST, MapDirection.SOUTH, MapDirection.EAST, MapDirection.NORTH};
        for (int i = 0; i < pInput1.size(); i++) {
            assertEquals(output1[i], testAnimal.getDirection());
            testAnimal.move(pInput1.get(i));
        }
//        Test przemieszczenia i wyjścia poza mapę dla kolejno North East South West:
        boundsMoveTest(0, 0, 1);
        boundsMoveTest(1, 1, 0);
        boundsMoveTest(2, 0, -1);
        boundsMoveTest(3, -1, 0);

//        Test parsera
//        Dane poniżej bez śmieci: ,"f", "b","r", "l", "forward", "backward","left","right"
        int e_cnt = 0;
        map = new RectangularMap(5,5);
        testAnimal = new Animal(map, new Vector2d(2,2));
        map.place(testAnimal);
        String[] input2 = {"trololo","f","blabla", "b","rr", "r","ll f b r l", "l", "forward","kupol", "backward","forwardfas","left","smiec","right"};

        try{
            ArrayList<MoveDirection> pInput2 = testParser.parse(input2);
            MapDirection[] outputMapDir = {MapDirection.NORTH, MapDirection.NORTH, MapDirection.NORTH, MapDirection.EAST,
                    MapDirection.NORTH, MapDirection.NORTH, MapDirection.NORTH, MapDirection.WEST, MapDirection.NORTH};
            int[][] outPosInt = {{2,2}, {2,3}, {2,2}, {2,2}, {2,2}, {2,3},{2,2},{2,2},{2,2}};
            Vector2d[] outputPos = new Vector2d[outPosInt.length];
            for (int i = 0; i < outPosInt.length; i++) {
                outputPos[i] = new Vector2d(outPosInt[i][0], outPosInt[i][1]);
            }
            for (int i = 0; i < pInput2.size(); i++) {
                assertEquals(outputMapDir[i], testAnimal.getDirection());
                assertEquals(outputPos[i], testAnimal.getPosition());
                testAnimal.move(pInput2.get(i));
            }
        }catch (Exception e){
            assertEquals("trololo is not legal move specification", e.getMessage());
        }

    }

    /*
        Funkcja testująca. Obraca zwierzaka podaną liczbę razy w prawo, następnie próbuje nim wyjść
        poza mapę przodem i tyłem. Dla każdego ruchu sprawdzane jest czy pozycja jest poprawna. x_vec i y_vec to
        skłądowe x i y wektora ruchu któru powinien wykonać zwierzak
    */
    void boundsMoveTest(int rotations, int x_vec, int y_vec) {
        map = new RectangularMap(5,5);
        testAnimal = new Animal(map, new Vector2d(2,2));
        map.place(testAnimal);
        int expected_x = 2;
        int expected_y = 2;
        String[] input = new String[rotations+12];
        for (int i = 0; i < rotations; i++) {
            input[i] = "r";
        }
        for (int i = 0; i < 6; i++) {
            input[i+rotations] = "f";
        }
        for (int i = 0; i < 6; i++) {
            input[i+6+rotations] = "b";
        }
//       // Zparsowanie wygenerowanej sekwencji ruchów
        ArrayList<MoveDirection> pInput = testParser.parse(input);
        for (int i = 0; i < rotations; i++) {
            testAnimal.move(pInput.get(i));
        }
        for (int j = 0; j < 2; j++) {
//          //Sign zmienia znak dodawania wektora - w drugiej iteracji wykonywane jest 6 ruchów w tył.
            int sign = 1 - j*2;
            for (int i = 0; i < 6; i++) {
                testAnimal.move(pInput.get(rotations+i+(j*6)));
                expected_x = Math.min(Math.max(expected_x + (x_vec*sign), 0), 4);
                expected_y = Math.min(Math.max(expected_y + (y_vec*sign), 0), 4);
                assertEquals(new Vector2d(expected_x, expected_y), testAnimal.getPosition());
            }
        }
    }
}
