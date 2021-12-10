package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class App extends Application implements IMapChangeObserver {
    AbstractWorldMap map;
    SimulationEngine engine;
    Scene scene;
    GridPane gridPane;
    public void init(){

        this.map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,3) };
        this.engine = new SimulationEngine(map, positions, 300);
        engine.addObserver(this);

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        gridPane= new GridPane();

        TextField moveDirectionsText = new TextField("f f f f r r f f f f f f f ");
        Button engineStartButton = new Button("Start with provided moves");

//        Przypnij wydarzenie uruchomienia symulacji
        engineStartButton.setOnAction(e -> {
            ArrayList<MoveDirection> directions = new OptionsParser().parse(
                    moveDirectionsText.getText().split(" ")
            );
            engine.setMoveArray(directions);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });

//        Włóż elementy do kontenerów
        HBox movesInputBox = new HBox( moveDirectionsText, engineStartButton);
        VBox mainContainer = new VBox(gridPane, movesInputBox);

        mainContainer.setAlignment(Pos.CENTER);
        HBox.setMargin(moveDirectionsText, new Insets(5, 10, 0, 10));
        HBox.setMargin(engineStartButton, new Insets(5, 10, 0, 10));
        map.renderGrid(gridPane);
        scene = new Scene(mainContainer, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void mapChanged() {
        Platform.runLater(()->{
            gridPane.setGridLinesVisible(false);
            gridPane.getChildren().clear();
            map.renderGrid(gridPane);
        });


    }
    public void handleSimulationButtonClick(){

    }
}
