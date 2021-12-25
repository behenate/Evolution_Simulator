package agh.ics.oop.gui;

import agh.ics.oop.dataTypes.LinkedImageView;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiElementBox {
    private final VBox mainContainer;
    private final IMapElement element;
    private final LinkedImageView imageView;
    private final int size;
    public GuiElementBox(IMapElement element, int size){
        this.element = element;
        this.size = size;

//        Set up the UI elements
        mainContainer = new VBox();
        imageView = new LinkedImageView(element.getImage(), element);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        mainContainer.getChildren().addAll(imageView);
        // Render animal healthbar
        if (element instanceof Animal animal){
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
//    Method called when animal does a move that requires an UI update
    public void update(){
//        Update the animal image
        imageView.setImage(element.getImage());
        // Render animal healthbar
        if (element instanceof Animal animal){
            ImageView healthBarImageView = animal.getHealthBarImageView();
            int width = (int)((size-5) * Math.min(1, animal.getEnergyPercentage()));
//           Width of 0 renders with original resolution
            width = Math.max(width, 1);
            healthBarImageView.setFitWidth(width);
        }
    }
//    Highlights the element by changing the background
    public void highlight(){
        mainContainer.setStyle("-fx-background-color: #a7d2fc");
    }
    public void deHighlight(){
        mainContainer.setStyle("-fx-background-color: rgba(255,255,255,0)");
    }
}
