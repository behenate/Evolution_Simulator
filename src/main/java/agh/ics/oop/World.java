package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

//The creme de la creme 
public class World {
    public static void main(String[] args) {
        try{
//            Start the application
            Application.launch(App.class, args);
        }catch (IllegalArgumentException e){
            System.out.print(e.getMessage());
            System.exit(1);
        }
    }
}