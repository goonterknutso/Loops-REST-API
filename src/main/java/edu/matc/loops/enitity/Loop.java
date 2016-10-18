package edu.matc.loops.enitity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunther on 10/3/16.
 */
public class Loop {

    private ArrayList<Coordinate> coordinates;
    private int legLength;
    private int numLegs;
    private int routeDistance;


    /**
     * Constructor (empty)
     */
    public Loop(){
    }

    /**
     * Constructor (with parameters)
     *
     * @param legLength the leg length for the loop
     */
    public Loop(int legLength, int routeDistance){
        coordinates = new ArrayList<Coordinate>();
        this.legLength = legLength;
        this.routeDistance = routeDistance;
    }

    public void addCoordinate(Coordinate coordinate){
        coordinates.add(coordinate);
    }

    public Coordinate getCoordinate(int position){
        return coordinates.get(position);
    }


    //Getters and Setters

    /**
     * Gets the coordinates from the loop
     * @return the coordinates from the loop
     */
    public List<Coordinate> getCoordinates(){
        return coordinates;
    }

    /**
     * Sets the coordinates for the loop
     * @param coordinates the coordinates for the loop
     */
    public void setCoordinates(List<Coordinate> coordinates){
        this.coordinates = (ArrayList<Coordinate>) coordinates;
    }

    /**
     * Gets the leg length for the loop
     * @return the leg length for the loop
     */
    public int getLegLength(){
        return legLength;
    }

    /**
     * Sets the leg length for the loop
     * @param legLength the leg length for the loop
     */
    public void setLegLength(int legLength){
        this.legLength = legLength;
    }

    /**
     * Gets the route distance
     * @return the route distance
     */
    public int getRouteDistance(){
        return routeDistance;
    }

    /**
     * Sets the route distance
     * @param routeDistance the route distance
     */
    public void setRouteDistance(int routeDistance){
        this.routeDistance = routeDistance;
    }

    /**
     * Gets the number of legs for the route
     * @return the number of legs for the route
     */
    public int getNumLegs(){
        return (coordinates.size()-1);
    }

    /**
     * Sets the number of legs for the route
     * @param numLegs the number of legs for the route
     */
    public void setNumLegs(int numLegs){this.numLegs = coordinates.size()-1;}

}
