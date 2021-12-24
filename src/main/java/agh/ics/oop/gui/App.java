package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.jshell.execution.Util;

public class App extends Application {
    Scene scene;
    Simulation wallSimulation;
    Simulation rolledSimulation;
    SimulationPropsReader simulationPropsReader = new SimulationPropsReader(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            switchToSimulationView();
        }
    });
    HBox mainContainer = new HBox();
    public void init(){
        Utils.loadImages();
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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

//        Load the width and height to the utils
        Utils.windowWidth = (int) primaryStage.getWidth();
        Utils.windowHeight = (int) primaryStage.getHeight();

//        switchToSimulationView();
    }
    private void switchToSimulationView(){
        wallSimulation = simulationPropsReader.generateWallMapSimulation();
        rolledSimulation = simulationPropsReader.generateRolledMapSimulation();
        mainContainer.getChildren().clear();
        mainContainer.getChildren().addAll(wallSimulation.getUI(), rolledSimulation.getUI());
    }

}
