package agh.ics.oop.objects;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.GuiElementBox;
import agh.ics.oop.maps.AbstractWorldMap;
import javafx.scene.image.Image;

// A simple world object representing a tuft of grass
public class Grass extends AbstractWorldMapElement {

    public Grass(Vector2d position, AbstractWorldMap map){
        super(position, map);
        this.guiElementBox = new GuiElementBox(this, map.getGridCellSize());
    }
    @Override
    public String toString(){
        return "*";
    }
    @Override
    public Image getImage(){
        return Utils.grassImage;
    }
}
