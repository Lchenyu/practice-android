package vincent.assignment1.view;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;


import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.adapter.TrackingAdapter;
import vincent.assignment1.controller.SwipeManager;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.controller.evetsListener.SwiperControlOnSwipeListener;
import vincent.assignment1.database.MyDatabaseHelper;
import vincent.assignment1.model.SimpleTracking;

public class TrackingActivity extends AppCompatActivity {

    private List<SimpleTracking> trackingList;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_layout);


        //dbHelper = new MyDatabaseHelper(this, "assignment.db");

        trackingList = TrackingHolder.getINSTANCE().getTrackingList();

        RecyclerView trackingView = (RecyclerView) findViewById(R.id.tracking_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        trackingView.setLayoutManager(layoutManager);
        final TrackingAdapter adapter = new TrackingAdapter(trackingList);
        trackingView.setAdapter(adapter);

        final SwipeManager swiper = new SwipeManager(new SwiperControlOnSwipeListener(this, adapter));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swiper);
        itemTouchHelper.attachToRecyclerView(trackingView);

        trackingView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swiper.onDraw(c);
            }
        });

        Toast.makeText(this, "Swipe to edit or delete", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
