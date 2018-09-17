package vincent.assignment1.controller.evetsListener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vincent.assignment1.adapter.RouteAdapter;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.service.TrackingService;

/**
 * @author Yu Liu
 *
 * *********************fixing bug required!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * this class does not work well as there is a bug on line 48 of
 * .getTrackingInfoForTimeRange(dateFormat.parse(pickedDateAndTime), 20, 0);
 *
 */


public class RouteInfoBtnOnClickListener implements View.OnClickListener {


    private String pickedDateAndTime;

    private List<TrackingService.TrackingInfo> tempMatch;
    private int trackableid;
    private RecyclerView routeContentRecyclerView;

    public RouteInfoBtnOnClickListener(String pickedDateAndTime, int trackableID, RecyclerView routeRecyclerView){

        this.pickedDateAndTime = pickedDateAndTime;
        this.trackableid = trackableID;
        this.routeContentRecyclerView = routeRecyclerView;
    }

    @Override
    public void onClick(View v) {

        try{
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            Date date = dateFormat.parse(pickedDateAndTime);

            List<TrackingService.TrackingInfo> matched = TrackingService.getSingletonInstance(v.getContext())
                    .getTrackingInfoForTimeRange(date, 20, 0);


            final RouteAdapter adapter = new RouteAdapter(getRouteList(matched));
            routeContentRecyclerView.setAdapter(adapter);
            TrackingService.getSingletonInstance(v.getContext()).log(tempMatch);

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }


    /**
     *
     * finding route information from tracking_data.txt through TrackingService based on selected trackable ID
     */
    private List<SimpleRoute> getRouteList(List<TrackingService.TrackingInfo> matched){
        List<SimpleRoute> routeList = new ArrayList<>();
        tempMatch = new ArrayList<>();

        Log.i("test++++", String.valueOf(trackableid));

        for(TrackingService.TrackingInfo routeInfo : matched){
            if(routeInfo.stopTime > 0 && routeInfo.trackableId == trackableid){
                //implement builder pattern later
                SimpleRoute routeObj = new SimpleRoute();
                routeObj.setDate(routeInfo.date);
                routeObj.setStopTime(routeInfo.stopTime);
                routeObj.setLatitude(routeInfo.latitude);
                routeObj.setLongitude(routeInfo.longitude);
                routeList.add(routeObj);
                tempMatch.add(routeInfo);

                Log.i("test++++", String.valueOf(routeObj.getLatitude() + " " + String.valueOf(routeObj.getLongitude())));
            }
        }

        return routeList;
    }
}
