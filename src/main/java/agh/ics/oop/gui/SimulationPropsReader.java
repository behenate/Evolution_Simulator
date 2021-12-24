package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.objects.AbstractWorldMap;
import agh.ics.oop.objects.IWorldMap;
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
import jdk.jshell.execution.Util;

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
    CheckBox isMagical = new CheckBox();
    TextField [] fields = {mapWidthField, mapHeightField, animalNumberField,grassNumberField,startEnergyField, moveEnergyField, plantEnergyField, jungleRatioField};
    String[] fieldsDescriptions = { "Width of the map: ", "Height of the map: ","Start number of animals: ",
            "Start number of grass tufts: ","Animal start energy:", "Energy cost of an animal move: ",
            "Energy bonus from eating a tuft of grass: ", "Jungle to field ratio:"
    };
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

    public int getMapWidth(){
        return Integer.parseInt(mapWidthField.getText());
    }
    public int getMapHeight(){
        return Integer.parseInt(mapHeightField.getText());
    }
    public int getStartEnergy(){
        return Integer.parseInt(startEnergyField.getText());
    }
    public int getAnimalNumber(){
        return Integer.parseInt(animalNumberField.getText());
    }
    public int getGrassNumber(){
        return Integer.parseInt(grassNumberField.getText());
    }
    public int getMoveEnergy(){
        return Integer.parseInt(moveEnergyField.getText());
    }
    public int getPlantEnergy(){
        return Integer.parseInt(plantEnergyField.getText());
    }
    public float getJungleRatio(){
        return Float.parseFloat(jungleRatioField.getText());
    }
    public boolean getIsMagical(){
        return isMagical.selectedProperty().get();
    }
    public VBox getSubReaderUI(){
        return this.mainContainer;
    }
}
public class SimulationPropsReader {
    HBox inputContainer = new HBox();
    VBox mainContainer = new VBox();
    SimulationPropsSubReader wallMapProps = new SimulationPropsSubReader("Walled Map Properties");
    SimulationPropsSubReader rolledMapProps = new SimulationPropsSubReader("Rolled Map Properties");
    Button startSimulationButton = new Button("Start Simulation");
    public SimulationPropsReader(EventHandler<ActionEvent> onButtonClick){
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setPrefWidth(1920);
        inputContainer.setSpacing(50);
        inputContainer.getChildren().addAll(wallMapProps.getSubReaderUI(), rolledMapProps.getSubReaderUI());
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
    public Simulation generateWallMapSimulation(){
        AbstractWorldMap map = new WallMap(
                wallMapProps.getMapWidth(),
                wallMapProps.getMapHeight(),
                wallMapProps.getJungleRatio(),
                (int) (Utils.windowWidth*0.3)
        );
        return new Simulation(
                map,
                wallMapProps.getAnimalNumber(),
                wallMapProps.getGrassNumber(),
                wallMapProps.getStartEnergy(),
                wallMapProps.getMoveEnergy(),
                wallMapProps.getPlantEnergy(),
                wallMapProps.getIsMagical(),
                0
        );
    };
    public Simulation generateRolledMapSimulation(){
        AbstractWorldMap map = new RolledMap(
                rolledMapProps.getMapWidth(),
                rolledMapProps.getMapHeight(),
                rolledMapProps.getJungleRatio(),
                (int) (Utils.windowWidth*0.3)
        );
        return new Simulation(
                map,
                rolledMapProps.getAnimalNumber(),
                rolledMapProps.getGrassNumber(),
                rolledMapProps.getStartEnergy(),
                rolledMapProps.getMoveEnergy(),
                rolledMapProps.getPlantEnergy(),
                rolledMapProps.getIsMagical()
                ,1
        );
    };
    public SimulationPropsSubReader getRolledMapProps(){
        return this.rolledMapProps;
    }

}
