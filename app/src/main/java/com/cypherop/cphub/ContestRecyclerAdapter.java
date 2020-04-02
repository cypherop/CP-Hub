package com.cypherop.cphub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ContestRecyclerAdapter extends RecyclerView.Adapter<ContestRecyclerAdapter.ViewHolder> {

    int id;

    Context context;
    ArrayList<String> head = new ArrayList<>();
    ArrayList<String> starttime = new ArrayList<>();
    ArrayList<String> endtime = new ArrayList<>();
    ArrayList<String> duration = new ArrayList<>();
    ArrayList<String> href = new ArrayList<>();
    ArrayList<String> eDateArray = new ArrayList<>();
    ArrayList<String> sDateArray = new ArrayList<>();

    public ArrayList<String> getHref(){
        return href;
    }


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
                sDateArray.add(start_item);
                start_item = start_item.replace("T"," at\n");
                start_item = "Start: " + start_item + " UTC";
                end_item = contest.getString("end");
                eDateArray.add(end_item);
                end_item = end_item.replace("T"," at\n");
                end_item = "End: " + end_item + " UTC";
                duration_item = contest.getString("duration");
                head_item = contest.getString("event");
                href_item = contest.getString("href");
                href.add(href_item);
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
                    sDateArray.add(start_item);
                    start_item = start_item.replace("T"," at\n");
                    start_item = "Start: " + start_item + " UTC";
                    end_item = contest.getString("end");
                    eDateArray.add(end_item);
                    end_item = end_item.replace("T"," at\n");
                    end_item = "End: " + end_item + " UTC";
                    duration_item = contest.getString("duration");
                    head_item = contest.getString("event");
                    href_item = contest.getString("href");
                    head.add(head_item);
                    href.add(href_item);
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
        holder.href = href.get(position);
        holder.sDate = sDateArray.get(position);
        holder.eDate = eDateArray.get(position);
        Log.d("headText",head.get(position));

    }


    @Override
    public int getItemCount() {
        Log.d("Starttime size",String.valueOf(starttime.size()));
        return starttime.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image,reminder;
        TextView start,end,head;
        String href;
        String sDate,eDate;


        public ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            head = itemView.findViewById(R.id.head);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            reminder = itemView.findViewById(R.id.reminder);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("head","Clicked");
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(href));
                    context.startActivity(i);
                }
            });

            reminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    try {
                        Date mDate = sdf.parse(sDate);
                        long timeInMilliseconds = mDate.getTime() + TimeZone.getDefault().getOffset(mDate.getTime());
                        intent.putExtra("beginTime", timeInMilliseconds);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        Date mDate = sdf.parse(eDate);
                        long timeInMilliseconds = mDate.getTime() + TimeZone.getDefault().getOffset(mDate.getTime());
                        intent.putExtra("endTime", timeInMilliseconds);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("allDay", false);
                    intent.putExtra("title", head.getText().toString());
                    context.startActivity(intent);
                }
            });

        }
    }
}
