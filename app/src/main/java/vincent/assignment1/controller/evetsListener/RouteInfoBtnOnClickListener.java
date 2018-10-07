package vincent.assignment1.controller.evetsListener;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vincent.assignment1.adapter.RouteAdapter;
import vincent.assignment1.googleMaps.MyTrackerMapHelper;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.service.TrackingService;
import vincent.assignment1.view.MapsActivity;

import static vincent.assignment1.view.MainActivity.SEARCH_DATE;

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

    private List<TrackingService.TrackingInfo> tempMatch;
    private int trackableid;
    private RecyclerView routeContentRecyclerView;

    public RouteInfoBtnOnClickListener(int trackableID, RecyclerView routeRecyclerView){
        this.trackableid = trackableID;
        this.routeContentRecyclerView = routeRecyclerView;
    }

    @Override
    public void onClick(View v) {

        try{

            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat curDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            String searchdate = SEARCH_DATE;
            //Date date = dateFormat.parse(curDateFormat.format(currentTime));
            Date date = dateFormat.parse(searchdate);

            Log.d("maptest", "current time on refresh" + date.toString());

            List<TrackingService.TrackingInfo> matched = TrackingService.getSingletonInstance(v.getContext())
                    .getTrackingInfoForTimeRange(date, 60, 0);

            final RouteAdapter adapter = new RouteAdapter(getRouteList(matched));
            routeContentRecyclerView.setAdapter(adapter);
            //TrackingService.getSingletonInstance(v.getContext()).log(tempMatch);

            Intent intent = new Intent(v.getContext(), MapsActivity.class);
            intent.putExtra("trackid", ""+trackableid);
            v.getContext().startActivity(intent);

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
            if(routeInfo.trackableId == trackableid){
                //implement builder pattern later
                SimpleRoute routeObj = new SimpleRoute();
                routeObj.setDate(routeInfo.date);
                routeObj.setStopTime(routeInfo.stopTime);
                routeObj.setLatitude(routeInfo.latitude);
                routeObj.setLongitude(routeInfo.longitude);
                routeList.add(routeObj);
                tempMatch.add(routeInfo);
            }
        }

        MyTrackerMapHelper.getINSTANCE().setRouteList(routeList);

        return routeList;
    }
}
