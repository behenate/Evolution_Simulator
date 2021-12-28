package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.maps.RolledMap;
import agh.ics.oop.simulation.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class ReaderBox {
    protected String defaultValue;
    protected String title;
    private final TextField textField;
    private final float valueLow;
    private final float valueHigh;
//  Simple box that stores the value and the allowed limits for said value
    public ReaderBox(String defaultValue, String title, float valueLow, float valueHigh) {
        this.defaultValue = defaultValue;
        this.title = title;
        textField = new TextField(defaultValue);
        this.valueLow = valueLow;
        this.valueHigh = valueHigh;
    }
//  function that gets the value and checks if input data is correct
    public float getValue() {
        float value = 0;
        try {
            value = Float.parseFloat(textField.getText());
            if (value < valueLow) {
                throw new IllegalArgumentException("Value: " + value + " is too low for the " + title.toLowerCase() + " field!");
            } else if (value > valueHigh) {
                throw new IllegalArgumentException("Value: " + value + " is too high for the " + title.toLowerCase() + " field!");
            }
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Provide data in numerical value for the" + title.toLowerCase() + " field!");
        }
        return value;
    }
//  Returns a box with the title and the field
    public Node getUI() {
        VBox toRet = new VBox(new Label(title  + ":") , textField);
        toRet.setAlignment(Pos.CENTER);
        return toRet;
    }
}

// Class that reads the data and creates two simulations based on it
// Splitting into two classes made sense when both simulations had different inputs, now
public class SimulationPropsReader {
    VBox inputContainer = new VBox();
    VBox mainContainer = new VBox();
    Button startSimulationButton = new Button("Start Simulation");

    //    Create the input fields
    ReaderBox mapWidth = new ReaderBox("10", "Width of the map", 2, 1000);
    ReaderBox mapHeight = new ReaderBox("10", "Width of the map", 2, 1000);
    ReaderBox animalNumber = new ReaderBox("20", "Number of animals", 0, Integer.MAX_VALUE);
    ReaderBox grassNumber = new ReaderBox("20", "Number of start carrots", 0, Integer.MAX_VALUE);
    ReaderBox startEnergy = new ReaderBox("10", "Start energy", 1, Integer.MAX_VALUE);
    ReaderBox moveEnergy = new ReaderBox("1", "Move cost", 0, Integer.MAX_VALUE);
    ReaderBox plantEnergy = new ReaderBox("8", "Energy bonus from eating a carrot", 0, Integer.MAX_VALUE);
    ReaderBox jungleRatio = new ReaderBox("0.3", "Jungle to field ratio", 0, 1);
    ReaderBox animationStepDelay = new ReaderBox("50", "Animation step in ms", 1, Integer.MAX_VALUE);
    CheckBox isMagicalWall = new CheckBox();
    CheckBox isMagicalRoll = new CheckBox();
    //    On start create window and create new reader
    public SimulationPropsReader(EventHandler<ActionEvent> onButtonClick) {
        VBox isMagicalWallContainer = new VBox(new Label("Is the walled map magical?"), isMagicalWall);
        VBox isMagicalRolledContainer = new VBox(new Label("Is the rolled map magical?"), isMagicalRoll);
        isMagicalWallContainer.setAlignment(Pos.CENTER);
        isMagicalRolledContainer.setAlignment(Pos.CENTER);HBox checkboxContainer = new HBox(isMagicalWallContainer, isMagicalRolledContainer);
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setMaxWidth(250);
        mainContainer.setPrefWidth(350);
        checkboxContainer.setSpacing(20);
        checkboxContainer.setAlignment(Pos.CENTER);
//      Add fields to the input container
        inputContainer.getChildren().addAll(
                mapWidth.getUI(),
                mapHeight.getUI(),
                animalNumber.getUI(),
                grassNumber.getUI(),
                startEnergy.getUI(),
                moveEnergy.getUI(),
                plantEnergy.getUI(),
                jungleRatio.getUI(),
                animationStepDelay.getUI()
        );
        mainContainer.getChildren().addAll(inputContainer,checkboxContainer, startSimulationButton);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);
        startSimulationButton.setPrefWidth(200);
        startSimulationButton.setPrefHeight(40);
        startSimulationButton.setOnAction(onButtonClick);
    }

    public VBox getReaderUI() {
        return this.mainContainer;
    }

    //    Generate a simulation with walled map based on the input
    public Simulation generateWallMapSimulation() {
        return createSimulation(0);
    }

    //    Generates a simulation with walled map
    public Simulation generateRolledMapSimulation() {
        return createSimulation(1);
    }

    //      Create a simlation of the specified type
    private Simulation createSimulation(int type) {
        AbstractWorldMap map = new RolledMap(
                (int) mapWidth.getValue(),
                (int) mapHeight.getValue(),
                jungleRatio.getValue(),
                (int) (Utils.windowWidth * 0.3)
        );
        boolean isMagical = (isMagicalRoll.selectedProperty().get() && type ==1 || isMagicalWall.selectedProperty().get() && type == 0);

        return new Simulation(
                map,
                (int) animalNumber.getValue(),
                (int) grassNumber.getValue(),
                (int) startEnergy.getValue(),
                (int) moveEnergy.getValue(),
                (int) plantEnergy.getValue(),
                isMagical,
                (int) animationStepDelay.getValue()
                , type
        );
    }
}

