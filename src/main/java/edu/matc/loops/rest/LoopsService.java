package edu.matc.loops.rest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.matc.loops.enitity.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/generate")
public class LoopsService {

    private final static int X_MIN = 50;
    private final static int X_MAX = 100;
    private final static int Y_MIN = 50;
    private final static int Y_MAX = 100;

    private final Logger logger = Logger.getLogger(this.getClass());

    //Testing URL
    //http://localhost:8080/generate/loops?xSize=100&ySize=100&routeDistance=100&legSize=5&numLoops=2

    @GET
    @Path("/loops")
    public Response getLoops(
            @DefaultValue("0") @QueryParam("xSize") int xSize,
            @DefaultValue("0") @QueryParam("ySize") int ySize,
            @DefaultValue("0") @QueryParam("routeDistance") int routeDistance,
            @DefaultValue("0") @QueryParam("legSize") int legSize,
            @DefaultValue("1") @QueryParam("numLoops") int numLoops,

            @DefaultValue("50") @QueryParam("sameFailCount") int sameFailCount,
            @DefaultValue("500000") @QueryParam("failCount") int failCount,

            @DefaultValue("false") @QueryParam("allowDoubleBack") boolean allowDoubleBack,
            @DefaultValue("false") @QueryParam("allowSameCoordinates") boolean allowSameCoordinates,
            @DefaultValue("false") @QueryParam("allowThroughStart") boolean allowThroughStart,

            @DefaultValue("false") @QueryParam("variableLegSize") boolean variableLegSize,

            @DefaultValue("") @QueryParam("returnType") String returnType) {

        //Check for defaulting value. If default, randomize.
        if(xSize == 0){
            xSize = randomMultipleOfFive(X_MIN,X_MAX);
            logger.debug("Random xSize:"+xSize);
        }
        if(ySize == 0){
            ySize = randomMultipleOfFive(Y_MIN,Y_MAX);
            logger.debug("Random ySize:"+ySize);
        }
        if(routeDistance == 0){
            routeDistance = randomMultipleOfFive(xSize,ySize);
            logger.debug("Random routeDistance:"+routeDistance);
        }

        List<Integer> commonFactors = getFactors(routeDistance);
        if(legSize == 0){
            legSize = commonFactors.get(randomInt(1, commonFactors.size()-1));
            logger.debug("Random legSize:"+legSize);
        }

        //Setup loop generator
        LoopGenerator loopGenerator = new LoopGenerator();

        loopGenerator.setxSize(xSize);
        loopGenerator.setySize(ySize);
        loopGenerator.setRouteDistance(routeDistance);
        loopGenerator.setLegSize(legSize);
        loopGenerator.setNumLoops(numLoops);
        loopGenerator.setSameFailCount(sameFailCount);
        loopGenerator.setFailCount(failCount);
        loopGenerator.setAllowDoubleBack(allowDoubleBack);
        loopGenerator.setAllowSameCoordinates(allowSameCoordinates);
        loopGenerator.setAllowThroughStart(allowThroughStart);
        loopGenerator.setVariableNumLegs(variableLegSize);

        logger.debug("xSize:"+xSize);
        logger.debug("ySize:"+ySize);
        logger.debug("routeDistance:"+routeDistance);
        logger.debug("legSize:"+legSize);
        logger.debug("numLoops:"+numLoops);
        logger.debug("sameFailCount:"+sameFailCount);
        logger.debug("failCount:"+failCount);
        logger.debug("allowDoubleBack:"+allowDoubleBack);
        logger.debug("allowSameCoordinate:"+allowSameCoordinates);
        logger.debug("allowThroughStart:"+allowThroughStart);
        logger.debug("variableLegSize:"+variableLegSize);
        logger.debug("returnType:"+returnType);


        //Generate loops
        loopGenerator.generateLoops();


        String result;

        //Return Type
        if(returnType.equals("JSON")){
            result = convertToJSON(loopGenerator);
        } else if(returnType.equals("HTML")){
            result = convertToHTML(loopGenerator);
        } else {
            result = "Error: Invalid returnType";
        }


        //Return response
        return Response
                .status(200)
                .entity(result).build();

    }

    private String convertToJSON(Object o){
        //Convert loops to JSON string
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Convert object to JSON string
            String JSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);

