package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.AbstractWorldMap;
import agh.ics.oop.simulation.IMapChangeObserver;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Simulation implements IMapChangeObserver {
    private AbstractWorldMap map;
    private SimulationEngine engine;
    private AnimalStatsTracker animalStatsTracker;
    private StatsChartManager statsChartManager = new StatsChartManager();
    private GridPane gridPane = new GridPane();
    private Thread engineThread;
    private HBox mainContainer;
    private VBox mapAndStatsContainer = new VBox();
    private int type;
    private int mapSizePx = (int) (Utils.windowWidth*0.3);
    public Simulation(AbstractWorldMap map, int startAnimals, int startGrass, int startEnergy, int moveCost, int plantEnergy, boolean isMagical, int type){
        this.map = map;
        this.type = type;
        animalStatsTracker = new AnimalStatsTracker(map);

        int[] mapProps = map.getMapProps();
        Vector2d[] positions = new Vector2d[startAnimals];
        for (int i = 0; i < startAnimals; i++) {
            int x = Utils.getRandomNumber(0, mapProps[0]);
            int y = Utils.getRandomNumber(0, mapProps[1]);
            positions[i] = new Vector2d(x,y);
        }
        this.engine = new SimulationEngine(map, positions, 20, startEnergy, moveCost, plantEnergy, isMagical, statsChartManager, animalStatsTracker);
        map.placeGrassSteppe(startGrass/2);
        map.placeGrassJungle(startGrass/2);
        engine.addObserver(this);


        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                animalStatsTracker.setupTracker(e.getTarget());
            }
        };
        gridPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        setupUI();
    }
    private void setupUI(){
        Button pause = new Button("Start");
        pause.setPrefWidth(Utils.windowWidth*0.15);
        pause.setPrefHeight(Utils.windowWidth*0.02);
        pause.setOnAction(e -> {
            if (engineThread == null){
                engineThread = new Thread(engine);
                engineThread.start();
                pause.setText("Pause");
            }else if (!engine.isSuspended()){
                engineThread.suspend();
                engine.suspend();
                pause.setText("Continue");
            }else{
                engineThread.resume();
                engine.resume();
                pause.setText("Pause");
            }

        });
        HBox movesInputBox = new HBox(pause);
        movesInputBox.setAlignment(Pos.CENTER);
        movesInputBox.setStyle("-fx-background-color: #b05dc7;");
        mapAndStatsContainer.getChildren().addAll(gridPane, movesInputBox, animalStatsTracker.getUI());
        if (type == 0){
            mainContainer = new HBox(statsChartManager.getUI(), mapAndStatsContainer);
        }else{
            mainContainer = new HBox(mapAndStatsContainer, statsChartManager.getUI());
        }
        mapAndStatsContainer.setAlignment(Pos.CENTER);
        map.renderGrid(gridPane, mapSizePx);
    }
    public Pane getUI() {
//        Wrapuję w pane, bo inaczej lubi się wylewać poza ekran
        return new Pane(this.mainContainer);
    }

    @Override
    public void mapChanged() {
//      Przed kontynuacją wątku Simulation Engine poczekaj na koniec przerysowywania UI
        FutureTask<Object> futureTask = new FutureTask<>(()->{
            gridPane.setGridLinesVisible(false);
            gridPane.getChildren().clear();
            map.renderGrid(gridPane, mapSizePx);
        }, null);

        try {
            Platform.runLater(futureTask);
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
