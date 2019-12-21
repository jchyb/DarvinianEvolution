package com.chyb;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        WorldMapConfig config = new WorldMapConfig();
        try (FileReader reader = new FileReader("parameters.json")) {
            config = new Gson().fromJson(reader, WorldMapConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WorldMap wMap = new WorldMap(config);
        wMap.generateStartingAnimals(config.animalAmount);

        StatisticsTracker statisticsTracker = new StatisticsTracker(wMap);

        WorldDraw wd = new WorldDraw(wMap);

        final JPanel gui = new JPanel(new BorderLayout(10,10));

        gui.setBorder( new TitledBorder("Simulation") );

        JPanel pane = new JPanel(new GridLayout(1,5));
        JPanel optComponents = new JPanel( new FlowLayout(FlowLayout.RIGHT, 3,3));
        optComponents.setBorder( new TitledBorder("Options") );
        JSlider slider = new JSlider();
        JButton startButton = new JButton("Pause");
        optComponents.add(startButton);

        final boolean[] isStopped = {false};
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStopped[0] = !isStopped[0];
                if(isStopped[0]) startButton.setText("Resume");
                else startButton.setText("Pause");
                startButton.repaint();
            }
        });

        JPanel stats = new JPanel();
        stats.setLayout(new BoxLayout(stats, BoxLayout.PAGE_AXIS));
        stats.setBorder(new TitledBorder("Statistics "));
        stats.add(new JLabel("Number of Enemies: "));
        stats.add(new JLabel("Number of Plants: "));
        stats.add(new JLabel("Energy Average: "));

        slider.setBorder(new TitledBorder("Simulation Time Step"));
        int[] stopTime = {200};

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                stopTime[0] = slider.getValue();
            }
        });
        optComponents.add(slider);

        pane.setBorder(new TitledBorder("Options"));

        JPanel first = new JPanel( new BorderLayout());
        JScrollPane scroll = new JScrollPane(wd,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(700,550));
        first.add(scroll,BorderLayout.EAST);
        //TODO
        first.add(stats);

        pane.add(first);
        gui.add(optComponents);
        gui.add(pane,BorderLayout.NORTH);
        JFrame f = new JFrame("World Sim");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(gui);
        f.pack();
        f.setVisible(true);

        while(true){
            while(!wd.redrawn)Thread.sleep(1);
            if(!isStopped[0]) wMap.cycle();
            wd.redrawn = false;
            wd.repaint();
            Thread.sleep(stopTime[0]);
        }
    }
}
