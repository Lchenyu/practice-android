package vincent.assignment1.adapter;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import vincent.assignment1.R;
import vincent.assignment1.controller.suggestionControl.SuggestionControl;
import vincent.assignment1.view.AddingActivity;
import vincent.assignment1.model.SimpleTrackable;


public class TrackableAdapter extends RecyclerView.Adapter<TrackableAdapter.ViewHolder> {

    private List<SimpleTrackable> mSimpleTrackableList;
    private List<SimpleTrackable> mOriginalList;


    public void getFilter(String category){
        List<SimpleTrackable> temp = new ArrayList<>();


        if(category.equalsIgnoreCase("All")){
            temp = mOriginalList;
        }else {
            for(SimpleTrackable item : mOriginalList){
                if (item.getCategory().equalsIgnoreCase(category)){
                    temp.add(item);
                }
            }
        }
        mSimpleTrackableList = temp;

        SuggestionControl.getInstance().updateAvailableList(mOriginalList);

        notifyDataSetChanged();

    }

    public List<SimpleTrackable> getFilteredTrackableList() {
        return this.mSimpleTrackableList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView trackImage;
        private TextView trackID;
        private TextView trackName;
        private TextView trackDescription;
        private TextView trackCategory;

        public ViewHolder(View view){
            super(view);
            trackImage = (ImageView) view.findViewById(R.id.list_trackable_image);
            trackID = (TextView) view.findViewById(R.id.list_trackable_id);
            trackName = (TextView) view.findViewById(R.id.list_trackable_name);
            trackDescription = (TextView) view.findViewById(R.id.list_trackable_description);
            trackCategory = (TextView) view.findViewById(R.id.list_trackable_category);
        }
    }

    public TrackableAdapter (List<SimpleTrackable> simpleTrackable_list){
        this.mSimpleTrackableList = simpleTrackable_list;
        this.mOriginalList = simpleTrackable_list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_trackable_layout, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),AddingActivity.class);
                intent.putExtra("trackable_ID", holder.trackID.getText());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleTrackable trackObj = mSimpleTrackableList.get(position);

        holder.trackImage.setImageResource(trackObj.getPhoto());
        holder.trackID.setText(String.valueOf(trackObj.getId()));
        holder.trackName.setText(trackObj.getName());
        holder.trackDescription.setText(trackObj.getDescription());
        holder.trackCategory.setText(trackObj.getCategory());

    }

    @Override
    public int getItemCount() {
        return mSimpleTrackableList.size();
    }
}
