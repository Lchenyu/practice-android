package vincent.assignment1.model;

import java.util.Date;

/**
 * @author Yu Liu
 *
 *
 * a class to hold information of routes from tracking_data.txt
 */

public class SimpleRoute implements Routeable {

    private Date date;
    private int trackableId;
    private int stopTime;
    private double latitude;
    private double longitude;

    public SimpleRoute(){

    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setTrackableId(int trackableId) {
        this.trackableId = trackableId;
    }

    @Override
    public int getTrackableId() {
        return trackableId;
    }

    @Override
    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    @Override
    public int getStopTime() {
        return stopTime;
    }

    @Override
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }
}
