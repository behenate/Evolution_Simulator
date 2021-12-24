package agh.ics.oop.dataTypes;

import agh.ics.oop.objects.IMapElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LinkedImageView extends ImageView {
    private IMapElement representedElement;
    public LinkedImageView(Image image, IMapElement element){
        super(image);
        this.representedElement = element;
    }
    public IMapElement getRepresentedElement(){
        return this.representedElement;
    }
}
