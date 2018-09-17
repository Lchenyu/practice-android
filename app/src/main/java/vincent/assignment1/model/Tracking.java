package vincent.assignment1.model;

import java.util.Date;

public interface Tracking {

     String getTrackingID();
     void setTrackingID(String trackingID);

     int getTrackableID();
     void setTrackableID(int trackableID);

     String getTilte();
     void setTilte(String tilte);

     Date getTargetStartTime();
     void setTargetStartTime(Date time);

     Date getTargetEndTime();
     void setTargetEndTime(Date time);

     Date getMeetTime();
     void setMeetTime(Date time);

     String getCurLocation();
     void setCurLocation(String location);

     String getMeetLocation();
     void setMeetLocation(String location);
}
