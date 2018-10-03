package vincent.assignment1.controller.evetsListener;

import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.database.UpdateTrackingTask;
import vincent.assignment1.model.SimpleTracking;

public class EditListener {

    private int index;
    private SimpleTracking trackingObj;
    private List<String> availableTimelist = new ArrayList<>();
    private List<Date> dateList = new ArrayList<>();
    private Date selectedDate;
    private Activity activity;
    private EditText editView;

    public EditListener (int index, SimpleTracking trackingObj, Activity activity, EditText editView){
        this.index = index;
        this.trackingObj = trackingObj;
        this.activity = activity;
        this.editView = editView;
        getAvailableTime();
    }

    public List<String> getAvailableTimelist(){
        return availableTimelist;
    }

    public TimeSpinnerOnItemSelectedListener getOnItemSelectedListener(){
        return new TimeSpinnerOnItemSelectedListener();
    }

    public SaveChangeOnClickListener getOnClickListener(){
        return new SaveChangeOnClickListener();
    }

    class TimeSpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedDate = dateList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class SaveChangeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String newTitle = editView.getText().toString();
            TrackingHolder.getINSTANCE().getTrackingList().get(index).setTilte(newTitle);
            TrackingHolder.getINSTANCE().getTrackingList().get(index).setMeetTime(selectedDate);

            String key = TrackingHolder.getINSTANCE().getTrackingList().get(index).getTrackingID();
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            ContentValues values = new ContentValues();
            values.put("title", newTitle);
            values.put("meetTime",dateformat.format(selectedDate));

            if(key != null){
                UpdateTrackingTask updateTrackingTask = new UpdateTrackingTask(activity, values, key);
                updateTrackingTask.execute();
            }



            activity.finish();
        }
    }


    private void getAvailableTime(){
        Calendar fromTime = Calendar.getInstance();
        Calendar toTime = Calendar.getInstance();

        fromTime.setTime(trackingObj.getTargetStartTime());
        toTime.setTime(trackingObj.getTargetEndTime());

        SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm:ss aa");

        while (fromTime.before(toTime)){
            toTime.add(Calendar.MINUTE, -1);
            Log.i("Time+++++", dateformat.format(toTime.getTime()));
            dateList.add(toTime.getTime());
        }
        Collections.reverse(dateList);
        for(Date date : dateList){
            availableTimelist.add(dateformat.format(date.getTime()));
        }
    }

}
