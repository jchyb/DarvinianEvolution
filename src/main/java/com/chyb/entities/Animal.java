package com.chyb.entities;

import com.chyb.WorldMap;
import com.chyb.utils.MapDirection;
import com.chyb.utils.Vector2D;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    private static Random random = new Random();
    private final WorldMap wMap;
    private AnimalGenome genome;
    private Vector2D position;
    private MapDirection direction;
    private int stamina;

    public Animal(Vector2D position, WorldMap wMap, int startingStamina) {
        this.position = new Vector2D(position.x, position.y);
        stamina = startingStamina;
        this.wMap = wMap;
        direction = MapDirection.N.rotateByValue(random.nextInt(8));
        genome = new AnimalGenome();
    }
    public Animal(Vector2D position, Animal parent1, Animal parent2, WorldMap wMap){
        this.position = new Vector2D(position.x, position.y);
        this.wMap = wMap;
        direction = MapDirection.N.rotateByValue(random.nextInt(8));
        genome = new AnimalGenome(parent1.getGenome(), parent2.getGenome());
        stamina = 0;
    }
    public void move(int moveEnergy){
        direction = (direction.rotateByValue(genome.getRandomGenomeValue()));

        Vector2D target = direction.unitVector.copy();

        Vector2D oldPosition = position;
        position = position.add(target);
        position = position.mod(wMap.getSize());
        wMap.animalMoved(this, oldPosition, position);

        stamina -= moveEnergy;
    }

    public Vector2D getPosition(){
        return position;
    }

    private AnimalGenome getGenome() {
        return genome;
    }

    public int getEnergy() {
        return stamina;
    }

    public MapDirection getMapDirection(){
        return direction;
    }

    @Override
    public int compareTo(Animal other) {
        return this.getEnergy() - other.getEnergy();
    }

    public void addEnergy(int plantEnergy) {
        stamina += plantEnergy;
    }

    public int popQuarterEnergy() {
        int quarterEnergy = stamina/4;
        stamina -= quarterEnergy;
        return quarterEnergy;
    }
}
