package com.chyb.entities;

import java.util.Random;

public class AnimalGenome {
    private int[] genomeValues;
    static Random random = new Random();

    public AnimalGenome(){
        genomeValues = new int[32];
        randomizeGenome();
    }
    public AnimalGenome(AnimalGenome genome1, AnimalGenome genome2){
        genomeValues = new int[32];
        inheritGenome(genome1.getGenomeValues(), genome2.getGenomeValues());
    }

    private int[] getGenomeValues() {
        return genomeValues;
    }

    private void inheritGenome(int[] parentGenome1, int[] parentGenome2) {
        int partLeft = random.nextInt() % 29 + 1;
        int partRight = partLeft + random.nextInt() % (31 - partLeft) + 1;

        for(int i = 0; i < 32; i++){
            if(i < partLeft) genomeValues[i] = parentGenome1[i];
            else if(i<partRight) genomeValues[i] = parentGenome2[i];
            else genomeValues[i] = parentGenome1[i];
        }
        repairGenome();
    }

    private void randomizeGenome(){
        for(int i = 0; i<32; i++){
            genomeValues[i] = random.nextInt(8);
            if(genomeValues[i]<0) System.out.print('o');
        }
        repairGenome();
    }
    private void repairGenome(){
        boolean[] contains = new boolean[8];
        while(true){
            for (int i = 0; i < 8; i++) contains[i] = false;
            for (int g : genomeValues) contains[g] = true;
            int missingGene = -1;
            for (int g = 0; g < 32; g++) {
                if (!contains[genomeValues[g]]){
                    missingGene = genomeValues[g];
                    break;
                }
            }
            if(missingGene == -1) break;
            genomeValues[random.nextInt() % 32] = missingGene;
        }
    }
    public int getRandomGenomeValue(){
        return genomeValues[random.nextInt(32)];
    }

}
