package vincent.assignment1.controller.evetsListener;

import android.app.Activity;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.database.InsertTrackingTask;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTracking;

public class AddSaveBtnOnClickListener implements View.OnClickListener {

    private int trackableid;
    private Activity activity;
    private TextView trackingTitle;


    public AddSaveBtnOnClickListener (int trackableID, TextView trackingTitle, Activity activity){
        this.trackableid = trackableID;
        this.activity = activity;
        this.trackingTitle = trackingTitle;
    }

    @Override
    public void onClick(View v) {
        String title = trackingTitle.getText().toString();
        TrackingHolder holder = TrackingHolder.getINSTANCE();

        // save added tracking
        if(title != null && holder.isRouteSelected()){
            if(holder.getSelectedRoute().getStopTime() > 0){
                SimpleTracking trackingObj = new SimpleTracking();
                SimpleRoute routeObj = holder.getSelectedRoute();
                // create tracking object
                trackingObj.setTilte(title);
                trackingObj.setTrackableID(trackableid);
                trackingObj.setTargetStartTime(routeObj.getDate());
                trackingObj.setMeetTime(routeObj.getDate());

                //calculate end time by start time + stop time; it will use to find time windows for editing
                Date endtime = routeObj.getDate();
                Calendar cl = Calendar.getInstance();
                cl.setTime(endtime);
                cl.add(Calendar.MINUTE, routeObj.getStopTime());
                trackingObj.setTargetEndTime(cl.getTime());

                String id = UUID.randomUUID().toString();
                trackingObj.setTrackingID(id);


                //add a tracking object into database
                InsertTrackingTask insertTrackingTask = new InsertTrackingTask(activity, trackingObj);
                insertTrackingTask.execute();

                holder.addTracking(trackingObj);
                holder.cleanRoute();
                activity.finish();
            } else {
                Toast.makeText(v.getContext(), "Pick stop time > 0", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(v.getContext(), "cannot save", Toast.LENGTH_LONG).show();
        }
    }
}
