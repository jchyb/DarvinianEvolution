package com.chyb;

public class Animal {
    private Random random;
    public int[] genome;
    Vector2D position;
    private int direction;
    public Animal(Vector2D position) {
        this.position = new Vector2D(position.x, position.y);
        random = new Random();
        genome = new genome[32];
        randomizeGenome();
    }
    public Animal(Animal parent1, Animal parent2){


    }
    private void randomizeGenome(){
        for(int i = 0; i<32; i++){
            genome[i] = random.nextInt() % 8;
        }
        repairGenome();
    }
    private repairGenome(){
        int[] contains = contains[8];
        while(true){
            for (int i = 0; i < 8; i++) contains[i] = false;
            for (int g : genome) contains[g] = true;
            missingGene = -1;
            for (int g = 0; g < 32; g++) {
                if (!contains[i]){
                    missingGene = i;
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
}
