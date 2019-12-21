package com.chyb;

import com.chyb.entities.Animal;
import com.chyb.entities.Plant;
import com.chyb.utils.Vector2D;

import java.util.*;

public class WorldMap {

    private final int startEnergy, moveEnergy, plantEnergy;
    private final int jungleX, jungleY;
    private final int width, height;
    private final int jungleWidth, jungleHeight;
    private HashMap<Vector2D, LinkedList<Animal> > animalMap;
    private HashMap<Vector2D, Plant> plantMap;
    private ArrayList<Animal> animalList;
    private static Random random = new Random();

    public WorldMap(WorldMapConfig config){
        this.width = config.width;
        this.height = config.height;
        this.jungleWidth = (int)((double)config.width * Math.sqrt(config.jungleRatio));
        jungleX = (width-jungleWidth)/2;
        this.jungleHeight = (int)((double)config.height * Math.sqrt(config.jungleRatio));
        jungleY = (height-jungleHeight)/2;
        this.startEnergy = config.startEnergy;
        this.moveEnergy = config.moveEnergy;
        this.plantEnergy = config.plantEnergy;
        animalList = new ArrayList<Animal>();
        animalMap = new HashMap<Vector2D, LinkedList<Animal> >();
        plantMap = new HashMap<Vector2D, Plant>();
    }
    public void generateStartingAnimals(int amount){
        for(int i = 0; i < amount; i++){
            while(true){
                Vector2D newPosition = new Vector2D(random.nextInt()% width, random.nextInt() % height);
                if(!animalMap.containsKey(newPosition)){
                    Animal animal = new Animal(newPosition,this,startEnergy);
                    LinkedList<Animal> ll = new LinkedList<Animal>();
                    ll.add(animal);
                    animalMap.put(newPosition, ll);
                    animalList.add(animal);
                    break;
                }
            }
        }
    }
    private void addPlants(int amountPerZone){
        for(int i = 0; i<amountPerZone; i++){
            boolean foundSpot = false;
            int randomX=0, randomY=0;
            int missedAmount = 0;

            //TODO REDO ?
            while(!foundSpot && missedAmount < width * height) {
                int randomInt = random.nextInt(width*height - jungleWidth*jungleHeight);
                Vector2D randomVector;

                randomVector = new Vector2D(randomInt % width, randomInt / width);
                randomInt -= width*(jungleY);

                if(randomInt >= 0){
                    int randX = randomInt % (width - jungleWidth);
                    int randY = randomInt / (width - jungleWidth);
                    if(randX >= jungleX) randX += jungleWidth;
                    randomVector = new Vector2D(randX, jungleY + randY);
                    randomInt -= (width - jungleWidth) *(jungleHeight);
                }
                if(randomInt>=0){
                    randomVector = new Vector2D(randomInt % (width),jungleY + jungleHeight + randomInt / width);
                }
                foundSpot = !isOccupiedByAnimal(randomVector) && !isOccupiedByPlant(randomVector);
                missedAmount++;
                if(foundSpot) plantMap.put(randomVector.copy(), new Plant(randomVector.copy()));
            }

            //TODO refactor
            //jungleAdd

            foundSpot = false;
            missedAmount = 0;
            while(!foundSpot && missedAmount < jungleWidth*jungleHeight) {
                randomX = random.nextInt(jungleWidth) + jungleX;
                randomY = random.nextInt(jungleHeight) + jungleY;
                Vector2D randomVector = new Vector2D(randomX, randomY);
                foundSpot = !isOccupiedByAnimal(randomVector) && !isOccupiedByPlant(randomVector);
                missedAmount++;
            }
            if(foundSpot) plantMap.put(new Vector2D(randomX, randomY), new Plant(new Vector2D(randomX,randomY)));
        }
    }

    public boolean isOccupiedByPlant(Vector2D position) {
        return plantMap.containsKey(position);
    }

