package vincent.assignment1.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.model.SimpleTracking;

import static vincent.assignment1.view.MainActivity.TAG_STAGE;

public class LoadTrackingsTask extends AsyncTask<Void, Void, Void> {

    private Context activity;

    public LoadTrackingsTask(Context activity){
        this.activity = activity;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        Log.d(TAG_STAGE + getClass().getName(), "Loading tracking from database");

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

        if(cursor.getCount() > 0 && cursor.moveToFirst()){
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
