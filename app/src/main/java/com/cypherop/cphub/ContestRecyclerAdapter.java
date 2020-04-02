package com.cypherop.cphub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContestRecyclerAdapter extends RecyclerView.Adapter<ContestRecyclerAdapter.ViewHolder> {

    int id;

    Context context;
    ArrayList<String> head = new ArrayList<>();
    ArrayList<String> starttime = new ArrayList<>();
    ArrayList<String> endtime = new ArrayList<>();
    ArrayList<String> duration = new ArrayList<>();


    public ContestRecyclerAdapter(Context context, int id) throws JSONException {
        this.context = context;
        this.id = id;
        String start_item, end_item, duration_item, head_item, href_item;
        if(id==-1){
            for(int i=0; i < MainActivity.contests.length(); ++i){
                JSONObject contest = MainActivity.contests.getJSONObject(i);
                JSONObject resource = contest.getJSONObject("resource");
                int ID = resource.getInt("id");
                Log.d("ContestRecyclerAdapter",contest.toString());

                Log.d("ContestRecyclerAdapter2",contest.toString());
                start_item = contest.getString("start");
                start_item = start_item.replace("T"," at\n");
                start_item = "Start: " + start_item + " UTC";
                end_item = contest.getString("end");
                end_item = end_item.replace("T"," at\n");
                end_item = "End: " + end_item + " UTC";
                duration_item = contest.getString("duration");
                head_item = contest.getString("event");
                href_item = contest.getString("href");
                head.add(head_item);
                starttime.add(start_item);
                endtime.add(end_item);
                duration.add(duration_item);
                Log.d("head", String.valueOf(head.size()));
            }
        }
        else {
            for (int i = 0; i < MainActivity.contests.length(); ++i) {
                JSONObject contest = MainActivity.contests.getJSONObject(i);
                JSONObject resource = contest.getJSONObject("resource");
                int ID = resource.getInt("id");
                Log.d("ContestRecyclerAdapter", contest.toString());
                if (ID == id) {
                    Log.d("ContestRecyclerAdapter2", contest.toString());
                    start_item = contest.getString("start");
                    start_item = start_item.replace("T"," at\n");
                    start_item = "Start: " + start_item + " UTC";
                    end_item = contest.getString("end");
                    end_item = end_item.replace("T"," at\n");
                    end_item = "End: " + end_item + " UTC";
                    duration_item = contest.getString("duration");
                    head_item = contest.getString("event");
                    href_item = contest.getString("href");
                    head.add(head_item);
                    starttime.add(start_item);
                    endtime.add(end_item);
                    duration.add(duration_item);
                    Log.d("head", String.valueOf(head.size()));
                }
            }
        }
    }

    @NonNull
    @Override
    public ContestRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contest_item, viewGroup, false);
        Log.d("onCreateViewHolder","working");
        return new ContestRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContestRecyclerAdapter.ViewHolder holder, int position) {

        holder.head.setText(head.get(position));
        holder.start.setText(starttime.get(position));
        holder.end.setText(endtime.get(position));
        Log.d("headText",head.get(position));

    }


    @Override
    public int getItemCount() {
        Log.d("Starttime size",String.valueOf(starttime.size()));
        return starttime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView start,end,head;


        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            head = itemView.findViewById(R.id.head);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);

        }
    }
}
