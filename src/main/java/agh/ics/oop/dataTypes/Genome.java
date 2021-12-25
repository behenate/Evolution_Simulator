package agh.ics.oop.dataTypes;

import java.util.Arrays;

// Datatype for storing an animals genome
public class Genome {
    private final int[] genome;
    public Genome(int[] genome){
        this.genome = genome;
    }

    public int[] getGenomeArr() {
        return genome;
    }
//    Converts the genome to string
    public String getGenomeString(){
        StringBuilder str = new StringBuilder();
        for (int j : genome) {
            str.append(j);
        }
        return str.toString();
    }
//    Creates correct equals and hashcode for storing genomes in a HashMap
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
