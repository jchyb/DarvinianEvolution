package com.chyb.Entity;

import com.chyb.utils.Vector2D;

public class Plant {
    Vector2D position;
    public Plant(Vector2D position){
        this.position = new Vector2D(position.x, position.y);
    }
    public Vector2D getPosition(){
        return position;
    }

}