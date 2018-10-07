package vincent.assignment1.database;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class DeleteTrackingTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private String key;

    public DeleteTrackingTask (Activity activity, String key) {
        this.activity = activity;
        this.key = key;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        Log.d("finaltest", "delete tracking in DeleteTrackingTask : key: " + key);

        MyDatabaseManager dbManager = MyDatabaseManager.getInstance(activity);
        SQLiteDatabase db = dbManager.openDatabase();

        db.delete(MyDatabaseHelper.TRACKING_TABLE, "trackingID = ?", new String[]{key});


        dbManager.closeDatabase();
        return null;
    }
}
