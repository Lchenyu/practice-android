package vincent.assignment1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.model.SimpleTracking;

public class MyDatabaseHelper extends SQLiteOpenHelper{

    private Context mContext;
    public static final String CREATE_TRACKABLE = "CREATE TABLE trackable ("
            + "trackableID INTEGER PRIMARY KEY, "
            + "name TEXT, "
            + "description TEXT, "
            + "category TEXT, "
            + "webSite TEXT); ";

    public static final String CREATE_TRACKING = "CREATE TABLE tracking ("
            + "trackingID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "trackable_id INTEGER, "
            + "title TEXT, "
            + "meetTime TEXT, "
            + "targetStartTime TEXT, "
            + "targetEndTime TEXT, "
            + "curLocation TEXT, "
            + "meetLocation TEXT, "
            + "FOREIGN KEY (trackable_id) REFERENCES trackable (trackableID) );" ;

    public MyDatabaseHelper (Context context, String name){
        super(context, name,  null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRACKABLE);
        db.execSQL(CREATE_TRACKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertTrackableDB(List<SimpleTrackable> trackableList, SQLiteDatabase db){
        ContentValues values = new ContentValues();

        for(SimpleTrackable trackable : trackableList){
            values.put("trackableID", trackable.getId());
            values.put("name", trackable.getName());
            values.put("description", trackable.getDescription());
            values.put("category", trackable.getCategory());
            values.put("webSite", trackable.getWebSite());
            db.insertWithOnConflict("trackable", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            values.clear();
        }
    }

    public void InsertTrackingDB(List<SimpleTracking> trackingList, SQLiteDatabase db){
        // called only
        ContentValues values = new ContentValues();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

        for(SimpleTracking trackingObj : trackingList){
            values.put("trackingID", trackingObj.getTrackingID());
            values.put("trackable_id", trackingObj.getTrackableID());
            values.put("title", trackingObj.getTilte());

            values.put("meetTime",dateformat.format(trackingObj.getMeetTime().getTime()));
            values.put("targetStartTime", dateformat.format(trackingObj.getTargetStartTime().getTime()));
            values.put("targetEndTime", dateformat.format(trackingObj.getTargetEndTime().getTime()));

            values.put("curLocation", trackingObj.getCurLocation());
            values.put("meetLocation", trackingObj.getMeetLocation());
            db.insertWithOnConflict("tracking", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            values.clear();
        }
    }

//    public void InsertTrackingDB(SimpleTracking trackingObj, SQLiteDatabase db){
//
//        ContentValues values = new ContentValues();
//        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
//
//        values.put("trackingID", trackingObj.getTrackingID());
//        values.put("trackable_id", trackingObj.getTrackableID());
//        values.put("title", trackingObj.getTilte());
//
//        values.put("meetTime",dateformat.format(trackingObj.getMeetTime().getTime()));
//        values.put("targetStartTime", dateformat.format(trackingObj.getTargetStartTime().getTime()));
//        values.put("targetEndTime", dateformat.format(trackingObj.getTargetEndTime().getTime()));
//
//        values.put("curLocation", trackingObj.getCurLocation());
//        values.put("meetLocation", trackingObj.getMeetLocation());
//        db.insertWithOnConflict("tracking", null, values, SQLiteDatabase.CONFLICT_REPLACE);
//
//        values.clear();
//    }

    public void loadTrackingDB(SQLiteDatabase db){

        // only be called in tracking activity

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

        db.delete("tracking", null, null);
    }
}
