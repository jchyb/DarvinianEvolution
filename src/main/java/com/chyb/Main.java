package com.chyb;




import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

        WorldMap wMap = new WorldMap(width,height,30,30, startEnergy, moveEnergy,plantEnergy);
        wMap.generateStartingAnimals(150);

        initGraphics(wMap);

        WorldDraw wd = new WorldDraw(wMap);

        final JPanel gui = new JPanel(new BorderLayout(10,10));
        gui.setBorder( new TitledBorder("Simulation") );

        JPanel pane = new JPanel(new GridLayout(1,5));
        JPanel optComponents = new JPanel( new FlowLayout(FlowLayout.RIGHT, 3,3));
        optComponents.setBorder( new TitledBorder("Options") );
        optComponents.add(new JButton("Start"));
        optComponents.add(new JButton("Stop"));
        JSlider slider = new JSlider();

        slider.setBorder(new TitledBorder("Simulation Speed"));
        int[] stopTime = {200};

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                stopTime[0] = slider.getValue();
            }
        });
        optComponents.add(slider);

        pane.setBorder(new TitledBorder("Options"));

        JPanel first = new JPanel( new FlowLayout(FlowLayout.RIGHT, 3,3));
        JScrollPane scroll = new JScrollPane(wd);

        first.add(scroll);
        JButton non= new JButton("lolololoo");
        //TODO
        first.add(non);
        non.setBorder(new TitledBorder("Stats"));

        pane.add(first);
        gui.add(optComponents);
        gui.add(pane,BorderLayout.NORTH);
        JFrame f = new JFrame("World Sim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(gui);
        f.pack();
        f.setVisible(true);

        while(true){
            wMap.cycle();
            f.repaint();
            Thread.sleep(stopTime[0]);
        }
    }
    private static void initGraphics(WorldMap wMap) {

    }
}
