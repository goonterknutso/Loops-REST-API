package edu.matc.loops.enitity;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * LoopGenerator class is used to randomly generate loops.
 *
 * Created by gunther on 9/16/16.
 */
public class LoopGenerator {

    private final Logger logger = Logger.getLogger(this.getClass());

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    private int xSize;
    private int xMid;
    private int xMin;

    private int ySize;
    private int yMid;
    private int yMin;

    private int routeDistance;
    private int legSize;
    private int numLoops;

    private int sameFailCount;
    private int failCount;

    private boolean allowDoubleBack;
    private boolean allowSameCoordinates;
    private boolean allowThroughStart;

    private boolean variableNumLegs;
    private boolean variableLegSize;

    private Loops loops;


    public LoopGenerator(){}

    public void generateLoops(){

        loops = new Loops();
        int sameCounter = 0;
        int failCounter = 0;
        boolean generateLoops = true;
        boolean generateLegs = true;
        boolean added = false;
        int randomDirection;
        int oppositeDirection = Integer.MAX_VALUE;
        Loop loop;
        List<Integer> commonFactors = getFactors(getRouteDistance());


        /* Run until the number of loops we want is generated */
        while(generateLoops){

            //Create new loop
            loop = new Loop(legSize, routeDistance);

            //Push first coordinate
            int xCurrent = xMid;
            int yCurrent = yMid;
            loop.addCoordinate(new Coordinate(xCurrent,yCurrent));

            generateLegs = true;


            /* Keep looking for route until we reach max distance */
            while(generateLegs){

                //Create random direction (0-3)
                randomDirection = (int) (Math.random()*4);

                //Checks the random direction, adds point if passes certain conditionals (see check direction)
                if(checkDirection(loop, randomDirection, xCurrent, yCurrent, oppositeDirection)) {
                    switch(randomDirection){
                        case UP: yCurrent += legSize; if(!allowDoubleBack){oppositeDirection=1;} break;
                        case DOWN: yCurrent -= legSize; if(!allowDoubleBack){oppositeDirection=0;} break;
                        case LEFT: xCurrent -= legSize; if(!allowDoubleBack){oppositeDirection=3;} break;
                        case RIGHT: xCurrent += legSize; if(!allowDoubleBack){oppositeDirection=2;} break;
                    }

                    //Add coordinate to loop
                    loop.addCoordinate(new Coordinate(xCurrent,yCurrent));
                }else{
                    break;
                }


                //Loop complete with correct route distance
                if(atStart(xCurrent,yCurrent) && (loop.getNumLegs()*loop.getLegLength() == routeDistance)){

                    //Set legSize to 0 if varirableLegSize
                    if(variableLegSize){ loop.setLegLength(0); }

                    //Try to add loop and set added
                    added = loops.addLoop(loop);

                    if(added){
                        sameCounter = 0;
                        failCounter = 0;
                    }else{
                        sameCounter++;
                    }
                    generateLegs = false;
                }

                //Loop beyond our route distance, start over
                if(loop.getNumLegs()*loop.getLegLength() > routeDistance){
                    generateLegs = false;
                }

                //Check for variable legsize, change legsize if necessary
                if(getVariableLegSize()){
                    legSize = randomInt(1,commonFactors.get(commonFactors.size()-1));
                }

            }

            //Check to see if we have generated the number of loops we wanted
            if(loops.getLoops().size() == numLoops){
                generateLoops = false;
            }

            //Check fail counters
            if(sameCounter == sameFailCount|| failCounter == failCount){
                generateLoops = false;
                logger.debug("Exited with fail count");
            }

            failCounter++;

        }
    }


    //Private Method for Loop Generating

    /**
     * This method is used within the LoopGenerator class to check directions generated randomly.
     *
     * @param randomDirection
     * @param xCurrent
     * @param yCurrent
     * @return
     */
    private Boolean checkDirection(Loop loop, int randomDirection, int xCurrent, int yCurrent, int oppositeDirection){

        if(!allowDoubleBack) {
            if (randomDirection == oppositeDirection) {
                return false;
            }
        }

        switch(randomDirection){
            case UP: if((yCurrent + legSize) >= ySize){ return false; }
                    else if(isACoordinate(loop, xCurrent, yCurrent + legSize)) { return false; }
                    break;
            case DOWN: if((yCurrent - legSize) <= yMin){ return false;}
                    else if(isACoordinate(loop, xCurrent, yCurrent - legSize)) { return false; }
                    break;
            case LEFT: if((xCurrent - legSize) <= xMin){ return false; }
                    else if(isACoordinate(loop, xCurrent - legSize, yCurrent)) { return false; }
                    break;
            case RIGHT: if((xCurrent + legSize) >= xSize){ return false; }
                    else if(isACoordinate(loop, xCurrent + legSize, yCurrent)) { return false; }
                    break;
        }
        return true;
    }

