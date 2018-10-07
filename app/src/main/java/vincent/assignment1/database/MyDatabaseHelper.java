package vincent.assignment1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            + "trackingID TEXT PRIMARY KEY, "
            + "trackable_id INTEGER, "
            + "title TEXT, "
            + "meetTime TEXT, "
            + "targetStartTime TEXT, "
            + "targetEndTime TEXT, "
            + "curLocation TEXT, "
            + "meetLocation TEXT, "
            + "FOREIGN KEY (trackable_id) REFERENCES trackable (trackableID) );" ;

    public MyDatabaseHelper (Context context){
        super(context, DATABASE_NAME,  null,   1);
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
}
