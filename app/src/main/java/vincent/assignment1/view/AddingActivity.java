package vincent.assignment1.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.TrackableReader;
import vincent.assignment1.controller.evetsListener.AddSaveBtnOnClickListener;
import vincent.assignment1.controller.evetsListener.RouteInfoBtnOnClickListener;
import vincent.assignment1.model.SimpleTrackable;
import vincent.assignment1.service.TrackingService;

public class AddingActivity extends AppCompatActivity {

    private TextView trackableName;
    private EditText trackingTitle;
    private Button saveBtn;
    private Button routeInfoBtn;
    private RecyclerView routeContentRecyclerView;

    private int trackableid;
    private SimpleTrackable tempTrackable;
    private TrackableReader trackableReader;
    private List<TrackingService.TrackingInfo> tempMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_layout);

        trackableid = Integer.parseInt(getIntent().getStringExtra("trackable_ID"));
        trackableName = (TextView) findViewById(R.id.adding_trackable_name);
        saveBtn = (Button) findViewById(R.id.adding_save_btn);
        routeInfoBtn = (Button) findViewById(R.id.route_info_btn);
        trackingTitle = (EditText) findViewById(R.id.adding_trackingtitle_input);
        routeContentRecyclerView = (RecyclerView) findViewById(R.id.route_content_view);

        trackableName.setText(TrackableReader.getINSTANCE(this).getSelectedTrackableObj(trackableid).getName());

        //set recyclerview to show all routes duration of a specific range
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routeContentRecyclerView.setLayoutManager(layoutManager);

        trackableReader = TrackableReader.getINSTANCE(this);

        tempTrackable = trackableReader.getSelectedTrackableObj(trackableid);

        routeInfoBtn.setOnClickListener(new RouteInfoBtnOnClickListener(trackableid, routeContentRecyclerView));

        saveBtn.setOnClickListener(new AddSaveBtnOnClickListener(trackableid, trackingTitle, this));

    }
}
