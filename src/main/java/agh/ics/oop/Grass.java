package agh.ics.oop;

import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass extends AbstractWorldMapElement {
    public Grass(Vector2d position){
        this.position = position;
    }
    @Override
    public String toString(){
        return "*";
    }
    @Override
    public String getImagePath(){
        return "src/main/resources/grass.png";
    }
}
