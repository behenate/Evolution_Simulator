package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.objects.AbstractWorldMap;
import agh.ics.oop.objects.RolledMap;
import agh.ics.oop.objects.WallMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Subclass for just reading the data
class SimulationPropsSubReader {
    private final VBox mainContainer = new VBox();
    TextField mapWidthField = new TextField("10");
    TextField mapHeightField = new TextField("10");
    TextField animalNumberField = new TextField("20");
    TextField grassNumberField = new TextField("20");
    TextField startEnergyField = new TextField("10");
    TextField moveEnergyField = new TextField("1");
    TextField plantEnergyField = new TextField("8");
    TextField jungleRatioField = new TextField("0.3");
    TextField animationStepDelayField = new TextField("50");
    CheckBox isMagical = new CheckBox();
    TextField [] fields = {mapWidthField, mapHeightField, animalNumberField,grassNumberField,startEnergyField, moveEnergyField, plantEnergyField, jungleRatioField, animationStepDelayField};
    String[] fieldsDescriptions = { "Width of the map: ", "Height of the map: ","Start number of animals: ",
            "Start number of grass tufts: ","Animal start energy:", "Energy cost of an animal move: ",
            "Energy bonus from eating a tuft of grass: ", "Jungle to field ratio:", "Animation step delay in ms: "
    };
//    On start create new window and read the properties
    public SimulationPropsSubReader(String title){
        Text titleText = new Text(title);
        titleText.setFont(Font.font ("Rubik", 20));
        mainContainer.getChildren().add(titleText);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPrefWidth(250);
        for (int i = 0; i < fields.length; i++) {
            mainContainer.getChildren().addAll(new Text(fieldsDescriptions[i]), fields[i]);
        }
        mainContainer.getChildren().addAll(new Text("Is the simulation magical?"), isMagical);
    }

    private void correctnessChecker(TextField textField, float value_low, float value_high){
        if (Float.parseFloat(textField.getText()) < value_low || Float.parseFloat(textField.getText()) > value_high){
            throw new IllegalArgumentException("The input value was outside of bounds!");
        }
    }
    public int getMapWidth(){
        correctnessChecker(mapWidthField, 1, Integer.MAX_VALUE);
        return Integer.parseInt(mapWidthField.getText());
    }
    public int getMapHeight(){
        correctnessChecker(mapHeightField, 1, Integer.MAX_VALUE);
        return Integer.parseInt(mapHeightField.getText());
    }
    public int getStartEnergy(){
        correctnessChecker(startEnergyField, 1, Integer.MAX_VALUE);
        return Integer.parseInt(startEnergyField.getText());
    }
    public int getAnimalNumber(){
        correctnessChecker(animalNumberField, 0, getMapWidth()*getMapHeight());
        return Integer.parseInt(animalNumberField.getText());
    }
    public int getGrassNumber(){
        correctnessChecker(grassNumberField, 0, getMapWidth()*getMapHeight());
        return Integer.parseInt(grassNumberField.getText());
    }
    public int getMoveEnergy(){
        correctnessChecker(moveEnergyField, 0, Integer.MAX_VALUE);
        return Integer.parseInt(moveEnergyField.getText());
    }
    public int getPlantEnergy(){
        correctnessChecker(plantEnergyField, 0, Integer.MAX_VALUE);
        return Integer.parseInt(plantEnergyField.getText());
    }
    public float getJungleRatio(){
        correctnessChecker(jungleRatioField, 0, 1);
        return Float.parseFloat(jungleRatioField.getText());
    }
    public int getMoveDelay(){
        correctnessChecker(animationStepDelayField, 1, Integer.MAX_VALUE);
        return Integer.parseInt(animationStepDelayField.getText());
    }
    public boolean getIsMagical(){
        return isMagical.selectedProperty().get();
    }
    public VBox getSubReaderUI(){
        return this.mainContainer;
    }
}
// Class that reads the data and creates two simulations based on it
// Splitting into two classes made sense when both simulations had different inputs, now
public class SimulationPropsReader {
    HBox inputContainer = new HBox();
    VBox mainContainer = new VBox();
    SimulationPropsSubReader mapProps = new SimulationPropsSubReader("Map Properties");
    Button startSimulationButton = new Button("Start Simulation");
//    On start create window and create new reader
    public SimulationPropsReader(EventHandler<ActionEvent> onButtonClick){
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setPrefWidth(1920);
        inputContainer.setSpacing(50);
        inputContainer.getChildren().addAll(mapProps.getSubReaderUI());
        mainContainer.getChildren().addAll(inputContainer, startSimulationButton);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);
        startSimulationButton.setPrefWidth(200);
        startSimulationButton.setPrefHeight(40);
        startSimulationButton.setOnAction(onButtonClick);
    }

    public VBox getReaderUI(){
        return this.mainContainer;
    }
//    Generate a simulation with walled map based on the input
    public Simulation generateWallMapSimulation(){
        AbstractWorldMap map = new WallMap(
                mapProps.getMapWidth(),
                mapProps.getMapHeight(),
                mapProps.getJungleRatio(),
                (int) (Utils.windowWidth*0.3)
        );
        return new Simulation(
                map,
                mapProps.getAnimalNumber(),
                mapProps.getGrassNumber(),
                mapProps.getStartEnergy(),
                mapProps.getMoveEnergy(),
                mapProps.getPlantEnergy(),
                mapProps.getIsMagical(),
                mapProps.getMoveDelay(),
                0
        );
    }

//    Generates a simulation with walled map
    public Simulation generateRolledMapSimulation(){
        AbstractWorldMap map = new RolledMap(
                mapProps.getMapWidth(),
                mapProps.getMapHeight(),
                mapProps.getJungleRatio(),
                (int) (Utils.windowWidth*0.3)
        );
        Simulation sim;
        return new Simulation(
                map,
                mapProps.getAnimalNumber(),
                mapProps.getGrassNumber(),
                mapProps.getStartEnergy(),
                mapProps.getMoveEnergy(),
                mapProps.getPlantEnergy(),
                mapProps.getIsMagical(),
                mapProps.getMoveDelay()
                ,1
        );
    }
}
