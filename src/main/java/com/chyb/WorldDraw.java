package main.java.com.chyb;

import main.java.com.chyb.Entity.Animal;
import main.java.com.chyb.utils.Vector2D;

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
        g.drawRect(0,0,wMap.getWidth()*10,wMap.getHeight()*10);

        g.setColor(Color.BLACK);
        ArrayList<Animal> animalList = wMap.getAnimalList();
        for(int i = 0; i<animalList.size(); i++){
            g.fillRect(animalList.get(i).getPosition().x * 10,animalList.get(i).getPosition().y * 10,10,10);
        }

        g.setColor(Color.GREEN);
        for(int i = 0; i<wMap.getWidth();i++){
            for(int j = 0; j<wMap.getHeight();j++){
                if(wMap.isOccupiedByPlant(new Vector2D(i,j))){
                    g.drawRect(i*10,j*10,10,10);
                }
            }
        }
    }
}
