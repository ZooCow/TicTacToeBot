package com.zoocow;

/**
 * Created by Chris on 1/26/16.
 */
public class Move {

    int xCoordinate;
    int yCoordinate;

    public Move(){}

    public Move(int xCoordinate, int yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getX(){ return xCoordinate;}
    public int getY(){ return yCoordinate;}
    public void setX( int xCoordinate){
        this.xCoordinate = xCoordinate;
    }

    public void setY( int yCoordinate){
        this.yCoordinate = yCoordinate;
    }
}
