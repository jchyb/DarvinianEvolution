package com.chyb;
import com.chyb.ui.StatisticsTracker;
import com.chyb.ui.WorldDraw;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class Setup {

    public static void main(String[] args) throws InterruptedException {

        WorldMapConfig config = new WorldMapConfig();
        try (FileReader reader = new FileReader("parameters.json")) {
            config = new Gson().fromJson(reader, WorldMapConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        WorldMapSimulator wMap = new WorldMapSimulator(config);
        StatisticsTracker statisticsTracker = new StatisticsTracker(wMap);
        WorldDraw wd = new WorldDraw(wMap);

        JPanel gui = new JPanel(new BorderLayout(10,10));
        gui.setBorder( new TitledBorder("Simulation") );

        //arrays for easy lambda access
        final boolean[] isStopped = {false};
        int[] stopTime = {0};

        JPanel optComponents = new JPanel( new FlowLayout(FlowLayout.RIGHT, 3,3));
        optComponents.setBorder( new TitledBorder("Options") );
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            isStopped[0] = !isStopped[0];
            if(isStopped[0]) pauseButton.setText("Resume");
            else pauseButton.setText("Pause");
            pauseButton.repaint();
        });
        JSlider slider = new JSlider();
        slider.setBorder(new TitledBorder("Simulation Time Step"));
        slider.addChangeListener(e -> stopTime[0] = slider.getValue());
        optComponents.add(pauseButton);
        optComponents.add(slider);
        stopTime[0] = slider.getValue();

        JPanel statsComponents = new JPanel();
        statsComponents.setLayout(new BoxLayout(statsComponents, BoxLayout.PAGE_AXIS));
        statsComponents.setBorder(new TitledBorder("Statistics "));
        JLabel numberOfAnimalsLabel = new JLabel("Number of Animals: ");
        JLabel numberOfPlantsLabel = new JLabel("Number of Plants: ");
        JLabel energyAverageLabel = new JLabel("Energy Average: ");
        JLabel childrenAverageLabel = new JLabel("Children Average: ");
        JLabel averageAgeLabel = new JLabel("Average Age: ");
        statsComponents.add(numberOfAnimalsLabel);
        statsComponents.add(numberOfPlantsLabel);
        statsComponents.add(energyAverageLabel);
        statsComponents.add(childrenAverageLabel);
        statsComponents.add(averageAgeLabel);
        statsComponents.setPreferredSize(new Dimension(200,100));

        JPanel middlePanel = new JPanel( new BorderLayout());
        JScrollPane scrollableMap = new JScrollPane(wd,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableMap.setPreferredSize(new Dimension(700,540));

        middlePanel.add(scrollableMap,BorderLayout.EAST);
        middlePanel.add(statsComponents);

        gui.add(optComponents);
        gui.add(middlePanel,BorderLayout.NORTH);

        JFrame f = new JFrame("Evolution Simulation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(gui);
        f.pack();
        f.setVisible(true);

        while(true){
            while(!wd.redrawn)Thread.sleep(1);
            if(!isStopped[0]) wMap.update();
            if(!isStopped[0]) statisticsTracker.update();

            numberOfAnimalsLabel.setText("Number of Animals: " + statisticsTracker.getNumberOfAnimals());
            numberOfPlantsLabel.setText("Number of Plants: " + statisticsTracker.getNumberOfPlants());
            energyAverageLabel.setText("Average Energy: " + statisticsTracker.getAverageEnergy());
            childrenAverageLabel.setText("Children Average: " + statisticsTracker.getChildrenAverage());
            averageAgeLabel.setText("Average Age: " + statisticsTracker.getAverageAge());

            wd.redrawn = false;
            wd.repaint();
            Thread.sleep(stopTime[0]);
        }
    }
}
