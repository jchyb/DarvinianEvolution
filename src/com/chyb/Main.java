package com.chyb;

public class Main {

    public static int width, height;
    public static int startEnergy;
    public static int moveEnergy;
    public static int plantEnergy;
    public static int jungleRatio;

    public static void main(String[] args) {
        //default

        width = 50;
        height = 50;
        startEnergy = 10;
        moveEnergy = 1;
        plantEnergy = 4;

        WorldDraw wMap = new WorldMap(width,height,10,10);


    }
}
