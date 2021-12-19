package agh.ics.oop.gui;

import agh.ics.oop.objects.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

class LinkedImageView extends ImageView{
    private IMapElement representedElement;
    public LinkedImageView(Image image, IMapElement element){
        super(image);
        this.representedElement = element;
    }
    public IMapElement getRepresentedElement(){
        return this.representedElement;
    }
}

public class GuiElementBox {
    private VBox verticalBox;
    public GuiElementBox(IMapElement element, String labelText, int size){
        Image image = element.getImage();
        LinkedImageView imageView = new LinkedImageView(image, element);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        Label label = new Label(labelText);
        verticalBox = new VBox();
        if (labelText != ""){
            verticalBox.getChildren().addAll(imageView,  label);
        }else {
            verticalBox.getChildren().addAll(imageView);
        }
        verticalBox.setAlignment(Pos.CENTER);

    }
    public VBox getVBox(){
        return verticalBox;
    }
}