    /**
     * This method is used within the LoopGenerator class as a way of testing if an x,y coordinate
     * pair is already part of the loop
     *
     * @param loop The loop we want to test coordinates from
     * @param x The x value of the coordinate pair to test for
     * @param y The y value of the coordinate pair to test for
     * @return
     */
    private Boolean isACoordinate(Loop loop, int x, int y){
        if(!allowSameCoordinates) {
            for (int c = 0; c < loop.getNumLegs(); c++) {
                if ((!atStart(x, y) && loop.getCoordinate(c).getX() == x && loop.getCoordinate(c).getY() == y)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks a pair of x,y coordinate values to see if they are
     * at the start of the loop
     *
     * @param xCurrent the x value to check
     * @param yCurrent the y value to check
     * @return
     */
    private Boolean atStart(int xCurrent, int yCurrent){
        if(!allowThroughStart) {
            if (xCurrent == xMid && yCurrent == yMid) {
                return true;
            }
        }
        return false;
    }




    public int randomInt(int min, int max) {
        int randomNum = (min + (int)(Math.random() * ((max - min) + 1)));
        return randomNum;
    }

    public List<Integer> getFactors(int a){
        List<Integer> commonFactors = new ArrayList<Integer>();
        for(int i = 1; i<a; i++){
            if((double)a/i == a/i){
                commonFactors.add(i);
            }
        }
        return commonFactors;
    }



    //Getters and Setters

    public int getxSize() { return xSize; }
    public void setxSize(int xSize) {
        this.xSize = xSize;
        this.xMid = xSize/2;
        this.xMin = 0;
    }

    public int getySize() { return ySize; }
    public void setySize(int ySize) {
        this.ySize = ySize;
        this.yMid = ySize/2;
        this.yMin = 0;
    }

    public int getRouteDistance() { return routeDistance; }
    public void setRouteDistance(int routeDistance) { this.routeDistance = routeDistance; }

    public int getLegSize() { return legSize; }
    public void setLegSize(int legSize) { this.legSize = legSize; }

    public int getNumLoops() { return numLoops; }
    public void setNumLoops(int numLoops) { this.numLoops = numLoops; }

    public int getSameFailCount() { return sameFailCount; }
    public void setSameFailCount(int addSameFailCount) { this.sameFailCount = addSameFailCount; }

    public int getFailCount() { return failCount; }
    public void setFailCount(int noAddFailCount) { this.failCount = noAddFailCount; }

    public boolean getAllowDoubleBack() { return allowDoubleBack; }
    public void setAllowDoubleBack(boolean allowDoubleBack) { this.allowDoubleBack = allowDoubleBack; }

    public boolean getAllowSameCoordinates() { return allowSameCoordinates; }
    public void setAllowSameCoordinates(boolean allowSameCoordinates) { this.allowSameCoordinates = allowSameCoordinates; }

    public boolean getAllowThroughStart() { return allowThroughStart; }
    public void setAllowThroughStart(boolean allowThroughStart) { this.allowThroughStart = allowThroughStart; }

    public boolean getVariableNumLegs() { return variableNumLegs; }
    public void setVariableNumLegs(boolean variableNumLegs) { this.variableNumLegs = variableNumLegs; }

    public boolean getVariableLegSize() { return variableLegSize; }
    public void setVariableLegSize(boolean variableLegSize) { this.variableLegSize = variableLegSize; }

    public Loops getLoops() { return loops; }
    public void setLoops(Loops loops) { this.loops = loops; }





    //Output Methods for Terminal


    public String generatedLoopsToHTML(){

        String HTML = "<body>";
        HTML += "<h1>Generated Loops</h1>";
        HTML += "<div id='info'>";

        HTML += "<table>";

        for(int i = 0; i < loops.getLoops().size(); i++){

            HTML += "<tr><td colspan=2><h2>Loop #"+i+"</h2></td></tr>";
            HTML += "";
            HTML += "<tr><td><b>X Size:</b></td><td>"+xSize+"</td></tr>";
            HTML += "<tr><td><b>Y Size:</b></td><td>"+ySize+"</td></tr>";
            HTML += "<tr><td><b>Route Distance:</b></td><td>"+routeDistance+"</td></tr>";
            HTML += "<tr><td><b>Leg Size:</b></td><td>"+legSize+"</td></tr>";
            HTML += "<tr><td><b>Number of Loops:</b></td><td>"+numLoops+"</td></tr>";
            HTML += "<tr><td><b>Same Fail Count:</b></td><td>"+sameFailCount+"</td></tr>";
            HTML += "<tr><td><b>Fail Count:</b></td><td>"+failCount+"</td></tr>";



            for(Coordinate c : l.getCoordinates()){


                System.out.print("(");

                if(c.getX() < 10){
                    System.out.print(" "+c.getX());
                }else{
                    System.out.print(c.getX());
                }
                System.out.print(",");
                if(c.getY() < 10){
                    System.out.print(c.getY()+" ");
                }else{
                    System.out.print(c.getY());
                }
                System.out.print(") ");
            }
            System.out.println();
        }
        System.out.println();


        System.out.println("::Grid w/ Coordinates::");
        System.out.println();
        //For each loop
        for(int l = 0; l < loops.getLoops().size(); l++){
            writeLoopToTerminal(loops.getLoop(l));
        }
    }

    /*
    private void writeLoopToTerminal(Loop loop){
        setUpRouteGridForTerminal();

        //Print Coordinates
        for(int c = 0; c < loop.getNumLegs(); c++){
            System.out.print(loop.getCoordinate(c).toString());
            if(c < 10) {
                routeGrid[loop.getCoordinate(c).getX()][loop.getCoordinate(c).getY()] = Integer.toString(c) + "  ";
            }else{
                routeGrid[loop.getCoordinate(c).getX()][loop.getCoordinate(c).getY()] = Integer.toString(c) + " ";
            }
        }

        System.out.println();
        System.out.println();

        //Print grid
        for(int y = yMax -1; y > yMin; y--){
            for(int x = xMin; x < xMax; x++){
                System.out.print(routeGrid[x][y]);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }

    private void setUpRouteGridForTerminal(){
        for(int y = yMin; y < yMax; y++){
            for(int x = xMin; x < xMax; x++){
                routeGrid[x][y] = ".  ";
            }
        }
    }
    */

}
