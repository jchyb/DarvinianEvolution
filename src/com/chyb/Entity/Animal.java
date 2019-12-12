package com.chyb.Entity;

import com.chyb.utils.Vector2D;

import java.util.Random;

public class Animal {

    static Random random = new Random();
    public int[] genome;
    Vector2D position;
    private int direction;
    private int stamina;

    public Animal(Vector2D position) {
        this.position = new Vector2D(position.x, position.y);
        genome = new int[32];
        randomizeGenome();
    }
    public Animal(Animal parent1, Animal parent2, Vector2D position){
        this.position = new Vector2D(position.x, position.y);
        genome = new int[32];
        inheritGenome(parent1.getGenome(), parent2.getGenome());
    }

    private void inheritGenome(int[] parentGenome1, int[] parentGenome2) {
        int partLeft = random.nextInt() % 29 + 1;
        int partRight = partLeft + random.nextInt() % (31 - partLeft) + 1;

        for(int i = 0; i < 32; i++){
            if(i < partLeft) genome[i] = parentGenome1[i];
            else if(i<partRight) genome[i] = parentGenome2[i];
            else genome[i] = parentGenome1[i];
        }
        repairGenome();
    }

    private void randomizeGenome(){
        for(int i = 0; i<32; i++){
            genome[i] = random.nextInt() % 8;
        }
        repairGenome();
    }
    private void repairGenome(){
        boolean[] contains = new boolean[8];
        while(true){
            for (int i = 0; i < 8; i++) contains[i] = false;
            for (int g : genome) contains[g] = true;
            int missingGene = -1;
            for (int g = 0; g < 32; g++) {
                if (!contains[g]){
                    missingGene = g;
                    break;
                }
            }
            if(missingGene == -1) break;
            genome[random.nextInt() % 32] = missingGene;
        }
    }
    public Vector2D getPosition(){
        return position;
    }

    private int[] getGenome() {
        return genome;
    }
}
