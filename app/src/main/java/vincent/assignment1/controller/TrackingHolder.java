package vincent.assignment1.controller;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTracking;

/**
 * @author Yu Liu
 *
 * a signalton design to hold tracking instances
 */

public class TrackingHolder {
    private static TrackingHolder INSTANCE = null;
    private List<SimpleTracking> trackingList = new ArrayList<>();
    private SimpleRoute selectedRoute;
    private RecyclerView.ViewHolder holder;


    public static TrackingHolder getINSTANCE() {

        if(INSTANCE == null){
            INSTANCE = new TrackingHolder();
        }
        return INSTANCE;
    }


    public void setHolder(RecyclerView.ViewHolder holder) {
        this.holder = holder;
    }

    public RecyclerView.ViewHolder getHolder(){
        return this.holder;
    }

    //return list sorted by meet_time
    public List<SimpleTracking> getTrackingList(){
        Collections.sort(trackingList, new DateComparator());
        return trackingList;
    }

    public void addTracking(SimpleTracking trackingObj){
        this.trackingList.add(trackingObj);
        Log.d("finaltest", "trackingObj's time in TrackingHolder : "+ trackingObj.getMeetTime());
    }

    public void deleteTracking(int position){

        this.trackingList.remove(position);
    }

    public void setSelectedRoute(SimpleRoute route){
        this.selectedRoute = route;
    }

    public SimpleRoute getSelectedRoute() {
        return selectedRoute;
    }

    //check if there is a selected route before press save button in adding activity
    public boolean isRouteSelected(){
        if (selectedRoute != null)
            return true;
        return false;
    }

    //cleanRoute method would be called after press save button in adding activity
    public void cleanRoute(){
        this.selectedRoute = null;
    }


}
