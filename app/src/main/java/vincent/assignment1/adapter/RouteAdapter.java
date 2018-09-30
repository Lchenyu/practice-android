package vincent.assignment1.adapter;

import android.graphics.Color;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.TrackingHolder;
import vincent.assignment1.model.SimpleRoute;
import vincent.assignment1.model.SimpleTracking;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private List<SimpleRoute> routeList;
   // private int pos;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView routeDate;
        private TextView routeStop;
        private TextView routeStopTime;

        public ViewHolder(View view) {
            super(view);
            routeDate = (TextView) view.findViewById(R.id.route_date_time);
            routeStop = (TextView) view.findViewById(R.id.route_station);
            routeStopTime = (TextView) view.findViewById(R.id.route_duration);
        }
    }



    public RouteAdapter (List<SimpleRoute> routeList){
        this.routeList = routeList;
    }



    @NonNull
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_route_layout, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteAdapter.ViewHolder holder, int position) {
        final SimpleRoute routeObj = routeList.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");

        holder.routeDate.setText(dateFormat.format(routeObj.getDate()));
        holder.routeStop.setText(String.valueOf(routeObj.getLatitude()) + " " + String.valueOf(routeObj.getLongitude()));
        holder.routeStopTime.setText(String.valueOf(routeObj.getStopTime()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change color and set selected route

                v.setBackgroundColor(Color.parseColor("#9af9e0"));
                TrackingHolder.getINSTANCE().setSelectedRoute(routeObj);
                if(TrackingHolder.getINSTANCE().getHolder() != null){
                    TrackingHolder.getINSTANCE().getHolder().itemView.setBackgroundColor(Color.parseColor("#f2f2f2"));
                }
                TrackingHolder.getINSTANCE().setHolder(holder);

            }
        });

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}
