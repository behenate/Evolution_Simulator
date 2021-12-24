package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.LinkedImageView;
import agh.ics.oop.objects.AbstractWorldMap;
import agh.ics.oop.objects.AbstractWorldMapElement;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiElementBox {
    private final VBox mainContainer;
    private IMapElement element;
    private LinkedImageView imageView;
    private int size;
    public GuiElementBox(IMapElement element, int size){
        this.element = element;
        this.size = size;
        mainContainer = new VBox();
        imageView = new LinkedImageView(element.getImage(), element);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        mainContainer.getChildren().addAll(imageView);
        // Render animal healthbar
        if (element instanceof Animal){
            Animal animal = (Animal) element;
            ImageView healthBarImageView = animal.getHealthBarImageView();
            healthBarImageView.setFitHeight(Math.max(size/10,1));
            healthBarImageView.setTranslateY(-size*0.1);
            update();
            mainContainer.getChildren().addAll(healthBarImageView);
        }
        mainContainer.setAlignment(Pos.CENTER);

    }
    public VBox getVBox(){
        return mainContainer;
    }
    public void update(){
//        Update the animal image
        imageView.setImage(element.getImage());
        // Render animal healthbar
        if (element instanceof Animal){
            Animal animal = (Animal) element;
            ImageView healthBarImageView = animal.getHealthBarImageView();
            int width = (int)((size-5) * Math.min(1, animal.getEnergyPercentage()));
//           Width of 0 renders with original resolution
            width = Math.max(width, 1);
            healthBarImageView.setFitWidth(width);
        }
    }
    public void highlight(){
        mainContainer.setStyle("-fx-background-color: #a7d2fc");
    }
    public void deHighlight(){
        mainContainer.setStyle("-fx-background-color: rgba(255,255,255,0)");
    }
}
