package agh.ics.oop;

import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;

public interface IMapElement {
    Vector2d getPosition();
    String getImagePath();
}