    private Animal addChild(Vector2D centralPosition, Animal parent1, Animal parent2){
        if(parent2.getEnergy()<startEnergy/2) return null;
        int childEnergy;
        childEnergy = parent1.popQuarterEnergy();
        childEnergy += parent2.popQuarterEnergy();

        ArrayList<Vector2D> openSpaces = new ArrayList<Vector2D>();

        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                Vector2D newPosition = centralPosition.add(new Vector2D(i, j)).mod(getSize());
                if(!isOccupiedByAnimal(centralPosition.add(new Vector2D(i, j)))){
                    openSpaces.add(newPosition);
                }
            }
        }
        if(openSpaces.isEmpty()) return null;
        Vector2D childPosition = openSpaces.get(random.nextInt(openSpaces.size()) );

        Animal animal = new Animal(childPosition, parent1, parent2, this);
        animal.addEnergy(childEnergy);
        return animal;
    }
    public void eatPlants(){
        ArrayList<Vector2D> toRemove = new ArrayList<Vector2D>();
        for(Vector2D plantPosition : plantMap.keySet()){
            if(!animalMap.containsKey(plantPosition) || animalMap.get(plantPosition).isEmpty()) continue;
            toRemove.add(plantPosition);
            LinkedList<Animal> eatingAnimals = animalMap.get(plantPosition);
            ArrayList<Animal> bestAnimals = new ArrayList<Animal>();

            int bestEnergy = -1;
            for(Animal animal : eatingAnimals){
                if(bestEnergy < animal.getEnergy()){
                    bestAnimals.clear();
                    bestAnimals.add(animal);
                    bestEnergy = animal.getEnergy();
                } else if(bestEnergy == animal.getEnergy()){
                    bestAnimals.add(animal);
                }
            }
            if(bestEnergy>0) {
                for (Animal animal : bestAnimals) {
                    animal.addEnergy((int) plantEnergy / bestAnimals.size());
                }
            }
        }
        for(Vector2D plantPosition : toRemove){
            plantMap.remove(plantPosition);
        }
    }
    public void handleBirths(){
        ArrayList<Animal> animalsToAdd = new ArrayList<>();
        for(Vector2D animalPosition : animalMap.keySet()){
            LinkedList<Animal> animalLl = animalMap.get(animalPosition);
            if(animalLl.size()>1) {
                Collections.sort(animalLl);
                Animal child = addChild(animalPosition, animalLl.get(0), animalLl.get(1));
                if(child != null){
                    animalsToAdd.add(child);
                }
            }
        }
        for(Animal child : animalsToAdd){
            animalList.add(child);
            if(!animalMap.containsKey(child.getPosition())) {
                animalMap.put(child.getPosition(), new LinkedList<Animal>());
            }
            animalMap.get(child.getPosition()).add(child);
        }
    }
    public void cycle(){
        for(int i=0;i<animalList.size();i++){
            Animal animal = animalList.get(i);
            if(animal.getEnergy() <= 0){
                LinkedList<Animal> animalLL = animalMap.get(animal.getPosition());
                animalLL.remove(animal);
                animalList.remove(i);
                i--;
                continue;
            }
            animal.move(moveEnergy);
        }
        eatPlants();
        handleBirths();
        addPlants(1);
    }

    public void animalMoved(Animal animal, Vector2D oldPosition, Vector2D newPosition) {
        //remove from oldPosition
        LinkedList<Animal> animalLl = animalMap.get(oldPosition);
        animalLl.remove(animal);

        //add to newPosition
        if(animalMap.containsKey(newPosition)){
            animalMap.get(newPosition).add(animal);
        }else{
            animalMap.put(newPosition, new LinkedList<Animal>(Collections.singleton(animal)));
        }
    }
    private boolean isOccupiedByAnimal(Vector2D position){
        if(! animalMap.containsKey(position)) return false;
        else return !animalMap.get(position).isEmpty();
    }

    public Vector2D getSize() {
        return new Vector2D(width,height);
    }

    public ArrayList<Animal> getAnimalList() {
        return animalList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getPlantsAmount() {
        return plantMap.size();
    }
}