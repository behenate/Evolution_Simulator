package agh.ics.oop.objects;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.CustomHashMap;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.GuiElementBox;
import agh.ics.oop.simulation.IPositionChangeObserver;
import agh.ics.oop.simulation.MapVisualizer;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected int width;
    protected int height;
    protected int jungleWidth;
    protected int jungleHeight;
    protected int jungleX;
    protected int jungleY;
    protected int size;
    protected int gridCellSize;
    protected final CustomHashMap mapElements = new CustomHashMap();
    protected final MapVisualizer visualizer = new MapVisualizer(this);

    public AbstractWorldMap(int width,int height,float jungleRatio, int size){
        this.width = width;
        this.height = height;
        this.jungleWidth = Math.round(width*(float)Math.sqrt(jungleRatio));
        this.jungleHeight = Math.round(height*(float)Math.sqrt(jungleRatio));
        this.jungleX = (width-jungleWidth)/2;
        this.jungleY = (height-jungleHeight)/2;
        this.size = size;
        this.gridCellSize = Math.min(size/width, size/height);
    }
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!canMoveTo(animal.getPosition())){
            throw new IllegalArgumentException("Pole " + animal.getPosition() + " nie jest dobrym polem dla zwierzaka!");
        }
        animal.addObserver(this);
        mapElements.cPut(animal.getPosition(), animal);
        return true;
    }



    public CustomHashMap getMapElements(){
        return mapElements;
    }
    public Object objectAt(Vector2d position) {
        return mapElements.get(position);
    }


    public boolean isOccupied(Vector2d position) {
        return mapElements.get(position) != null;
    }
    public boolean isOccupiedByAnimal(Vector2d position){
        //If there is a strongest animal on chosen field, that means that the field is occupied
        return mapElements.getStrongest(position).size() != 0;
    }
    public String toString() {
        return visualizer.draw(new Vector2d(0,0), new Vector2d(width, height));
    }

    @Override
    public void positionChanged(IMapElement element, Vector2d newPosition) {
        mapElements.cRemove(element.getPosition(), element);
        mapElements.cPut(newPosition, element);
    }
    //Zamienia realną pozycję czegoś na odpowiednią pozycję na mapie
    private Vector2d flipPos(Vector2d pos){
        return new Vector2d(pos.x, height - (pos.y));
    }

    public void renderGrid(GridPane gridPane, int size){
        gridPane.setGridLinesVisible(true);
        Label newLabel = new Label("y/x");
        gridPane.add(newLabel, 0,0,1, 1);
        GridPane.setHalignment(newLabel, HPos.CENTER);
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < width+1; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(gridCellSize));
        }
        for (int i = 0; i < height+1; i++){
            gridPane.getRowConstraints().add(new RowConstraints(gridCellSize));
        }

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

    @Override
    public int[] getMapProps() {
        return new int[]{width, height, jungleHeight, jungleHeight};
    }

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
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }

}
