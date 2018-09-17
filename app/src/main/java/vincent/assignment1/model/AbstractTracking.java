package vincent.assignment1.model;

import java.util.Date;

public abstract class AbstractTracking implements Tracking {

    private String trackingID;
    private int trackableID;
    private String tilte;
    private Date targetStartTime;
    private Date targetEndTime;
    private Date meetTime;
    private String curLocation;
    private String meetLocation;

    public AbstractTracking(){

    }


    @Override
    public String getTrackingID() {
        return trackingID;
    }

    @Override
    public void setTrackingID(String trackingID) {
        this.trackingID = trackingID;
    }

    @Override
    public int getTrackableID() {
        return trackableID;
    }

    @Override
    public void setTrackableID(int trackableID) {
        this.trackableID = trackableID;
    }

    @Override
    public String getTilte() {
        return tilte;
    }

    @Override
    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    @Override
    public Date getTargetStartTime() {
        return targetStartTime;
    }

    @Override
    public void setTargetStartTime(Date time) {
        this.targetStartTime = time;
    }

    @Override
    public Date getTargetEndTime() {
        return targetEndTime;
    }

    @Override
    public void setTargetEndTime(Date time) {
        this.targetEndTime = time;
    }

    @Override
    public Date getMeetTime() {
        return meetTime;
    }

    @Override
    public void setMeetTime(Date time) {
        this.meetTime = time;
    }

    @Override
    public String getCurLocation() {
        return curLocation;
    }

    @Override
    public void setCurLocation(String location) {
        this.curLocation = location;
    }

    @Override
    public String getMeetLocation() {
        return meetLocation;
    }

    @Override
    public void setMeetLocation(String location) {
        this.meetLocation = location;
    }
}
