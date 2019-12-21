package com.chyb.utils;

public enum MapDirection {
    N(0, 1,0), NE(1,1,1), E(1,0,2), SE(1,-1,3),
    S(0,-1,4), SW(-1,-1,5), W(-1,0,6), NW(-1,1,7);
    private final int orientationValue;
    public final Vector2D unitVector;
    MapDirection(int x, int y, int rotationValue) {
        this.unitVector = new Vector2D(x, y);
        this.orientationValue = rotationValue;
    }
    public MapDirection rotateByValue(int rotationValue){
        int newValue = (orientationValue + rotationValue) % 8;
        switch(newValue){
            case 0:
                return N;
            case 1:
                return NE;
            case 2:
                return E;
            case 3:
                return SE;
            case 4:
                return S;
            case 5:
                return SW;
            case 6:
                return W;
            case 7:
                return NW;
        }
        return null;
    }


}
