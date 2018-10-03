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
    public final static String DATABASE_NAME = "assignment.db";
    public final static String TRACKING_TABLE = "tracking";
    public final static String TRACKABLE_TABLE = "trackable";

    public static final String CREATE_TRACKABLE = "CREATE TABLE trackable ("
            + "trackableID INTEGER PRIMARY KEY, "
            + "name TEXT, "
            + "description TEXT, "
            + "category TEXT, "
            + "webSite TEXT); ";

    public static final String CREATE_TRACKING = "CREATE TABLE tracking ("
            + "trackingID INTEGER PRIMARY KEY, "
            + "trackable_id INTEGER, "
            + "title TEXT, "
            + "meetTime TEXT, "
            + "targetStartTime TEXT, "
            + "targetEndTime TEXT, "
            + "curLocation TEXT, "
            + "meetLocation TEXT, "
            + "FOREIGN KEY (trackable_id) REFERENCES trackable (trackableID) );" ;

    public MyDatabaseHelper (Context context){
        super(context, DATABASE_NAME,  null, 2);
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




}
