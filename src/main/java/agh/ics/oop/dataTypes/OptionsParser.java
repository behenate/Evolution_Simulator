package agh.ics.oop.dataTypes;

import agh.ics.oop.dataTypes.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;

public class OptionsParser {
    public ArrayList<MoveDirection> parse(String[] options) throws IllegalArgumentException{
        ArrayList<MoveDirection> result = new ArrayList<>();
        ArrayList<String> allowed = new ArrayList<String>(Arrays.asList("f", "forward","b","backward","r","right","l","left"));
        for (String word: options) {
            if (!allowed.contains(word)){
                throw new IllegalArgumentException(word + " is not legal move specification");
            }
            char letter = word.charAt(0);
            switch (letter){
                case 'f'-> result.add(MoveDirection.FORWARD);
                case 'b'->result.add(MoveDirection.BACKWARD);
                case 'r'->result.add(MoveDirection.RIGHT);
                case 'l'->result.add(MoveDirection.LEFT);
            }
        }
        return result;
    }
}
