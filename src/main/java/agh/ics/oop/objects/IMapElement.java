package agh.ics.oop.objects;

import agh.ics.oop.dataTypes.LinkedImageView;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.GuiElementBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public interface IMapElement {
    Vector2d getPosition();
    Image getImage();
    GuiElementBox getGuiElementBox();
}
