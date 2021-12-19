package agh.ics.oop.gui;

import agh.ics.oop.Utils;
import agh.ics.oop.objects.AbstractWorldMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.IMapElement;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class AnimalStatsTracker {
    private final AbstractWorldMap map;
    private final VBox mainContainer = new VBox();
    private Animal target;
    private final Text titleText = new Text("Animal Statistics Tracker");
    private final Text trackingNameText = new Text("");
    private final Text childrenNoText = new Text("");
    private final Text allDescendantsText = new Text("");
    private final Text isDeadText = new Text("");
    private final Text genomeText = new Text("");
    private int childrenSinceStart = 0;
    private int allDescendants = 0;
    public AnimalStatsTracker(AbstractWorldMap map){
        this.map = map;
        VBox statsContainer = new VBox();
        statsContainer.getChildren().addAll(
                trackingNameText,
                genomeText,
                isDeadText,
                childrenNoText,
                allDescendantsText
        );
        statsContainer.setAlignment(Pos.CENTER);
        statsContainer.setSpacing(10);
        mainContainer.getChildren().addAll(titleText, statsContainer);
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(10,10,100,10));
        mainContainer.setStyle("-fx-background-color: #7bc75d;");
    }
    public void setupTracker(EventTarget eventTarget){
        if (eventTarget instanceof LinkedImageView && ((LinkedImageView) eventTarget).getRepresentedElement() instanceof Animal){
            for (ArrayList<IMapElement> elements: map.getMapElements().values()) {
                for (IMapElement element:elements) {
                    if ( element instanceof Animal){
                        ((Animal) element).setDescendandOfTracked(false);
                    }
                }
            }
            target = ((Animal) ((LinkedImageView) eventTarget).getRepresentedElement());
            target.setDescendandOfTracked(true);
            childrenSinceStart = 0;
            allDescendants = 0;

            setupTrackerUI();
        }
    }
    private void setupTrackerUI(){
        Font titleFont = new Font(Utils.windowWidth*0.02);
        Font dataFont = new Font(Utils.windowWidth * 0.01);
        titleText.setFont(titleFont);
        trackingNameText.setFont(dataFont);
        childrenNoText.setFont(dataFont);
        allDescendantsText.setFont(dataFont);
        isDeadText.setFont(dataFont);
        genomeText.setFont(dataFont);
        String aOrAn = Set.of('A', 'E', 'I', 'O', 'U').contains(target.getName().charAt(0)) ? "an ": "a ";
        trackingNameText.setText("You are tracking " + aOrAn + target.getName());
        isDeadText.setText(target.getName() + " " + "Is still alive :))");
        genomeText.setText(target.getName() + "s genome: " + target.getGenotypeString());
        updateTrackerUI();
    }
    public void updateTrackerUI(){
        this.childrenNoText.setText("Children Count: " + childrenSinceStart);
        this.allDescendantsText.setText("All Descendants Count: " + allDescendants);
    }
    public void updateOnNewborn(Animal father, Animal mother, Animal newborn){
        if (father.equals(target) || mother.equals(target)){
            childrenSinceStart += 1;
        }
        if (father.isDescendandOfTracked() || mother.isDescendandOfTracked()){
            newborn.setDescendandOfTracked(true);
            allDescendants += 1;
        }
        updateTrackerUI();
    }
    public void updateOnDeath(Animal deceased, int epoch){
        if (deceased.equals(target)){
            isDeadText.setText(target.getName() + " " + String.format("Is has died on epoch number %d.", epoch));
            updateTrackerUI();
        }
    }
    public VBox getUI(){
        return this.mainContainer;
    }
}
