package agh.ics.oop.objects;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.LinkedImageView;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.GuiElementBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
// A simple world object representing a tuft of grass
public class Grass extends AbstractWorldMapElement {

    public Grass(Vector2d position, AbstractWorldMap map){
        super(position, map);
        this.guiElementBox = new GuiElementBox(this, map.gridCellSize);
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
