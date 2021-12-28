package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.simulation.Simulation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
//        primaryStage.setMaximized(true);
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
        }catch (NumberFormatException ex){
            System.out.println("Please input data in number format!");
            System.exit(1);
        }catch (IllegalArgumentException ex){
            System.out.println("Please input data that doesn't exceed sensible values!");
            System.exit(1);
        }

    }

}
