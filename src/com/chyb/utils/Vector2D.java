package com.chyb.utils;

public class Vector2D {
    public final int x,y;
    public Vector2D(int x, int y){
        this.x=x;
        this.y=y;
    }
    public String toString(){
        return "("+x+","+y+")";
    }
    public boolean precedes(Vector2D other){
        return this.x<=other.x && this.y<=other.y;
    }
    public boolean follows(Vector2D other){
        return this.x>=other.x && this.y>=other.y;
    }
    public Vector2D upperRight(Vector2D other){
        return new Vector2D(Math.max(this.x,other.x),Math.max(this.y,other.y));
    }
    public Vector2D lowerLeft(Vector2D other){
        return new Vector2D(Math.min(this.x,other.x),Math.min(this.y,other.y));
    }
    public Vector2D add(Vector2D other){
        return new Vector2D(this.x+other.x,this.y+other.y);
    }
    public Vector2D subtract(Vector2D other){
        return new Vector2D(this.x-other.x,this.y-other.y);
    }
    public Vector2D mod(Vector2D modVal){
        //TODO change name
        return new Vector2D((this.x % modVal.x + modVal.x) % modVal.x, (this.y % modVal.y + modVal.y) % modVal.y);
    }
    public boolean equals(Object other){
        if(this==other)return true;
        else if(!(other instanceof Vector2D))return false;
        Vector2D that = (Vector2D) other;
        return this.x==that.x && this.y==that.y;
    }
    public Vector2D opposite(){
        return new Vector2D(-x,-y);
    }
    @Override
    public int hashCode() {
        int hash = 17;
        hash += this.x * 31;
        hash += this.y * 13;
        return hash;
    }

}