package agh.ics.oop.dataTypes;

import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.Grass;
import agh.ics.oop.objects.IMapElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

// Modified HashMap, which uses ArrayList in order to store multiple elements at positions
public class CustomHashMap extends HashMap<Vector2d, ArrayList<IMapElement>> {
//    Add an element to the array list at the position
    public void cPut(Vector2d position, IMapElement element) {
        if (this.get(position) != null) {
            ArrayList<IMapElement> arr = this.get(position);
            arr.add(element);
        } else {
            ArrayList<IMapElement> arr = new ArrayList<>();
            arr.add(element);
            this.put(position, arr);
        }
    }
//  Remove element from an array at the specified position
    public void cRemove(Vector2d position, IMapElement element) {
        if (this.get(position) != null) {
            ArrayList<IMapElement> arr = this.get(position);
            arr.remove(element);
            if (arr.size() == 0) {
                this.remove(position);
            }
        }
    }
// Finds the strongest animal at the provided position
    public ArrayList<Animal> getStrongest(Vector2d position) {
        ArrayList<IMapElement> elements = this.get(position);
        ArrayList<Animal> strongestAnimals = new ArrayList<>();
//        Return empty ArrayList if there are no animals at the position
        if (elements == null)
            return strongestAnimals;

        int highestEnergy = -9999;
        for (IMapElement element : elements) {
            if (element instanceof Animal) {
                if (highestEnergy < ((Animal) element).getEnergy()) {
                    strongestAnimals = new ArrayList<>();
                    strongestAnimals.add((Animal) element);
                } else if (highestEnergy == ((Animal) element).getEnergy()) {
                    strongestAnimals.add((Animal) element);
                }
            }
        }
        return strongestAnimals;
    }

//    Fids the two strongest animal
    public ArrayList<Animal> getTwoStrongest(Vector2d position){
        ArrayList<IMapElement> elements = this.get(position);
        if (elements == null)
            return null;
        ArrayList<Animal> strongestAnimals = new ArrayList<>();
        strongestAnimals.add(null);
        strongestAnimals.add(null);
        int highestEnergy = -1;
        int secondHighestEnergy = -1;
        for (IMapElement element : elements) {
            if (element instanceof Animal) {
                Animal animal = (Animal) element;
                if (animal.getEnergy() > highestEnergy) {
                    strongestAnimals.set(1, strongestAnimals.get(0));
                    strongestAnimals.set(0, animal);
                    secondHighestEnergy = highestEnergy;
                    highestEnergy = animal.getEnergy();
                } else if (animal.getEnergy() == highestEnergy || animal.getEnergy() > secondHighestEnergy) {
                    strongestAnimals.set(1, (Animal) element);
                    secondHighestEnergy = animal.getEnergy();
                }
            }
        }
        return strongestAnimals;
    }

//    Check if grass at position exists, if so return it.
    public Grass grassAt(Vector2d position) {
        ArrayList<IMapElement> elements = this.get(position);
        for (IMapElement element : elements) {
            if (element instanceof Grass) {
                return (Grass) element;
            }
        }
        return null;
    }

//  Removes all provided elements from provided positions
    public void removeAll(ArrayList<Vector2d> keys, ArrayList<IMapElement> elements) {
        for (int i = 0; i < keys.size(); i++) {
            this.cRemove(keys.get(i), elements.get(i));
        }
    }

//    Adds all provided elements at provided positons
    public void putAll(ArrayList<Vector2d> keys, ArrayList<IMapElement> elements) {
        for (int i = 0; i < keys.size(); i++) {
            this.cPut(keys.get(i), elements.get(i));
        }
    }
}
