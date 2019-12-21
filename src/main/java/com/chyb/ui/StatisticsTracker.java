package com.chyb.ui;

import com.chyb.WorldMapSimulator;
import com.chyb.entities.Animal;

public class StatisticsTracker {
    private final WorldMapSimulator wMap;
    private float energyAverage;
    private float childrenAverage;
    private float averageAge;
    private int deadAnimalsAmount;
    private int sumOfAge;

    public StatisticsTracker(WorldMapSimulator wMap){
        this.wMap = wMap;
        deadAnimalsAmount = 0;
        sumOfAge = 0;
    }
    public int getNumberOfAnimals(){
        return wMap.getAnimalList().size();
    }
    public int getNumberOfPlants(){
        return wMap.getPlantsAmount();
    }
    private void recalculateEnergyAverage(){
        int energySum = 0;
        for(Animal animal : wMap.getAnimalList()){
            energySum += animal.getEnergy();
        }
        energyAverage = (float)(energySum)/wMap.getAnimalList().size();
    }

    private void recalculateChildrenAverage() {
        int childrenSum = 0;
        for(Animal animal : wMap.getAnimalList()){
            childrenSum += animal.getChildrenNumber();
        }
        childrenAverage = (float) (childrenSum) / wMap.getAnimalList().size();
    }

    private void recalculateAverageAge() {
        averageAge = (float)(sumOfAge) / deadAnimalsAmount;
    }

    private void addDeadAnimals() {
        for(Animal animal : wMap.getAnimalList()){
            if(animal.getEnergy() <= 0) {
                deadAnimalsAmount ++;
                sumOfAge += animal.getAge();
            }
        }
    }
    public void update() {
        recalculateEnergyAverage();
        recalculateChildrenAverage();
        addDeadAnimals();
        recalculateAverageAge();
    }
    public float getChildrenAverage() {
        return childrenAverage;
    }
    public float getAverageEnergy() {
        return energyAverage;
    }

    public float getAverageAge() {
        return averageAge;
    }
}
