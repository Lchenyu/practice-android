package vincent.assignment1.googleMaps;

import android.icu.util.Calendar;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vincent.assignment1.model.SimpleRoute;

public class MyTrackerMapHelper {


    private List<SimpleRoute> routeList;

    private static MyTrackerMapHelper INSTANCE = null;

    public static MyTrackerMapHelper getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new MyTrackerMapHelper();
        }

        return INSTANCE;
    }


    public MyTrackerMapHelper(){

    }

    public LatLng getCurLocation(){
        Date currentTime = Calendar.getInstance().getTime();
        LatLng curLocation;
        SimpleRoute tempRoute;
        int index = 0;
        ArrayList<Integer> timeDiffList = new ArrayList<>();

        if(routeList != null && routeList.size() > 0){
            for(SimpleRoute routeObj : routeList){
                int timeDiff = Math.abs(getDiff(getMeasurableTime(currentTime), getMeasurableTime(routeObj.getDate())));

                timeDiffList.add(timeDiff);
                Log.d("Maptest", currentTime.getTime() + "    " + routeObj.getDate().getTime() + "  " + timeDiff);
            }

            for(int i = 1; i < timeDiffList.size(); i++){
                if(timeDiffList.get(i-1) > timeDiffList.get(i)){
                    index = i;
                }
            }

            Log.d("maptest", "the closest object: " + routeList.get(index).getDate());


            curLocation = new LatLng(routeList.get(index).getLatitude(), routeList.get(index).getLongitude());

            routeList = null;
            return curLocation;
        }

        return new LatLng(0,0);


    }

    private int getMeasurableTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

        String strDate = dateFormat.format(date);

        Log.d("maptest", " time: "+ strDate);
        String[] splitedDate = strDate.split(":");
        int hh = Integer.valueOf(splitedDate[0]);
        int mm = Integer.valueOf(splitedDate[1]);
        int ss = Integer.valueOf(splitedDate[2]);

        return hh* 60 * 60 + mm * 60 + ss;
    }

    private int getDiff(int date1, int date2){
        return (date1 - date2);
    }

    public void setRouteList(List<SimpleRoute> routeList){
        this.routeList = routeList;
    }


    public List<LatLng> getRouteLocationList(){
        Date currentTime = Calendar.getInstance().getTime();

        List<LatLng> routeLocationList = new ArrayList<>();

        if(routeList != null && routeList.size() > 0){

            for(SimpleRoute routeObj : routeList){
                routeLocationList.add( new LatLng(routeObj.getLatitude(), routeObj.getLongitude()));
            }
            return routeLocationList;
        }
        routeLocationList.add(new LatLng(-37.807425,144.963814));
        return routeLocationList;
    }
}
