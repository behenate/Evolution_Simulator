package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    public static void main(String[] args) {
        try{
//        Zamiast run wywołuję run and show, które robi dokładnie to co run, tylko pokazuje informacje z punku 7.
//            engine.runAndShow();
            Application.launch(App.class, args);
        }catch (IllegalArgumentException e){
            System.out.print(e.getMessage());
            System.exit(1);
        }
    }
}