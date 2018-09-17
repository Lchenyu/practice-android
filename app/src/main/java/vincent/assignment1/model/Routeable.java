package vincent.assignment1.model;

import java.util.Date;

public interface Routeable {

     void setDate(Date date);
     Date getDate();

     void setTrackableId(int trackableId);
     int getTrackableId();

     void setStopTime(int stopTime);
     int getStopTime();

     void setLatitude(double latitude);
     double getLatitude();

     void setLongitude(double longitude);
     double getLongitude();
}
