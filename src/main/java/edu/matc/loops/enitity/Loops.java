package edu.matc.loops.enitity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunther on 10/3/16.
 */
public class Loops {

    ArrayList<Loop> loops;
    int tenFailsInARow;

    public Loops(){
        loops = new ArrayList<Loop>();
    }

    public Loop getLoop(int position){
        return loops.get(position);
    }

    public Boolean addLoop(Loop loop){
        if(loops.size()!= 0 && alreadyAdded(loop)){
            return false;
        }else{
            System.out.println("Loop added");
            loops.add(loop);
            return true;
        }
    }

    public Boolean alreadyAdded(Loop loop){

        Boolean sameCoordinates = true;

        //Checks for different coordinates in each loop
        for(int l = 0; l < loops.size(); l++) {
            sameCoordinates = true;

            //Checks each coordinate pair
            for(int c = 0; c < loops.get(l).getCoordinates().size(); c++){

                //System.out.println("X: " + loops.get(l).getCoordinate(c).getX() + " " + loop.getCoordinate(c).getX());
                //System.out.println("Y: " + loops.get(l).getCoordinate(c).getY() + " " + loop.getCoordinate(c).getY());

                if (loops.get(l).getCoordinate(c).getX() != loop.getCoordinate(c).getX() ||
                        loops.get(l).getCoordinate(c).getY() != loop.getCoordinate(c).getY()) {

                    sameCoordinates = false;
                }
            }
            //If same loop found, return true
            if(sameCoordinates){
                return sameCoordinates;
            }

        }
        return sameCoordinates;
    }

    public void removeLoop(Loop loop){
        loops.remove(loop);
    }

    //Getters and Setters

    public List<Loop> getLoops(){
        return loops;
    }

    public void setLoops(List<Loop> loops){
        this.loops = (ArrayList<Loop>) loops;
    }

}
