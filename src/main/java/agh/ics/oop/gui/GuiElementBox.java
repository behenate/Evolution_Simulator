package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GuiElementBox {
    private VBox verticalBox;
    public GuiElementBox(IMapElement element){
        try{
            Image image = new Image(new FileInputStream(element.getImagePath()));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            Label label = new Label(element.getPosition().toString());
            verticalBox = new VBox();
            verticalBox.getChildren().addAll(imageView,  label);
            verticalBox.setAlignment(Pos.CENTER);
        }catch (FileNotFoundException ex){
            System.out.println(ex.getMessage() + " In GuiElementBox!");
            System.exit(1);
        }
    }
    public VBox getVBox(){
        return verticalBox;
    }
}
