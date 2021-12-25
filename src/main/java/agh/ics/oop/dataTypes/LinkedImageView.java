package agh.ics.oop.dataTypes;

import agh.ics.oop.objects.IMapElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Modified ImageView that stores ImapElement that it references
// Used for user mouse events
public class LinkedImageView extends ImageView {
    private final IMapElement representedElement;
    public LinkedImageView(Image image, IMapElement element){
        super(image);
        this.representedElement = element;
    }
    public IMapElement getRepresentedElement(){
        return this.representedElement;
    }
}
