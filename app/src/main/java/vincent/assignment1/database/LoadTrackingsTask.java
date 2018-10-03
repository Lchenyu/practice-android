package vincent.assignment1.database;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.model.SimpleTracking;

public class LoadTrackingsTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;

    public LoadTrackingsTask(Activity activity){
        this.activity = activity;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        MyDatabaseManager dbManager = MyDatabaseManager.getInstance(activity);
        SQLiteDatabase db = dbManager.openDatabase();

        Cursor cursor = db.query(
                "tracking",
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

            do{
                SimpleTracking trackingObj = new SimpleTracking();
                String trackingid = cursor.getString(cursor.getColumnIndex("trackingID"));
                int trackable_id = cursor.getInt(cursor.getColumnIndex("trackable_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String curLocation = cursor.getString(cursor.getColumnIndex("curLocation"));
                String meetLocation = cursor.getString(cursor.getColumnIndex("meetLocation"));
                Date meetTime, targetStartTime, targetEndTime;

                try {
                    meetTime = dateFormat.parse(
                            cursor.getString(cursor.getColumnIndex("meetTime")));

                    targetStartTime = dateFormat.parse(
                            cursor.getString(cursor.getColumnIndex("targetStartTime")));

                    targetEndTime = dateFormat.parse(
                            cursor.getString(cursor.getColumnIndex("targetEndTime")));

                    trackingObj.setTilte(title);
                    trackingObj.setTrackableID(trackable_id);
                    trackingObj.setMeetTime(meetTime);
                    trackingObj.setTargetStartTime(targetStartTime);
                    trackingObj.setTargetEndTime(targetEndTime);

                    trackingObj.setMeetLocation(meetLocation);
                    trackingObj.setTrackingID(trackingid);
                    trackingObj.setCurLocation(curLocation);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TrackingHolder.getINSTANCE().addTracking(trackingObj);

            }while (cursor.moveToNext());
        }

        dbManager.closeDatabase();

        return null;
    }
}