            return JSON;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (com.fasterxml.jackson.databind.JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String convertToHTML(LoopGenerator loopGenerator){
        //Test loop generator:
        String html = "";
        html += getHTMLInfo(loopGenerator);
        html += getHTMLCoordinatesAll(loopGenerator);
        html += getHTMLGrids(loopGenerator);
        return html;
    }





    private String getHTMLInfo(LoopGenerator loopGenerator){
        //Loop Info
        String html = "";
        html += "<div>";
        html += "<h2>LOOPS INFO</h2>";
        html += "<table>";
        html += "<tr><th>Option</th><th>Value</th></tr>";
        html += "<tr><td>X Size:</td>" + "<td>" + loopGenerator.getxSize() + "</td></tr>";
        html += "<tr><td>Y Size:</td>" + "<td>" + loopGenerator.getySize() + "</td></tr>";
        html += "<tr><td>Route Distance:</td>" + "<td>" + loopGenerator.getRouteDistance() + "</td></tr>";
        html += "<tr><td>Leg Size:</td>" + "<td>" + loopGenerator.getLegSize() + "</td></tr>";
        html += "<tr><td>Number of Loops:</td>" + "<td>" + loopGenerator.getNumLoops() + "</td></tr>";
        html += "<tr><td>Same Fail Count:</td>" + "<td>" + loopGenerator.getSameFailCount() + "</td></tr>";
        html += "<tr><td>Fail Count:</td>" + "<td>" + loopGenerator.getFailCount() + "</td></tr>";
        html += "<tr><td>Allow Double Back?</td>" + "<td>" + loopGenerator.getAllowDoubleBack() + "</td></tr>";
        html += "<tr><td>Allow Same Coordinates?</td>" + "<td>" + loopGenerator.getAllowSameCoordinates() + "</td></tr>";
        html += "<tr><td>Allow Through Start?</td>" + "<td>" + loopGenerator.getAllowThroughStart() + "</td></tr>";
        html += "<tr><td>Variable Number of Legs?</td>" + "<td>" + loopGenerator.getVariableNumLegs() + "</td></tr>";
        html += "</table>";
        html += "</div>";
        return html;
    }

    private String getHTMLCoordinatesAll(LoopGenerator loopGenerator){
        String html="";
        //Coordinates
        html += "<div>";
        html += "<h2>Coordinates</h2>";
        for(Loop l: loopGenerator.getLoops().getLoops()){
            html += getHTMLCoordinates(l, loopGenerator);
        }
        html += "</div>";
        return html;
    }

    private String getHTMLCoordinates(Loop loop, LoopGenerator loopGenerator){
        String html = "";
        html += "<table>";
        for(Coordinate c : loop.getCoordinates()){
            html += "("+c.getX()+","+c.getY()+")";
        }
        html += "</td></tr>";
        html += "</table>";
        return html;
    }

    private String getHTMLGrids(LoopGenerator loopGenerator){
        String html="";
        String[][] grid;

        //Coordinates
        html += "<div>";
        html += "<style>" +
                "td.grid{height: 5px; width: 15px; text-align: center; vertical-align: center}" +
                "table.grid{border-collapse: collapse;}" +
                "</style>";
        html += "<h2>Grids</h2>";

        int i = 1;
        for(Loop l: loopGenerator.getLoops().getLoops()){
            html += getHTMLCoordinates(l, loopGenerator);

            grid = getHTMLGridForLoop(l, loopGenerator);

            html += "<table class='grid'>";
            for(int y = 0; y < loopGenerator.getySize(); y++){
                html += "<tr>";
                for(int x = 0; x < loopGenerator.getxSize(); x++){
                    html += ("<td class='grid'>" + grid[x][y] + "</td>");
                }
                html += "</tr>";
            }
            html += "</table>";

            i++;
        }

        html += "</table>";
        return html;
    }

    private String[][] getHTMLGridForLoop(Loop loop, LoopGenerator loopGenerator){

        int xSize = loopGenerator.getxSize();
        int ySize = loopGenerator.getySize();

        //Set up initial grid
        String[][] routeGrid = new String[xSize][ySize];
        for(int y = 0; y < ySize; y++){
            for(int x = 0; x < xSize; x++){
                routeGrid[x][y] = ".";
            }
        }

        //Add in coordinate points
        List<Coordinate> coordinates = loop.getCoordinates();
        for(int i = 0; i<coordinates.size(); i++) {
            routeGrid[coordinates.get(i).getX()][coordinates.get(i).getY()] = Integer.toString(i);
        }
        return routeGrid;
    }



    /**
     * Return a random multiple of 5 between the min and max
     * @param min
     * @param max
     * @return
     */
    private int randomMultipleOfFive(int min, int max) {
        int randomNum = ((min + (int)(Math.random() * ((max - min) + 1)))/5)*5;
        return randomNum;
    }

    private int randomInt(int min, int max) {
        int randomNum = (min + (int)(Math.random() * ((max - min) + 1)));
        return randomNum;
    }

    private List<Integer> getFactors(int a){
        List<Integer> commonFactors = new ArrayList<Integer>();
        for(int i = 1; i<a; i++){
            if((double)a/i == a/i){
                commonFactors.add(i);
                logger.debug("Common Factor:"+i);
            }
        }
        return commonFactors;
    }


}

