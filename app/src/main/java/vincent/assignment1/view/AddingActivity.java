package vincent.assignment1.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.adapter.RouteAdapter;
import vincent.assignment1.controller.TrackableReader;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.controller.evetsListener.AddSaveBtnOnClickListener;
import vincent.assignment1.controller.evetsListener.DateTimePickerOnClickLinstener;
import vincent.assignment1.controller.evetsListener.RouteInfoBtnOnClickListener;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.model.SimpleTracking;
import vincent.assignment1.model.Tracking;
import vincent.assignment1.service.TestTrackingService;
import vincent.assignment1.service.TrackingService;

public class AddingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private TextView trackableName;
    private TextView datepicker;
    private TextView timepicker;
    private EditText trackingTitle;
    private Button saveBtn;
    private Button routeInfoBtn;
    private RecyclerView routeContentRecyclerView;


    private DialogFragment timeFragment;
    private DialogFragment dateFragment;
    private int trackableid;
    private SimpleTrackable tempTrackable;
    private TrackableReader trackableReader;
    private List<TrackingService.TrackingInfo> tempMatch;


    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int hourOfDay;
    private int minuteOfHour;
    private String pickedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_layout);

        TestTrackingService.test(this);

        trackableid = Integer.parseInt(getIntent().getStringExtra("trackable_ID"));
        trackableName = (TextView) findViewById(R.id.adding_trackable_name);
        datepicker = (TextView) findViewById(R.id.add_date_picker);
        timepicker = (TextView) findViewById(R.id.add_time_picker);
        saveBtn = (Button) findViewById(R.id.adding_save_btn);
        routeInfoBtn = (Button) findViewById(R.id.route_info_btn);
        trackingTitle = (EditText) findViewById(R.id.adding_trackingtitle_input);
        routeContentRecyclerView = (RecyclerView) findViewById(R.id.route_content_view);

        //set recyclerview to show all routes duration of a specific range
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routeContentRecyclerView.setLayoutManager(layoutManager);

        trackableReader = TrackableReader.getINSTANCE(this);

        tempTrackable = trackableReader.getSelectedTrackableObj(trackableid);

        //set date and time picker
        dateFragment = new DatePickerFragment();
        timeFragment = new TimePickerFragment();

        datepicker.setOnClickListener(new DateTimePickerOnClickLinstener(dateFragment, this));
        timepicker.setOnClickListener(new DateTimePickerOnClickLinstener(timeFragment, this));


        saveBtn.setOnClickListener(new AddSaveBtnOnClickListener(trackableid, trackingTitle, this));

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        this.hourOfDay = hourOfDay;
        this.minuteOfHour = minute;

        /**
         * !-!-!-!-!-!-!-!-!-!-!-!-!-!
         *  SimpleDateFormat always get wrong result of year. so have to hard code this part.
         */
        String aa;
        if(hourOfDay < 12) {
            aa = " AM";

        } else if (hourOfDay == 12 ){
            aa = " PM";
        }else {
            this.hourOfDay = hourOfDay - 12;
            aa = " PM";
        }

        pickedDateTime = this.dayOfMonth + "/" + this.monthOfYear + "/" + this.year + " " + this.hourOfDay + ":" + this.minuteOfHour + ":" + "00" + aa;

        Log.i("time", "++++++++++++++++++++++" + pickedDateTime);
        timepicker.setText(this.hourOfDay + ":" + minute + ":" + "00" + aa);

        //routeInfoBtn button setOnClickListener after date and time are all set
        routeInfoBtn.setOnClickListener(new RouteInfoBtnOnClickListener(pickedDateTime, trackableid, routeContentRecyclerView));

/*************comments for future debug, this is a bug on SimpleDateFormat: year 2018 always show 3918 ****************************/

//        pickedDateAndTime = new Date(this.year, this.monthOfYear, this.dayOfMonth,
//                this.hourOfDay, this.minuteOfHour);
//
//        dateFormat = new SimpleDateFormat("hh:mm:ss aa");
//        timepicker.setText(dateFormat.format(pickedDateAndTime));
//
//        dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss ");
//        Log.i("timedate",  " +++++ " + dateFormat.format(pickedDateAndTime));

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.monthOfYear = month + 1;
        this.dayOfMonth = dayOfMonth;



        datepicker.setText(this.dayOfMonth + "/" + this.monthOfYear + "/" + this.year);


/*************comments for future debug, this is a bug on SimpleDateFormat: year 2018 always show 3918 ****************************/

//        dateFormat = new SimpleDateFormat("yyyy.MM.dd");
//        datepicker.setText(dateFormat.format(pickedDateAndTime))
//        Log.i("timedate", year + "-" + month + "-" + dayOfMonth + " +++++ " + dateFormat.format(pickedDateAndTime));
    }
}
