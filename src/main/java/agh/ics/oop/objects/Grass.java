package agh.ics.oop.objects;

import agh.ics.oop.dataTypes.Vector2d;
import javafx.scene.image.Image;

public class Grass extends AbstractWorldMapElement {
    private Image image = new Image("grass_proj.png");
    public Grass(Vector2d position){
        this.position = position;
    }
    @Override
    public String toString(){
        return "*";
    }
    @Override
    public Image getImage(){
        return image;
    }
}
