package agh.ics.oop.dataTypes;

import java.util.Arrays;
import java.util.Objects;

public class Genome {
    private int[] genome;
    public Genome(int[] genome){
        this.genome = genome;
    }

    public int[] getGenomeArr() {
        return genome;
    }
    public String getGenomeString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < genome.length; i++) {
            str.append(genome[i]);
        }
        return str.toString();
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(genome);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Genome))
            return false;
        else {
            int[] otherArr = ((Genome) obj).getGenomeArr();
            for (int i = 0; i < genome.length; i++) {
                if (genome[i] != otherArr[i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
