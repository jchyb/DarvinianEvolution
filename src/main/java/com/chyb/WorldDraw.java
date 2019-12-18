package com.chyb;

import com.chyb.Entity.Animal;
import com.chyb.utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WorldDraw extends JPanel {

    WorldMap wMap;
    public WorldDraw(WorldMap wMap){
        this.wMap = wMap;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.drawRect(0,0,wMap.getWidth()*5,wMap.getHeight()*5);

        g.setColor(Color.BLACK);
        ArrayList<Animal> animalList = wMap.getAnimalList();
        for(int i = 0; i<animalList.size(); i++){
            g.fillOval(animalList.get(i).getPosition().x * 5,animalList.get(i).getPosition().y * 5,5,5);
        }
        g.setColor(Color.GREEN);
        for(int i = 0; i<wMap.getWidth();i++){
            for(int j = 0; j<wMap.getHeight();j++){
                if(wMap.isOccupiedByPlant(new Vector2D(i,j))){
                    g.drawRect(i*5,j*5,5,5);
                }
            }
        }
    }
}
