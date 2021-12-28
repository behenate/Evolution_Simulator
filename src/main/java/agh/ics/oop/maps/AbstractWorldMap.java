package agh.ics.oop.maps;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.CustomHashMap;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.GuiElementBox;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;
import agh.ics.oop.objects.IMapElement;
import agh.ics.oop.simulation.IPositionChangeObserver;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

// A class representing map, its elements and implementing some essential functionality
public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
//    Map properties
    protected int width;
    protected int height;
    protected int jungleWidth;
    protected int jungleHeight;
    protected int jungleX;
    protected int jungleY;
    protected int size;
    protected int gridCellSize;
    protected final CustomHashMap mapElements = new CustomHashMap();
    protected final String name;
    public AbstractWorldMap(int width,int height,float jungleRatio, int size, String name){
        this.width = width;
        this.height = height;
//        Calculate jungle width and height
        this.jungleWidth = Math.round(width*(float)Math.sqrt(jungleRatio));
        this.jungleHeight = Math.round(height*(float)Math.sqrt(jungleRatio));
        this.jungleX = (width-jungleWidth)/2;
        this.jungleY = (height-jungleHeight)/2;
        this.size = size;
        this.gridCellSize = Math.min(size/width, size/height);
        this.name = name;
    }

//    Place an animal on the map
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!canMoveTo(animal.getPosition())){
            throw new IllegalArgumentException("Pole " + animal.getPosition() + " nie jest dobrym polem dla zwierzaka!");
        }
        animal.addObserver(this);
        mapElements.cPut(animal.getPosition(), animal);
        return true;
    }

//    Returns an ArrayList of map elemenst at provided position
    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }

//  Checks if there is any element at position
    public boolean isOccupied(Vector2d position) {
        return mapElements.get(position) != null;
    }
//    Checks if there is an animal at specified position
    public boolean isOccupiedByAnimal(Vector2d position){
        //If there is the strongest animal on chosen field, that means that the field is occupied
        return mapElements.getStrongest(position).size() != 0;
    }

//    Callback that gets called each time an animal moves
    @Override
    public void positionChanged(IMapElement element, Vector2d newPosition) {
        mapElements.cRemove(element.getPosition(), element);
        mapElements.cPut(newPosition, element);
    }
    // Flips the Y position coordinate
    private Vector2d flipPos(Vector2d pos){
        return new Vector2d(pos.x, height - (pos.y));
    }

    public void renderGrid(GridPane gridPane){
        gridPane.setGridLinesVisible(true);
        Label newLabel = new Label("y/x");
        gridPane.add(newLabel, 0,0,1, 1);
        GridPane.setHalignment(newLabel, HPos.CENTER);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

//        Set width and height of grid cells
        for (int i = 0; i < width+1; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(gridCellSize));
        }
        for (int i = 0; i < height+1; i++){
            gridPane.getRowConstraints().add(new RowConstraints(gridCellSize));
        }

//        Create coordinate labels
        for (int i = 0; i < width; i++){
            newLabel = new Label(Integer.toString(i));
            gridPane.add(newLabel, i+1, 0 ,1, 1);
            GridPane.setHalignment(newLabel, HPos.CENTER);
        }

        for (int i = 0; i < height; i++){
            newLabel = new Label(Integer.toString(height-i -1));
            gridPane.add(newLabel, 0, i+1 ,1, 1);
            GridPane.setHalignment(newLabel, HPos.CENTER);
        }
//        Draw the map elements
        for (Vector2d key: mapElements.keySet()) {
            IMapElement toRender;
            ArrayList<Animal> strongestAnimals = mapElements.getStrongest(key);
            toRender = strongestAnimals.size()==0 ? mapElements.grassAt(key) : strongestAnimals.get(0);
            Vector2d posFixed = flipPos(key);
            GuiElementBox guiBox = toRender.getGuiElementBox();
            gridPane.add(guiBox.getVBox(), posFixed.x + 1, posFixed.y,1, 1);
            GridPane.setHalignment(newLabel, HPos.CENTER);
        }
    }

//    Returns the most basic information about the map in an array form
    @Override
    public int[] getMapProps() {
        return new int[]{width, height, jungleHeight, jungleHeight};
    }

//  Method that places n tufts of grass in the jungle
    public int placeGrassJungle(int n){
        if (n==0)
            return 0;
        int x = Utils.getRandomNumber(jungleX, jungleX+jungleWidth);
        int y = Utils.getRandomNumber(jungleY, jungleY+jungleHeight);
        int placeTryCounter = 0;
        while (isOccupied(new Vector2d(x,y)) && placeTryCounter < 10){
            x = Utils.getRandomNumber(jungleX, jungleX+jungleWidth);
            y = Utils.getRandomNumber(jungleY, jungleY+jungleHeight);
            placeTryCounter += 1;
        }
        if (isOccupied(new Vector2d(x,y))){
            for (int i = jungleY; i < jungleY + jungleHeight; i++) {
                for (int j = jungleX; j < jungleX + jungleWidth; j++) {
                    if (!isOccupied(new Vector2d(j,i))){
                        x= j;
                        y = i;
                        break;
                    }
                }
                if (!isOccupied(new Vector2d(x,y)))
                    break;
            }
        }
        if (!isOccupied(new Vector2d(x,y))) {
            mapElements.cPut(new Vector2d(x,y), new Grass(new Vector2d(x,y), this));
            return  1 + placeGrassJungle(n-1);
        }
        return 0;
    }

//    Method to place n grass tufts on the steppe
    public int placeGrassSteppe(int n){
        if (n==0)
            return 0;
        int x = Utils.getRandomNumber(0, width);
        int y = Utils.getRandomNumber(0, height);
        int placeTryCounter = 0;
        if (x>= jungleX && x <jungleWidth+x && y>= jungleY && y < jungleY+jungleWidth){
            return placeGrassSteppe(n);
        }
        while (isOccupied(new Vector2d(x,y)) && placeTryCounter <= 10){
            x = Utils.getRandomNumber(0, width);
            y = Utils.getRandomNumber(0, height);
            placeTryCounter += 1;
        }
        if (isOccupied(new Vector2d(x,y))){
            for (int i = 0; i < height; i++) {
                if (i >= jungleY && i < jungleY+jungleWidth)
                    continue;
                for (int j = 0; j < width; j++) {
                    if (j >= jungleY && j < jungleY+jungleWidth)
                        continue;
                    if (!isOccupied(new Vector2d(j,i))){
                        x=j;
                        y=i;
                        break;
                    }
                }
                if (!isOccupied(new Vector2d(x,y)))
                    break;
            }
        }
        if (!isOccupied(new Vector2d(x,y))){
            mapElements.cPut(new Vector2d(x,y), new Grass(new Vector2d(x,y), this));
            return 1 +placeGrassSteppe(n-1);
        }
        return 0;
    }
    public CustomHashMap getMapElements(){
        return mapElements;
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public String getName(){return this.name;}
    public int getGridCellSize(){return this.gridCellSize;}

}
