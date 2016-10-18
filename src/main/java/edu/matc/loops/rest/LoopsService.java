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

            @DefaultValue("false") @QueryParam("variableLegSize") boolean variableLegSize) {

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
        logger.debug("xSize:"+xSize);
        loopGenerator.setySize(ySize);
        logger.debug("ySize:"+ySize);
        loopGenerator.setRouteDistance(routeDistance);
        logger.debug("routeDistance:"+routeDistance);
        loopGenerator.setLegSize(legSize);
        logger.debug("legSize:"+legSize);
        loopGenerator.setNumLoops(numLoops);
        logger.debug("numLoops:"+numLoops);
        loopGenerator.setSameFailCount(sameFailCount);
        logger.debug("sameFailCount:"+sameFailCount);
        loopGenerator.setFailCount(failCount);
        logger.debug("failCount:"+failCount);
        loopGenerator.setAllowDoubleBack(allowDoubleBack);
        logger.debug("allowDoubleBack:"+allowDoubleBack);
        loopGenerator.setAllowSameCoordinates(allowSameCoordinates);
        logger.debug("allowSameCoordinate:"+allowSameCoordinates);
        loopGenerator.setAllowThroughStart(allowThroughStart);
        logger.debug("allowThroughStart:"+allowThroughStart);
        logger.debug("variableLegSize:"+variableLegSize);

        //Generate loops
        loopGenerator.generateLoops();
        String JSON;

        //Convert to JSON
        JSON = convertToJSON(loopGenerator);

        //Return response
        return Response
                .status(200)
                .entity(JSON).build();

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

    /**
     * Return a random multiple of 5 between the min and max
     * @param min
     * @param max
     * @return
     */
    public int randomMultipleOfFive(int min, int max) {
        int randomNum = ((min + (int)(Math.random() * ((max - min) + 1)))/5)*5;
        return randomNum;
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
                logger.debug("Common Factor:"+i);
            }
        }
        return commonFactors;
    }


}