package edu.matc.loops.persistance;

import edu.matc.loops.enitity.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class FireDBHelper {

    private String firebase_baseURL = "https://loopme-144918.firebaseio.com/";
    Firebase firebase;


    public FireDBHelper(){
        try{
            firebase = new Firebase(firebase_baseURL);
        } catch (FirebaseException e){
            System.out.println("Error: couldn't connect to Firebase");
        }
    }

    public void writeLoopPatterns(Loops loops){
        //Convert loops to JSON string
        String JSON = convertToJSON(loops);
        System.out.println(JSON);
        //Write JSON string to database
        writeToDatabase(JSON);
    }


    public Loops getLoopPatterns(){
        //http://tutorials.jenkov.com/java-json/jackson-jsonparser.html
        //http://wiki.fasterxml.com/JacksonTreeModel

        String JSON = readFromDatabase("loops");
        //System.out.println(JSON);
        Loops loops = new Loops();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readValue(JSON, JsonNode.class);

            //Gets each loop
            for(JsonNode node : rootNode){

                Loop loop = new Loop();
                ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();

                for(JsonNode c : node.path("coordinates")){
                    Coordinate coordinate = new Coordinate();
                    coordinate.setX(c.path("x").intValue());
                    coordinate.setY(c.path("y").intValue());
                    coordinates.add(coordinate);
                }
                System.out.println(coordinates.toString());
                loop.setCoordinates(coordinates);

                loop.setLegLength(node.path("legLegth").intValue());
                loop.setNumLegs(node.path("numLegs").intValue());
                loop.setRouteDistance(node.path("routeDistance").intValue());

                loops.addLoop(loop);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loops;
    }


    private String readFromDatabase(String URL){
        FirebaseResponse response;
        try {
            response = firebase.get(URL);
            return response.getRawBody();
        } catch(FirebaseException e) {
            System.out.println("Error: Loops could not be read");
        } catch(UnsupportedEncodingException e){
            System.out.println("Error: Unsupported Encoding Exception");
        }
        return null;
    }

    private Object convertToObject(String JSON, Class c){

        //Convert loops to JSON string
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object obj = mapper.readValue(JSON, c);
            return obj;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (com.fasterxml.jackson.databind.JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertToJSON(Object o){
        //Convert loops to JSON string
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Convert object to JSON string
            String JSON = mapper.writeValueAsString(o);
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

    private void writeToDatabase(String JSON){
        try {
            firebase.put(JSON);
        } catch(FirebaseException e) {
            System.out.println("Error: JSON string could not be written");
        } catch(UnsupportedEncodingException e){
            System.out.println("Error: Unsupported Encoding Exception");
        }
    }
}