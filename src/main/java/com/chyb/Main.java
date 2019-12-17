package com.chyb;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static int width, height;
    public static int startEnergy;
    public static int moveEnergy;
    public static int plantEnergy;
    public static int jungleRatio;

    public static void main(String[] args) throws InterruptedException {
        //default

        width = 50;
        height = 50;
        startEnergy = 20;
        moveEnergy = 1;
        plantEnergy = 20;

        WorldMap wMap = new WorldMap(width,height,10,10);
        wMap.generateStartingAnimals(10);
        initGraphics(wMap);
        JFrame f = new JFrame("Swing Paint Demo");
        f.setSize(250*3,250*3);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new WorldDraw(wMap));

        //f.add(new JButton("button"));
        f.setVisible(true);

        while(true){
            wMap.cycle();
            f.repaint();
            System.out.print("ok");
            Thread.sleep(100);
        }

    }

    private static void initGraphics(WorldMap wMap) {

    }
}
