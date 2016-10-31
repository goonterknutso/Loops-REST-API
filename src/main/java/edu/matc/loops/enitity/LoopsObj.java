package edu.matc.loops.enitity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Tim on 10/25/2016.
 */
@Entity
@Table(name = "loops")
public class LoopsObj {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "LoopId")
    private int loopId;
    @Column(name = "num_legs")
    private int numLegs;
    @Column(name = "leg_length")
    private int leglength;
    @Column(name = "route_distance")
    private int routeDistance;

    public LoopsObj() {
    }

    public LoopsObj(int loopId, int numLegs, int leglength, int routeDistance) {
        this.loopId = loopId;
        this.numLegs = numLegs;
        this.leglength = leglength;
        this.routeDistance = routeDistance;
    }

    public int getLoopId() {
        return loopId;
    }

    public void setLoopId(int loopId) {
        this.loopId = loopId;
    }

    public int getNumLegs() {
        return numLegs;
    }

    public void setNumLegs(int numLegs) {
        this.numLegs = numLegs;
    }

    public int getLeglength() {
        return leglength;
    }

    public void setLeglength(int leglength) {
        this.leglength = leglength;
    }

    public int getRouteDistance() {
        return routeDistance;
    }

    public void setRouteDistance(int routeDistance) {
        this.routeDistance = routeDistance;
    }

    @Override
    public String toString() {
        return "LoopsObj{" +
                "loopId=" + loopId +
                ", numLegs=" + numLegs +
                ", leglength=" + leglength +
                ", routeDistance=" + routeDistance +
                '}';
    }
}
