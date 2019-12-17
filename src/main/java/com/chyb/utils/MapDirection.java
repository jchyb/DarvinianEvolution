package com.chyb.utils;

public enum MapDirection {
    N, NE, E, SE, S, SW, W, NW;

    public Vector2D toUnitVector() {
        switch (this) {
            case N:
                return new Vector2D(0, 1);
            case NE:
                return new Vector2D(1, 1);
            case E:
                return new Vector2D(1, 0);
            case SE:
                return new Vector2D(1, -1);
            case S:
                return new Vector2D(0, -1);
            case SW:
                return new Vector2D(-1, -1);
            case W:
                return new Vector2D(-1, 0);
            case NW:
                return new Vector2D(-1, 1);
        }
        return null;
    }

    public MapDirection rotateViaGene(int gene){
        //TODO
        /*
        int nextValue = ((int) this + gene) % 8;
    */
        return null;
    }


}
