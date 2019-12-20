package com.chyb;

public class StatisticsTracker {
    private final WorldMap wMap;

    public StatisticsTracker(WorldMap wMap){
        this.wMap = wMap;
    }
    public int getNumberOfAnimals(){
        return wMap.getAnimalList().size();
    }

}
