package vincent.assignment1.controller.suggestionControl;


import android.app.Activity;
import android.icu.util.Calendar;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.service.TrackingService;


public class SuggestionTrackableManager {

    private List<SimpleTrackable> mAvailableList;
    private Activity activity;

    public SuggestionTrackableManager (Activity activity, List<SimpleTrackable> availableList) {
        this.mAvailableList = availableList;
        this.activity = activity;
    }


    public List<SimpleRoute> getRouteInfo(){
        try{

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat curDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");


            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            String searchdate = "06/10/2018 1:05:00 PM";
            //Date date = dateFormat.parse(curDateFormat.format(currentTime));
            Date date = dateFormat.parse(searchdate);
            List<TrackingService.TrackingInfo> matched = TrackingService.getSingletonInstance(activity)
                    .getTrackingInfoForTimeRange(date, 600, 0);

            Log.d("finalTest", "date in suggestiontracking manager : " + date);
            Log.d("finalTest", "raw matched size in suggestiontracking manager : "+ matched.size());

            return getCurMatchList(matched);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    private List<SimpleRoute> getCurMatchList(List<TrackingService.TrackingInfo> matched){

        List<SimpleRoute> curMatchList = new ArrayList<>();
        Date currentTime = Calendar.getInstance().getTime();


        for(SimpleTrackable trackable : mAvailableList){

            List<SimpleRoute> routeList = new ArrayList<>();
            ArrayList<Integer> timeDiffList = new ArrayList<>();


            //find trackable based on stop time
            for(TrackingService.TrackingInfo routeInfo : matched){
                if(routeInfo.trackableId == trackable.getId() && routeInfo.stopTime > 0){
                    SimpleRoute routeObj = new SimpleRoute();
                    routeObj.setTrackableId(trackable.getId());
                    routeObj.setDate(routeInfo.date);
                    routeObj.setLatitude(routeInfo.latitude);
                    routeObj.setLongitude(routeInfo.longitude);
                    routeObj.setStopTime(routeInfo.stopTime);

                    routeList.add(routeObj);
//                    curMatchList.add(routeObj);
                }
            }

            //find stationary time close to current time
            if(routeList.size() != 0){
                for(SimpleRoute routeObj : routeList){
                    int timeDiff = getDiff(getMeasurableTime(currentTime), getMeasurableTime(routeObj.getDate()));

                    timeDiffList.add(timeDiff);
                }

                int index = 0;
                for(int i = 1; i < timeDiffList.size(); i++){
                    if(timeDiffList.get(index) > timeDiffList.get(i)){
                        index = i;
                    }
                }
                curMatchList.add(routeList.get(index));
            }

        }

        return curMatchList;
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
        return Math.abs(date1 - date2);
    }



}
