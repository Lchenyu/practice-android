package vincent.assignment1.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.model.SimpleTracking;
import vincent.assignment1.view.AddNotifyActivity;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder> {

    private List<SimpleTracking> trackingList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trackableName;
        private TextView trackingTitle;
        private TextView meetTime;
        public ViewHolder(View view) {
            super(view);

            trackableName = (TextView) view.findViewById(R.id.tracking_trackable_name);
            trackingTitle = (TextView) view.findViewById(R.id.tracking_title);
            meetTime = (TextView) view.findViewById(R.id.tracking_meet_time);
        }
    }

    public TrackingAdapter (List<SimpleTracking> trackingList){
        this.trackingList = trackingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_tracking_layout, parent, false);

        final ViewHolder holder = new ViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SimpleTracking trackingObj = trackingList.get(position);

        holder.trackingTitle.setText(trackingObj.getTilte());
        holder.trackableName.setText(String.valueOf(trackingObj.getTrackableID()));

        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
        Log.i("Tracking Time",dateformat.format(trackingObj.getMeetTime()));
        holder.meetTime.setText(dateformat.format(trackingObj.getMeetTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddNotifyActivity.class);
                intent.putExtra("tracking_id", trackingObj.getTrackingID());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return trackingList.size();
    }


}
