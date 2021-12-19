package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.objects.Animal;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.TextAlignment;


class StatsChart{
    final VBox container;
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    final XYChart.Series series = new XYChart.Series();
    final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
    private final String name;
    public StatsChart(String name, String colour){
        this.name = name;
        series.setName(name);
        lineChart.setCreateSymbols(false);
        lineChart.animatedProperty().set(false);
        lineChart.getData().add(series);
        Label label = new Label(name);
        label.setFont(new Font(15));
        lineChart.setMinHeight(10);
        container = new VBox(label, lineChart);
        container.setAlignment(Pos.CENTER);
        series.getNode().lookup(".chart-series-line").setStyle(String.format("-fx-stroke: %s;", colour));
    }
    public void addData(int x, int y){
        series.getData().add(new XYChart.Data(x, y));
    }
    public Node getUI(){
        return container;
    }
}

public class StatsChartManager {
    VBox mainContainer = new VBox();
    VBox chartsContainer = new VBox();
    private final StatsChart animalChart = new StatsChart("Animals No." , "#ffbe0b");
    private final StatsChart plantChart = new StatsChart("Number Of Plants", "#fb5607");
    private final StatsChart energyChart = new StatsChart("Average Energy", "#ff006e");
    private final StatsChart lifespanChart = new StatsChart("Average Lifespan", "#8338ec");
    private final StatsChart childrenChart = new StatsChart("Number Of Children", "#3a86ff");
    private StatsChart[] allCharts = {animalChart, plantChart, energyChart, lifespanChart, childrenChart};

    private int animalsNumber = 0;
    private int plantNumber = 0;
    private int energySum = 0;
    private int lifetimeSum = 0;
    private int lifetimeSamples = 1;
    private int childrenCountSum = 0;

    public StatsChartManager(){
        UISetup();
    }
    private void UISetup(){
        for (StatsChart chart:allCharts) {
            chartsContainer.getChildren().add(chart.getUI());
        }
        mainContainer.setMaxHeight(Utils.windowHeight);
        mainContainer.setPadding(new Insets(10,0,30,0));
        mainContainer.setPrefWidth(Utils.windowWidth*0.16);
        mainContainer.setAlignment(Pos.CENTER);
        Text label = new Text("Whole simulation \n stats tracker");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(new Font(Utils.windowWidth * 0.013));
        mainContainer.getChildren().addAll(label, chartsContainer);
        mainContainer.setStyle("-fx-background-color: #eeb79b;");
    }

    public void chartUpdate(int epoch){
        Platform.runLater(()->{
            animalChart.addData(epoch, animalsNumber);
            plantChart.addData(epoch, plantNumber);
            energyChart.addData(epoch, energySum/animalsNumber);
            lifespanChart.addData(epoch, lifetimeSum/lifetimeSamples);
            childrenChart.addData(epoch, childrenCountSum);
        });
    }
    public void resetEpochStats(){
        animalsNumber = 1;
        plantNumber = 1;
        energySum = 0;
        childrenCountSum = 0;
    }
    public void readAliveAnimalData(Animal animal){
        animalsNumber += 1;
        childrenCountSum += animal.getChildren().size();
        energySum += animal.getEnergy();
    }
    public void readDataOnAnimalDeath(Animal animal, int epoch){
        lifetimeSum += epoch-animal.getBirthEpoch();
        lifetimeSamples += 1;
    }

    public void addPlantCount(){
        plantNumber += 1;
    }
    public Node getUI(){
        return this.mainContainer;
    }
}
