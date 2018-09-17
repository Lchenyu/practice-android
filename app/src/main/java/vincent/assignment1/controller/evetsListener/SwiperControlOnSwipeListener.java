package vincent.assignment1.controller.evetsListener;

import android.app.Activity;
import android.content.Intent;

import vincent.assignment1.adapter.TrackingAdapter;
import vincent.assignment1.controller.SwipeControlAction;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.view.EditActivity;

public class SwiperControlOnSwipeListener implements SwipeControlAction {

    private Activity activity;
    private TrackingAdapter adapter;

    public SwiperControlOnSwipeListener(Activity activity, TrackingAdapter adapter){
        this.activity = activity;
        this.adapter = adapter;
    }
    @Override
    public void onLeftClicked(int position) {
        edit(position);
    }

    @Override
    public void onRightClicked(int position) {
        TrackingHolder.getINSTANCE().deleteTracking(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position,adapter.getItemCount());
    }

    private void edit(int position){
        Intent intent = new Intent(activity, EditActivity.class);
        intent.putExtra("tracking_index",String.valueOf(position));
        activity.startActivity(intent);
    }
}
