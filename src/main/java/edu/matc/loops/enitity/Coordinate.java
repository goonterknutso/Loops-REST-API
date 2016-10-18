package edu.matc.loops.enitity;

/**
 * Coordinate is a simple class that stores x and y value pairs.
 * Created by gunther on 10/1/16.
 */
public class Coordinate {

    private int x;
    private int y;

    /**
     * Constructor (empty)
     */
    public Coordinate(){
    }

    /**
     * Constructor (with parameters)
     * @param x
     * @param y
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x value
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * Sets x value
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets y value
     * @return y
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets y value
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Converts object to string
     * @return
     */
    @Override
    public String toString(){
        String toString = "("+x+","+y+")";
        return toString;
    }
}
