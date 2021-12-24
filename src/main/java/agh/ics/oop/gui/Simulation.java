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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Simulation implements IMapChangeObserver {
    private final AbstractWorldMap map;
    private final SimulationEngine engine;
    private final AnimalStatsTracker animalStatsTracker;
    private final StatsChartManager statsChartManager;
    private final GridPane gridPane = new GridPane();
    private final Label magicSpawnsLabel;
    private final Thread engineThread;
    private HBox mainContainer;
    private final VBox mapAndStatsContainer = new VBox();
    private final int type;
    private final int mapSizePx = (int) (Utils.windowWidth*0.3);
    public Simulation(AbstractWorldMap map, int startAnimals, int startGrass, int startEnergy, int moveCost, int plantEnergy, boolean isMagical, int type){
        this.map = map;
        statsChartManager = new StatsChartManager(map);
        this.type = type;
        int[] mapProps = map.getMapProps();
        animalStatsTracker = new AnimalStatsTracker(this);
        this.engine = new SimulationEngine(map, startAnimals, 1, startEnergy, startGrass, moveCost, plantEnergy, isMagical, statsChartManager, animalStatsTracker);

        engine.addObserver(this);
        engineThread = new Thread(engine);

        EventHandler<MouseEvent> eventHandler = e -> {
            if (engine.isSuspended()){
                animalStatsTracker.setupTracker(e.getTarget());
            }
        };
        gridPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        magicSpawnsLabel = engine.isMagical() ? new Label("Magic spawns : 0") : null;

        setupUI();
    }
    private void setupUI(){
        Button pauseButton = new Button("Start");
        pauseButton.setPrefWidth(Utils.windowWidth*0.10);
        pauseButton.setPrefHeight(Utils.windowWidth*0.02);
        pauseButton.setOnAction(e -> {
            if (!engineThread.isAlive()){
                engineThread.start();
                pauseButton.setText("Pause");
            }else if (!engine.isSuspended()){
                engine.suspend();
                pauseButton.setText("Continue");
            }else{
                statsChartManager.deHighlightGenome();
                animalStatsTracker.highlightTracked();
                engine.resume();
                pauseButton.setText("Pause");
            }

        });
//        Button for highlighting the animals with dominant Genome
        Button genomeHighlightButton = new Button("Highlight Genome");
        genomeHighlightButton.setPrefWidth(Utils.windowWidth*0.10);
        genomeHighlightButton.setPrefHeight(Utils.windowWidth*0.02);
        genomeHighlightButton.setOnAction((e) ->{
            if (engine.isSuspended()){
                if (!statsChartManager.highlighted()){
                    statsChartManager.highlightGenome();
                }else{
                    statsChartManager.deHighlightGenome();
                    animalStatsTracker.highlightTracked();
                }
            }
        });
        Button saveButton = new Button("Save history to file");
        saveButton.setPrefWidth(Utils.windowWidth*0.10);
        saveButton.setPrefHeight(Utils.windowWidth*0.02);
        saveButton.setOnAction((e)->{
            if (engine.isSuspended()){
                statsChartManager.saveToFile();
            }
        });

        HBox buttonsBox = new HBox(pauseButton, genomeHighlightButton, saveButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setStyle("-fx-background-color: #b05dc7;");
//        If the simulation is magical add the counter to the UI
        if (engine.isMagical()){
            mapAndStatsContainer.getChildren().addAll(gridPane, buttonsBox, magicSpawnsLabel, animalStatsTracker.getUI());
        }else {
            mapAndStatsContainer.getChildren().addAll(gridPane, buttonsBox, animalStatsTracker.getUI());
        }

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
//            Redraw the map and set the magic spawns counter UI
            gridPane.setGridLinesVisible(false);
            gridPane.getChildren().clear();
            map.renderGrid(gridPane, mapSizePx);
            if (engine.isMagical())
                magicSpawnsLabel.setText("Magic spawns: " + engine.getMagicSpawns());
        }, null);
        Platform.runLater(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
    }

    public SimulationEngine getEngine() {
        return engine;
    }

    public AbstractWorldMap getMap() {
        return map;
    }
}
