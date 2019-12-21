package com.chyb;

import com.chyb.entities.Animal;
import com.chyb.utils.Vector2D;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;

public class WorldDraw extends JPanel {

    int blockWidth;
    int blockHeight;
    WorldMap wMap;
    public WorldDraw(WorldMap wMap){
        this.setPreferredSize(new Dimension(wMap.getWidth()*5 + 20,wMap.getHeight()*5 + 20));
        //this.setMinimumSize(new Dimension(wMap.getWidth(),wMap.getHeight()));
        this.wMap = wMap;
        this.blockHeight=100;
        this.blockWidth=100;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawRect(10,10,wMap.getWidth()*5,wMap.getHeight()*5);

        g.setColor(Color.BLACK);
        ArrayList<Animal> animalList = wMap.getAnimalList();
        for(int i = 0; i<animalList.size(); i++){
            g.setColor(Color.getHSBColor(0, 1,0.3f));
            g.fillOval(animalList.get(i).getPosition().x * 5 + 10,animalList.get(i).getPosition().y * 5 + 10,5,5);
        }

        g.setColor(Color.GREEN);
        for(int i = -2; i<wMap.getWidth()+2;i++){
            for(int j = -2; j<wMap.getHeight()+2;j++){
                if(wMap.isOccupiedByPlant(new Vector2D(i,j))){
                    g.fillRect((i+2)*5,(j+2)*5,5,5);
                }
            }
        }
    }
}
