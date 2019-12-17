package main.java.com.chyb.Entity;

import main.java.com.chyb.WorldMap;
import main.java.com.chyb.utils.Vector2D;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    private static Random random = new Random();
    private final WorldMap wMap;
    private int[] genome;
    private Vector2D position;
    private int direction;
    private int stamina;

    public Animal(Vector2D position, WorldMap wMap, int startingStamina) {
        this.position = new Vector2D(position.x, position.y);
        stamina = startingStamina;
        this.wMap = wMap;
        genome = new int[32];
        randomizeGenome();
    }
    public Animal(Vector2D position, Animal parent1, Animal parent2, WorldMap wMap){
        this.position = new Vector2D(position.x, position.y);
        this.wMap = wMap;
        genome = new int[32];
        inheritGenome(parent1.getGenome(), parent2.getGenome());

    }
    public void move(){
        direction = (direction + genome[random.nextInt(32)]) % 8;

        Vector2D target;
        switch(direction){
            case 0:
                target = new Vector2D(0,1);
                break;
            case 1:
                target = new Vector2D(1,1);
                break;
            case 2:
                target = new Vector2D(1,0);
                break;
            case 3:
                target = new Vector2D(1,-1);
                break;
            case 4:
                target = new Vector2D(0,-1);
                break;
            case 5:
                target = new Vector2D(-1,-1);
                break;
            case 6:
                target = new Vector2D(-1,0);
                break;
            case 7:
                target = new Vector2D(-1,1);
                break;
            default:
                target= new Vector2D(2,2);
        }

        Vector2D oldPosition = position;
        position = position.add(target);
        position = position.mod(wMap.getSize());
        wMap.moveAnimal(this, oldPosition, position);

        stamina--;

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
            genome[i] = random.nextInt(8);
            if(genome[i]<0) System.out.print('o');
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
                if (!contains[genome[g]]){
                    missingGene = genome[g];
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

    public int getEnergy() {
        return stamina;
    }

    @Override
    public int compareTo(Animal other) {
        return this.getEnergy() - other.getEnergy();
    }

    public void addEnergy(int plantEnergy) {
        stamina += plantEnergy;
    }
}
