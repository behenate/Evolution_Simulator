package agh.ics.oop.objects;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.Vector2d;
import com.sun.javafx.UnmodifiableArrayList;
import javafx.scene.image.Image;

import java.util.ArrayList;


public class Animal extends AbstractWorldMapElement {
    private final IWorldMap map;
    private int mapDirection;
    private final int[][] directionVectors = {{0,1}, {1,1}, {1,0}, {1,-1}, {0, -1}, {-1,-1},{-1,0}, {-1,1}};
    private final Image[] images = new Image[8];
    private final int[] genotype = new int[32];
    private int energy;
    private final int moveCost;
    private final int birthEpoch;
    private final String name = Utils.getRandomAnimalName();
    private boolean descendandOfTracked = false;
    ArrayList<Animal> children = new ArrayList<>();
    public Animal(IWorldMap map, Vector2d initialPosition, int startEnergy, int moveCost, Animal father, Animal mother, int birthEpoch){
        this.birthEpoch = birthEpoch;
        this.energy = startEnergy;
        this.map = map;
        this.moveCost = moveCost;
        this.position = initialPosition;
        mapDirection = Utils.getRandomNumber(0, 8);
        for (int i = 0; i < 8; i++) {
            images[i] = new Image(String.format("%d.png", i));
        }
        if (father != null && mother != null){
            generateGenotype(father, mother);
            this.energy = (int) (father.getEnergy() * 0.25 + mother.getEnergy() * 0.25);
            father.addEnergy((int)(-father.getEnergy() * 0.25));
            mother.addEnergy((int)(-mother.getEnergy() * 0.25));
            father.addChild(this);
            mother.addChild(this);
        }else {
            for (int i = 0; i < genotype.length; i++) {
                genotype[i] = Utils.getRandomNumber(0 ,8);
            }
        }
    }
    public void genotypeMove(){
        int moveIdx = Utils.getRandomNumber(0, genotype.length);
        move(genotype[moveIdx]);
    }
    public void move(int direction){
        Vector2d newPos = position;
        Vector2d forwardMoveVector = new Vector2d(directionVectors[mapDirection][0], directionVectors[mapDirection][1]);
        switch (direction){
            case 0 -> newPos = position.add(forwardMoveVector);
            case 4 -> newPos = position.add(forwardMoveVector.opposite());
            default -> mapDirection = (mapDirection+direction)%8;
        }
        newPos = new Vector2d(newPos.x % map.getMapProps()[0], newPos.y % map.getMapProps()[1]);
        if (map.canMoveTo(newPos)){
            if (newPos.x < 0){
                newPos = new Vector2d(map.getMapProps()[0] -1 , newPos.y);
            }
            if (newPos.y < 0){
                newPos = new Vector2d(newPos.x, map.getMapProps()[1] -1);
            }
            positionChanged(this, newPos);
            position = newPos;
        }
        this.energy -= this.moveCost;
    }

    private void generateGenotype(Animal father, Animal mother){
        Animal stronger = (father.getEnergy() > mother.getEnergy()) ? father : mother;
        Animal weaker = (father.getEnergy() <= mother.getEnergy()) ? father : mother;

        int side = Utils.getRandomNumber(0,2);
        int genesFromStronger = Math.round((float)stronger.getEnergy() / (stronger.getEnergy() + weaker.getEnergy()) * genotype.length);
        int i = 0;
        int iter_dir = 1;
        if (side == 1){
            i = genotype.length-1;
            iter_dir = -1;
        }
        for (int j = 0; j < genesFromStronger; j++) {
            genotype[j] = stronger.getGenotype()[i];
            System.out.print(stronger.getGenotype()[i]);
            i += iter_dir;

        }
        for (int j = genesFromStronger; j < genotype.length; j++) {
            genotype[j] = weaker.getGenotype()[i];
            i+= iter_dir;
        }
//        Żeby nie liczyć zbyt wielu dzieci, dziecko jest przypisane do silniejszego rodzica
    }

    public void setDescendandOfTracked(boolean descendandOfTracked) {
        this.descendandOfTracked = descendandOfTracked;
    }
    public boolean isDescendandOfTracked(){
        return this.descendandOfTracked;
    }

    public void die(int epoch){
        System.out.println("Ugh i died on: " + epoch);
    }
    @Override
    public Image getImage() {
        return  images[mapDirection];
    }
    public int getEnergy(){
        return energy;
    }
    public void addEnergy(int value){
        energy += value;
    }
    public int[] getGenotype(){
        return genotype;
    }
    public String getGenotypeString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < genotype.length; i++) {
            str.append(genotype[i]);
        }
        return str.toString();
    }
    public ArrayList<Animal> getChildren(){
        return this.children;
    }
    public void setPosition(Vector2d position){
        this.position = position;
    }
    public void addChild(Animal child){
        this.children.add(child);
    }
    public String getName(){
        return this.name;
    }
    public int getBirthEpoch(){
        return birthEpoch;
    }


}
