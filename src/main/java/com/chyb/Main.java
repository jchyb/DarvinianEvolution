package com.chyb;




import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static int width, height;
    public static int startEnergy;
    public static int moveEnergy;
    public static int plantEnergy;
    public static int jungleRatio;

    public static void main(String[] args) throws InterruptedException {
        //default
        JSONParser jPar = new JSONParser();

        try (FileReader reader = new FileReader("parameters.json"))
        {
            //Read JSON file
            JSONObject jSonObj = (JSONObject) jPar.parse(reader);
            System.out.println(jSonObj.get("width"));
            width = Math.toIntExact((Long) jSonObj.get("width"));
            height = Math.toIntExact((Long) jSonObj.get("height"));
            startEnergy = Math.toIntExact((Long) jSonObj.get("startEnergy"));
            moveEnergy = Math.toIntExact((Long) jSonObj.get("moveEnergy"));
            plantEnergy = Math.toIntExact((Long) jSonObj.get("plantEnergy"));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            width = 50;
            height = 50;
            startEnergy = 20;
            moveEnergy = 1;
            plantEnergy = 20;
        }


        WorldMap wMap = new WorldMap(width,height,50,40, startEnergy, moveEnergy,plantEnergy);
        wMap.generateStartingAnimals(70);

        initGraphics(wMap);
        JFrame f = new JFrame("Swing Demo");
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
