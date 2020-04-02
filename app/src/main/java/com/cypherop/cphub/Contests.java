package com.cypherop.cphub;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

public class Contests extends Fragment {

    private  int id;
    RecyclerView contestrecycler;

    public Contests() {
        this.id = MainActivity.id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.contests_list, container, false);
        contestrecycler = view.findViewById(R.id.contestrecycler);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        contestrecycler.setLayoutManager(linearLayoutManager);
        ContestRecyclerAdapter contestRecyclerAdapter = null;
        try {
            Log.d("AdapterSuccess:",String.valueOf(id));
            contestRecyclerAdapter = new ContestRecyclerAdapter(getContext(),id);
        } catch (JSONException e) {
            Log.d("AdapterError:",String.valueOf(id));
            e.printStackTrace();
        }
        contestrecycler.setAdapter(contestRecyclerAdapter);
        final ContestRecyclerAdapter finalContestRecyclerAdapter = contestRecyclerAdapter;
        contestrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), contestrecycler ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String url = finalContestRecyclerAdapter.getHref().get(position);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );

        return view;
    }

}
