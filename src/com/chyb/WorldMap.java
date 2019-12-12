package com.chyb;

import com.chyb.Entity.Animal;
import com.chyb.Entity.Plant;
import com.chyb.utils.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class WorldMap {

    int width, height;
    HashMap<Vector2D, Animal> animalMap;
    HashMap<Vector2D, Plant> plantMap;
    ArrayList<Animal> animalList;
    static Random random = new Random();

    public WorldMap(int width, int height){
        this.width = width;
        this.height = height;
        animalList = new ArrayList<Animal>();
        animalMap = new HashMap<Vector2D, Animal>();
        plantMap = new HashMap<Vector2D, Plant>();
    }

    private void generateAnimals(int amount){

    }

    private void addPlants(){

    }
    private void addChild(Vector2D position){
        ArrayList<Vector2D> openSpaces = new ArrayList<Vector2D>();

        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                Vector2D newPosition = position.add(new Vector2D(i, j);
                if(!isOccupiedByAnimal(position.add(new Vector2D(i, j)))){
                    openSpaces.add(newPosition);
                }
            }
        }

        if(openSpaces.isEmpty()) return;
        Vector2D childPosition = openSpaces.get(random.nextInt() % openSpaces.size());

    }
    public void cycle(){
        
    }

    private boolean isOccupiedByAnimal(Vector2D position){
        return animalMap.containsKey(position);
    }
}