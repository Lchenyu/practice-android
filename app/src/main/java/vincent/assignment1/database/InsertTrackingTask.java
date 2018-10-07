package vincent.assignment1.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;

import vincent.assignment1.model.SimpleTracking;

import static vincent.assignment1.view.MainActivity.TAG_STAGE;

public class InsertTrackingTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private SimpleTracking trackingObj;

    public InsertTrackingTask (Activity activity, SimpleTracking tracking){
        this.activity = activity;
        this.trackingObj = tracking;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Log.d(TAG_STAGE + getClass().getName(), "write tracking");

        MyDatabaseManager dbManager = MyDatabaseManager.getInstance(activity);
        SQLiteDatabase db = dbManager.openDatabase();

        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        ContentValues values = new ContentValues();
        values.put("trackingID", trackingObj.getTrackingID());
        values.put("trackable_id", trackingObj.getTrackableID());
        values.put("title", trackingObj.getTilte());

        values.put("meetTime",dateformat.format(trackingObj.getMeetTime().getTime()));
        values.put("targetStartTime", dateformat.format(trackingObj.getTargetStartTime().getTime()));
        values.put("targetEndTime", dateformat.format(trackingObj.getTargetEndTime().getTime()));

        values.put("curLocation", trackingObj.getCurLocation());
        values.put("meetLocation", trackingObj.getMeetLocation());

        db.insertWithOnConflict(MyDatabaseHelper.TRACKING_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        dbManager.closeDatabase();

        return null;
    }
}
