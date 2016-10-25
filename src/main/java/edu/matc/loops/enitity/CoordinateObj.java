package edu.matc.loops.enitity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Tim on 10/25/2016.
 */
@Entity
@Table(name = "coordinate")
public class CoordinateObj {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "coordinate_id")
    private int coordinate_id;
    @Column(name = "x_coordinate")
    private int xCoord;
    @Column(name = "y_coordinate")
    private int yCoord;
    @Column(name = "loop_id")
    private int loopId;

    public CoordinateObj() {
    }

    public CoordinateObj(int coordinate_id, int xCoord, int yCoord, int loopId) {
        this.coordinate_id = coordinate_id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.loopId = loopId;
    }

    public int getCoordinate_id() {
        return coordinate_id;
    }

    public void setCoordinate_id(int coordinate_id) {
        this.coordinate_id = coordinate_id;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getLoopId() {
        return loopId;
    }

    public void setLoopId(int loopId) {
        this.loopId = loopId;
    }

    @Override
    public String toString() {
        return "CoordinateObj{" +
                "coordinate_id=" + coordinate_id +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", loopId=" + loopId +
                '}';
    }
}
