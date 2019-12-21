package com.chyb;

import com.chyb.entities.Animal;

public class StatisticsTracker {
    private final WorldMap wMap;

    public StatisticsTracker(WorldMap wMap){
        this.wMap = wMap;
    }
    public int getNumberOfAnimals(){
        return wMap.getAnimalList().size();
    }
    public int getNumberOfPlants(){
        return wMap.getPlantsAmount();
    }
    public float getEnergyAverage(){
        int energySum = 0;
        for(Animal animal : wMap.getAnimalList()){
            energySum += animal.getEnergy();
        }
        return (float)(energySum)/wMap.getAnimalList().size();
    }
}
