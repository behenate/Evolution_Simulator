package agh.ics.oop.simulation;

import agh.ics.oop.Utils;
import agh.ics.oop.maps.AbstractWorldMap;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

// Class that creates a whole simulation including the fully functional UI for it.
public class Simulation implements IMapChangeObserver {
//    Define the neccessary variables
    private final AbstractWorldMap map;
    private final SimulationEngine engine;
    private final AnimalStatsTracker animalStatsTracker;
    private final StatsManager statsChartManager;
    private final GridPane gridPane = new GridPane();
    private final Label magicSpawnsLabel;
    private final Thread engineThread;
    private HBox mainContainer;
    private final VBox mapAndStatsContainer = new VBox();
    private final int type;
    Button genomeHighlightButton = new Button("Highlight Genome");
    Button saveButton = new Button("Save history to file");
    public Simulation(AbstractWorldMap map, int startAnimals, int startGrass, int startEnergy, int moveCost, int plantEnergy, boolean isMagical, int moveDelay, int type){
        this.map = map;
        this.type = type;
//        Create new simulation statisticts  collection tool and animal tracking tool
        statsChartManager = new StatsManager(map);
        animalStatsTracker = new AnimalStatsTracker(this);
//        Create new simulation engine
        this.engine = new SimulationEngine(map,
                startAnimals,
                moveDelay,
                startGrass,
                startEnergy,
                moveCost,
                plantEnergy, isMagical, statsChartManager, animalStatsTracker);

        engine.addObserver(this);
        engineThread = new Thread(engine);

//        Create Event Handler for tracking a single animal
        EventHandler<MouseEvent> eventHandler = e -> {
            if (engine.isSuspended()){
                animalStatsTracker.setupTracker(e.getTarget());
            }
        };
        gridPane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

//        If simulation is "magical" add and appropriate label
        magicSpawnsLabel = engine.isMagical() ? new Label("Magic spawns : 0") : null;
        setupUI();
    }
//    Create UI for the simulation
    private void setupUI(){
//        Button that starts and pauses the animation
        Button pauseButton = new Button("Start");
        pauseButton.setPrefWidth(Utils.windowWidth*0.10);
        pauseButton.setPrefHeight(Utils.windowWidth*0.02);
        pauseButton.setOnAction(e -> {
            if (!engineThread.isAlive()){
                engineThread.start();
                genomeHighlightButton.setVisible(false);
                saveButton.setVisible(false);
                pauseButton.setText("Pause");
            }else if (!engine.isSuspended()){
                engine.suspend();
                genomeHighlightButton.setVisible(true);
                saveButton.setVisible(true);
                pauseButton.setText("Continue");
            }else{
                statsChartManager.deHighlightAll();
                animalStatsTracker.highlightTracked();
                engine.resume();
                genomeHighlightButton.setVisible(false);
                saveButton.setVisible(false);
                pauseButton.setText("Pause");
            }

        });
//        Button for highlighting the animals with dominant Genome
        genomeHighlightButton.setPrefWidth(Utils.windowWidth*0.10);
        genomeHighlightButton.setPrefHeight(Utils.windowWidth*0.02);
        genomeHighlightButton.setVisible(false);
        genomeHighlightButton.setOnAction((e) ->{
            if (engine.isSuspended()){
                if (!statsChartManager.highlighted()){
                    statsChartManager.highlightGenome();
                }else{
                    statsChartManager.deHighlightAll();
                    animalStatsTracker.highlightTracked();
                }
            }
        });
//        Button for saving the simulation statistics
        saveButton.setPrefWidth(Utils.windowWidth*0.10);
        saveButton.setPrefHeight(Utils.windowWidth*0.02);
        saveButton.setVisible(false);
        saveButton.setOnAction((e)->{
            if (engine.isSuspended()){
                statsChartManager.saveToFile();
            }
        });
//      Create a container for the buttons
        HBox buttonsBox = new HBox(pauseButton, genomeHighlightButton, saveButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setStyle("-fx-background-color: #b05dc7;");
//        If the simulation is magical add the counter to the UI
        if (engine.isMagical()){
            mapAndStatsContainer.getChildren().addAll(gridPane, buttonsBox, magicSpawnsLabel, animalStatsTracker.getUI());
        }else {
            mapAndStatsContainer.getChildren().addAll(gridPane, buttonsBox, animalStatsTracker.getUI());
        }
//        The simulation on the left has the stats on the left, the one on the right has them on the right
        if (type == 0){
            mainContainer = new HBox(statsChartManager.getUI(), mapAndStatsContainer);
        }else{
            mainContainer = new HBox(mapAndStatsContainer, statsChartManager.getUI());
        }
        mapAndStatsContainer.setAlignment(Pos.CENTER);
        map.renderGrid(gridPane);
    }

    public Pane getUI() {
//        Insert into pane to avoid weird behaviour
        return new Pane(this.mainContainer);
    }

    @Override
    public void mapChanged() {
//      In the simulation Engine wait for the UI thread to stop drawing
        FutureTask<Object> futureTask = new FutureTask<>(()->{
//            Redraw the map and set the magic spawns counter UI
            gridPane.setGridLinesVisible(false);
            gridPane.getChildren().clear();
            map.renderGrid(gridPane);
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

    public AbstractWorldMap getMap() {
        return map;
    }
}
