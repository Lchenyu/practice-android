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

import vincent.assignment1.controller.MyTimeHelper;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.service.TrackingService;

import static vincent.assignment1.view.MainActivity.SEARCH_DATE;
import static vincent.assignment1.view.MainActivity.TAG_STAGE;


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
            //current time will not work well with tracking service, so I have to hard code date as TestTrackingService
            String searchdate = SEARCH_DATE;
            //Date date = dateFormat.parse(curDateFormat.format(currentTime));
            Date date = dateFormat.parse(searchdate);
            List<TrackingService.TrackingInfo> matched = TrackingService.getSingletonInstance(activity)
                    .getTrackingInfoForTimeRange(date, 600, 0);

            Log.d(TAG_STAGE + getClass().getName(), "date" + date);
            Log.d(TAG_STAGE + getClass().getName(), "raw matched size"+ matched.size());

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
                }
            }

            //find stationary time close to current time
            if(routeList.size() != 0){
                for(SimpleRoute routeObj : routeList){
                    int timeDiff = MyTimeHelper.getTimeDiff(MyTimeHelper.getMeasurableTime(currentTime), MyTimeHelper.getMeasurableTime(routeObj.getDate()));

                    timeDiffList.add(Math.abs(timeDiff));
                }

                int index = 0; //index for finding a correct object in another same sequence list

                //find an route which is the closest on current time
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
}
