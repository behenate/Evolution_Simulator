package agh.ics.oop.objects;

import agh.ics.oop.Utils;
import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.LinkedImageView;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.gui.GuiElementBox;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;


// Class describing an Animal on the map
public class Animal extends AbstractWorldMapElement {
    private int mapDirection;
//    Define moves based on animal direction
    private final int[][] directionVectors = {{0,1}, {1,1}, {1,0}, {1,-1}, {0, -1}, {-1,-1},{-1,0}, {-1,1}};

    private Genome genome = new Genome(new int[32]);
    private int energy;
    private final int startEnergy;
    private final int moveCost;
    private final int birthEpoch;
    private final Animal father;
    private final Animal mother;

//
    private final String name = Utils.getRandomAnimalName();
    private boolean descendantOfTracked = false;
    private final LinkedImageView healthBarImageView = new LinkedImageView(Utils.healthBarImage, this);

//    Animal's children
    ArrayList<Animal> children = new ArrayList<>();

    public Animal(AbstractWorldMap map, Vector2d initialPosition, int startEnergy,
                  int moveCost, Animal father, Animal mother, int birthEpoch, Genome genome){
//        Set up the animal
        super(initialPosition, map);
        this.birthEpoch = birthEpoch;
        this.energy = startEnergy;
        this.moveCost = moveCost;
        this.startEnergy = startEnergy;
        this.father = father;
        this.mother = mother;

        mapDirection = Utils.getRandomNumber(0, 8);
        this.guiElementBox = new GuiElementBox(this, map.gridCellSize);

        int[] genomeArr = new int[32];

//        If the genome was provided apply it, otherwise generate random one or one based on parents if they were provided
        if (genome != null){
            this.genome = genome;
        }else if (father != null && mother != null){
            generateGenome(father, mother);
            this.energy = (int) (father.getEnergy() * 0.25 + mother.getEnergy() * 0.25);
            father.addEnergy((int)(-father.getEnergy() * 0.25));
            mother.addEnergy((int)(-mother.getEnergy() * 0.25));
            father.addChild(this);
            mother.addChild(this);
        }else {
            for (int i = 0; i < genomeArr.length; i++) {
                genomeArr[i] = Utils.getRandomNumber(0 ,8);
            }
            Arrays.sort(genomeArr);
            this.genome = new Genome(genomeArr);
        }
    }

//    Do a random move based on the genotype
    public void genotypeMove(){
        int moveIdx = Utils.getRandomNumber(0, genome.getGenomeArr().length);
        move(genome.getGenomeArr()[moveIdx]);
    }

//    Move in the provided direction
    public void move(int direction){
        Vector2d newPos = position;
        Vector2d forwardMoveVector = new Vector2d(directionVectors[mapDirection][0], directionVectors[mapDirection][1]);
        switch (direction){
            case 0 -> newPos = position.add(forwardMoveVector);
            case 4 -> newPos = position.add(forwardMoveVector.opposite());
            default -> mapDirection = (mapDirection+direction)%8;
        }
//        Move the animal based on input and do some ugly hard-coded properties for walled and rolled maps
        if (map.canMoveTo(newPos)){
            newPos = new Vector2d(newPos.x % map.getMapProps()[0], newPos.y % map.getMapProps()[1]);
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

//    Generates a genome based on father and mother
    private void generateGenome(Animal father, Animal mother){
        Animal stronger = (father.getEnergy() > mother.getEnergy()) ? father : mother;
        Animal weaker = (father.getEnergy() <= mother.getEnergy()) ? father : mother;
        int[] genomeArr = genome.getGenomeArr();
        int side = Utils.getRandomNumber(0,2);
        int genesFromStronger = Math.round((float)stronger.getEnergy() /
                (stronger.getEnergy() + weaker.getEnergy()) * genomeArr.length);
        int i = 0;
        int iter_dir = 1;
        if (side == 1){
            i = genomeArr.length-1;
            iter_dir = -1;
        }
        for (int j = 0; j < genesFromStronger; j++) {
            genomeArr[j] = stronger.getGenome().getGenomeArr()[i];
            i += iter_dir;

        }
        for (int j = genesFromStronger; j < genome.getGenomeArr().length; j++) {
            genomeArr[j] = weaker.getGenome().getGenomeArr()[i];
            i+= iter_dir;
        }
        Arrays.sort(genomeArr);
//        Żeby nie liczyć zbyt wielu dzieci, dziecko jest przypisane do silniejszego rodzica
    }

//    Sets a flag for tracking
    public void setDescendantOfTracked(boolean descendantOfTracked) {
        this.descendantOfTracked = descendantOfTracked;
    }
    public boolean isDescendantOfTracked(){
        return this.descendantOfTracked;
    }


//    Some self-explanatory getters and setters
    @Override
    public Image getImage() {
        return  Utils.animalImages[mapDirection];
    }
    public LinkedImageView getHealthBarImageView(){return healthBarImageView;}
    public int getEnergy(){
        return energy;
    }
    public void addEnergy(int value){
        energy += value;
    }
    public Genome getGenome(){
        return genome;
    }
    public String getGenotypeString(){
        return genome.getGenomeString();
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

    public void highlight(){
        guiElementBox.highlight();
    }

    public void deHighlight(){
        guiElementBox.deHighlight();
    }
    public Animal getFather(){
        return father;
    }
    public Animal getMother(){
        return mother;
    }
    public int getMapDirection(){return mapDirection;}
    public float getEnergyPercentage(){
        if (this.energy <= 0 ){
            return 0;
        }
        return (float)this.energy/this.startEnergy;
    }
}
