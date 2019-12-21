package com.chyb.entities;

import com.chyb.map.WorldMapSimulator;
import com.chyb.utils.MapDirection;
import com.chyb.utils.Vector2D;

import java.util.Random;

public class Animal implements Comparable<Animal> {

    private static Random random = new Random();
    private final WorldMapSimulator wMap;
    private AnimalGenome genome;
    private Vector2D position;
    private MapDirection direction;
    private int childrenNumber;
    private int energy;
    private int age;

    public Animal(Vector2D position, WorldMapSimulator wMap, int startingStamina) {
        this(position, wMap);
        genome = new AnimalGenome();
        energy = startingStamina;
    }
    public Animal(Vector2D position, Animal parent1, Animal parent2, WorldMapSimulator wMap){
        this(position, wMap);
        genome = new AnimalGenome(parent1.getGenome(), parent2.getGenome());
        energy = parent1.popQuarterEnergy() + parent2.popQuarterEnergy();
    }
    private Animal(Vector2D position, WorldMapSimulator wMap){
        this.position = new Vector2D(position.x, position.y);
        this.wMap = wMap;
        direction = MapDirection.N.rotateByValue(random.nextInt(8));
        childrenNumber = 0;
        age = 0;
    }
    public void move(int moveEnergy){
        direction = (direction.rotateByValue(genome.getRandomGenomeValue()));

        Vector2D target = direction.unitVector.copy();

        Vector2D oldPosition = position;
        position = position.add(target);
        position = position.mod(wMap.getSize());
        wMap.animalMoved(this, oldPosition, position);

        energy -= moveEnergy;
        age++;
    }

    public Vector2D getPosition(){
        return position;
    }

    private AnimalGenome getGenome() {
        return genome;
    }

    public int getEnergy() {
        return energy;
    }

    public MapDirection getMapDirection(){
        return direction;
    }

    @Override
    public int compareTo(Animal other) {
        return this.getEnergy() - other.getEnergy();
    }

    public void addEnergy(int plantEnergy) {
        energy += plantEnergy;
    }

    private int popQuarterEnergy() {
        int quarterEnergy = energy/4;
        energy -= quarterEnergy;
        return quarterEnergy;
    }
    public void addedItsChild(){
        childrenNumber++;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public int getAge() {
        return age;
    }
}
