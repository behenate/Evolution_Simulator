package agh.ics.oop;

import agh.ics.oop.dataTypes.Genome;
import agh.ics.oop.dataTypes.Vector2d;
import agh.ics.oop.objects.Animal;
import agh.ics.oop.objects.WallMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenomeTest {
    private WallMap map = new WallMap(10,10,0,20);
    private Animal testAnimal1 = new Animal(
            map, new Vector2d(0,0),
            24,
            0,
            null,
            null,
            0,
            new Genome(new int[32])
    );
    private Animal testAnimal2 = new Animal(
            map, new Vector2d(0,0),
            8,
            0,
            null,
            null,
            0,
            new Genome(onesGenome())
    );
    @Test
    void genomeTest(){
        Animal newAnimal = new Animal(
                map,
                new Vector2d(0,0),
                20,
                0,
                testAnimal1,
                testAnimal2,
                0,
                null
        );
        int[] newAnimalGenomeArr = newAnimal.getGenome().getGenomeArr();
        int[] possibleGenome1 = new int[]{1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] possibleGenome2 = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1};
        assertTrue(genomeArrayEquals(newAnimalGenomeArr, possibleGenome1) || genomeArrayEquals(newAnimalGenomeArr, possibleGenome2));
    }
    int[] onesGenome(){
        int[] ones = new int[32];
        for (int i = 0; i < 32; i++) {
            ones[i] = 1;
        }
        return ones;
    }
    boolean genomeArrayEquals(int[] arr1, int[] arr2){
        for (int i = 0; i < 32; i++) {
            if (arr1[i] != arr2[i])
                return false;
        }
        return true;
    }
}
