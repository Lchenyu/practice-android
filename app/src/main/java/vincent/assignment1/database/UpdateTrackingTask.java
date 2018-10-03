package vincent.assignment1.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class UpdateTrackingTask extends AsyncTask<Void, Void, Void> {


    private Activity activity;
    private ContentValues values;
    private String key;

    public UpdateTrackingTask (Activity activity, ContentValues cv, String key ) {
        this.activity = activity;
        this.values = cv;
        this.key = key;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        MyDatabaseManager dbManager = MyDatabaseManager.getInstance(activity);
        SQLiteDatabase db = dbManager.openDatabase();

        db.update(MyDatabaseHelper.TRACKING_TABLE,values, "trackingID = "+ key, null);


        dbManager.closeDatabase();

        return null;
    }
}
