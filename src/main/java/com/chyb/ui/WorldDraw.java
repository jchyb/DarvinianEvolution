package com.chyb.ui;

import com.chyb.map.WorldMapSimulator;
import com.chyb.entities.Animal;
import com.chyb.utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WorldDraw extends JPanel {

    //redrawn is used for thread synchronisation
    public boolean redrawn;
    private WorldMapSimulator wMap;

    public WorldDraw(WorldMapSimulator wMap){
        this.wMap = wMap;
        redrawn = true;
        this.setPreferredSize(new Dimension(wMap.getWidth()*5 + 20,wMap.getHeight()*5 + 20));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(redrawn) return;
        g.setColor(Color.BLACK);
        g.drawRect(10,10,wMap.getWidth()*5,wMap.getHeight()*5);

        g.setColor(Color.BLACK);
        ArrayList<Animal> animalList = wMap.getAnimalList();
        for (Animal animal : animalList) {
            g.setColor(Color.getHSBColor(0, 1, 0.3f));
            g.fillOval(animal.getPosition().x * 5 + 10, animal.getPosition().y * 5 + 10, 5, 5);
        }

        g.setColor(Color.GREEN);
        for(int i = -2; i<wMap.getWidth()+2;i++){
            for(int j = -2; j<wMap.getHeight()+2;j++){
                if(wMap.isOccupiedByPlant(new Vector2D(i,j))){
                    g.fillRect((i+2)*5,(j+2)*5,5,5);
                }
            }
        }
        redrawn = true;
    }
}
