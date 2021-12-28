package agh.ics.oop.simulation;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.LinkedImageView;
import agh.ics.oop.maps.AbstractWorldMap;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.IMapElement;
import agh.ics.oop.simulation.Simulation;
import javafx.event.EventTarget;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Set;

// Tracker for the specific animal stats
public class AnimalStatsTracker {
    private final AbstractWorldMap map;
    private final VBox mainContainer = new VBox();
    private Animal target;
    private final Text trackingNameText = new Text("");
    private final Text childrenNoText = new Text("");
    private final Text allDescendantsText = new Text("");
    private final Text isDeadText = new Text("");
    private final Text genomeText = new Text("");
    Text titleText = new Text("");
    Button trackButton = new Button();
    private int childrenSinceStart = 0;
    private int allDescendants = 0;
    private boolean is_tracking = false;
    private int death_epoch = -1;
    public AnimalStatsTracker(Simulation simulation){
        this.map = simulation.getMap();

        //Setup the UI elements
        VBox statsContainer = new VBox();
        statsContainer.getChildren().addAll(
                trackingNameText,
                genomeText,
                isDeadText,
                childrenNoText,
                allDescendantsText,
                trackButton
        );

        statsContainer.setAlignment(Pos.CENTER);
        statsContainer.setSpacing(10);
        mainContainer.getChildren().addAll(titleText, statsContainer);
        mainContainer.setSpacing(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(10,10,100,10));
        mainContainer.setStyle("-fx-background-color: #7bc75d;");

        Font titleFont = new Font(Utils.windowWidth*0.02);
        Font dataFont = new Font(Utils.windowWidth * 0.01);
        titleText.setFont(titleFont);
        trackingNameText.setFont(dataFont);
        childrenNoText.setFont(dataFont);
        allDescendantsText.setFont(dataFont);
        isDeadText.setFont(dataFont);
        genomeText.setFont(dataFont);
        trackButton.setVisible(false);
    }
//    Method called when and element is clicked by mouse
    public void setupTracker(EventTarget eventTarget){
//        Check if Animal was tracked, setup tracker and highlight it, else disable the tracker
        if (eventTarget instanceof LinkedImageView &&
                ((LinkedImageView) eventTarget).getRepresentedElement() instanceof Animal){
            animalTrackerCleanup();
            target = ((Animal) ((LinkedImageView) eventTarget).getRepresentedElement());
            target.setDescendantOfTracked(true);
            childrenSinceStart = 0;
            allDescendants = 0;
            trackButton.setVisible(true);
            target.highlight();
            is_tracking = false;
            switchToPreviewUI();
        }else
            disableTracker();
    }

//    UI showing only basic inormation about the Animal
    private void switchToPreviewUI(){
        showElements();
        titleText.setText("Animal Stats Preview");
        childrenNoText.setText("");
        allDescendantsText.setText("");
        isDeadText.setText("");
        genomeText.setText("");
        death_epoch = -1;
//        aOrAn makes sure the sentence is gramatically correct
        String aOrAn = Set.of('A', 'E', 'I', 'O', 'U').contains(target.getName().charAt(0)) ? "an ": "a ";
        trackingNameText.setText("You are previewing " + aOrAn + target.getName());
        genomeText.setText(target.getName() + "s genome: " + target.getGenotypeString());

        trackButton.setText("Track The Chosen Animal");
        trackButton.setOnAction((event)->{
                trackButton.setText("Stop Tracking");
                is_tracking = true;
                switchToTrackerUI();
        });
    }

//    Switches to detailed tracking of chosen animal
    private void switchToTrackerUI(){
        titleText.setText("Animal Stats Tracker");
        String aOrAn = Set.of('A', 'E', 'I', 'O', 'U').contains(target.getName().charAt(0)) ? "an ": "a ";
        trackingNameText.setText("You are tracking " + aOrAn + target.getName());
        genomeText.setText(target.getName() + "s genome: " + target.getGenotypeString());

        trackButton.setOnAction(event -> disableTracker());
        updateTrackerUI();
    }

//    Disables the tracker
    private void disableTracker(){
        hideElements();
        animalTrackerCleanup();
        target = null;
    }

//    Called when crucial information has to be updated
    public void updateTrackerUI(){
        this.childrenNoText.setText("Children Count: " + childrenSinceStart);
        this.allDescendantsText.setText("All Descendants Count: " + allDescendants);
        if (death_epoch != -1)
            isDeadText.setText(target.getName() + " " + String.format("Is has died on epoch number %d :((", death_epoch));
        else
            isDeadText.setText(target.getName() + " " + "Is still alive :))");
    }

//    Method that adds the newborn to the animal statistics
    public void updateOnNewborn(Animal father, Animal mother, Animal newborn){
        if ((father.equals(target) || mother.equals(target)) && is_tracking){
            childrenSinceStart += 1;
            updateTrackerUI();
        }
        if ((father.isDescendantOfTracked() || mother.isDescendantOfTracked())&& is_tracking){
            newborn.setDescendantOfTracked(true);
            allDescendants += 1;
            updateTrackerUI();
        }

    }

//    Method notifying of animal's death.
    public void updateOnDeath(Animal deceased, int epoch){
        if (deceased.equals(target)){
            death_epoch = epoch;
            if (is_tracking)
                updateTrackerUI();
            else
                trackButton.setVisible(false);
        }
    }

//    Hides all UI elements of the tracker
    public void hideElements(){
        for (Node node: mainContainer.getChildren()) {
            node.setVisible(false);
        }
    }

//    Shows all UI elements of the tracker
    public void showElements(){
        for (Node node: mainContainer.getChildren()) {
            node.setVisible(true);
        }
    }

//    Cleans up the side effects of the tracker running
    private void animalTrackerCleanup(){
        for (ArrayList<IMapElement> elements: map.getMapElements().values()) {
            for (IMapElement element:elements) {
                if ( element instanceof Animal){
                    ((Animal) element).setDescendantOfTracked(false);
                    ((Animal) element).deHighlight();
                }
            }
        }
    }

//    Highlights the animal that is currently being tracked
    public void highlightTracked(){
        if (target != null){
            target.highlight();
        }
    }
    public VBox getUI(){
        return this.mainContainer;
    }
}
