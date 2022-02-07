package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.simulation.Simulation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

// Main application class
public class App extends Application {
    Scene scene;
    Simulation wallSimulation;
    Simulation rolledSimulation;
//    Creates a reader for initial settings for the maps.
    SimulationPropsReader simulationPropsReader = new SimulationPropsReader(event -> switchToSimulationView());
    HBox mainContainer = new HBox();
    public void init(){
//        Preloads the textures to the Utils module
        Utils.loadImages();
//        Sets up the ui
        mainContainer.getChildren().addAll(simulationPropsReader.getReaderUI());
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setMaxWidth(1000);

    }
    @Override
    public void start(Stage primaryStage) {
        scene = new Scene(mainContainer, 1280, 720);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

//        Close the entire app when clicking the window "x"
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

//        Load the width and height to the utils
        Utils.windowWidth = (int) primaryStage.getWidth();
        Utils.windowHeight = (int) primaryStage.getHeight();
    }
//    Methods that creates simulations based on user input
    private void switchToSimulationView(){
        try{
            wallSimulation = simulationPropsReader.generateWallMapSimulation();
            rolledSimulation = simulationPropsReader.generateRolledMapSimulation();
            mainContainer.getChildren().clear();
            mainContainer.getChildren().addAll(wallSimulation.getUI(), rolledSimulation.getUI());
        } catch (IllegalArgumentException ex){
            errorPopup(ex.getMessage());
        }
    }
    private void errorPopup(String message){
//        Button exitButton = new Button("Exit");
        ButtonType okButton = new ButtonType("Try Again", ButtonBar.ButtonData.OK_DONE);
        ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.ERROR, "", okButton, exitButton);
        alert.setContentText("");
        alert.setHeaderText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(exitButton)){
            System.out.println(message);
            System.exit(1);
        }


    }
}
