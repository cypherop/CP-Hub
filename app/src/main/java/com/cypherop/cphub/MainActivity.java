package com.cypherop.cphub;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.net.wifi.WifiEnterpriseConfig.Phase2.NONE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RequestQueue queue;
    String url = "https://clist.by/api/v1/contest/?username=cypherop&api_key=23a28ffa7d4ae6f7d92e14e8420463c43a01cef5";
    NavigationView navigationView;
    public static JSONArray contests;
    public static int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new fetch_data().execute();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        queue = Volley.newRequestQueue(this);

        new fetch_data().execute();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        this.id = id;
        Log.d("id",String.valueOf(id));
        Fragment selectedFrag;
        selectedFrag = new Contests();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.showfragement, selectedFrag);
        transaction.commit();
        transaction.addToBackStack(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class fetch_data extends AsyncTask<Void, Void, Void>

    {


        @Override
        protected Void doInBackground(Void... voids) {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String URL = url + "&start__gt=" + date + "&order_by=start";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("API response: ",response.toString());
                            try {
                                JSONArray contests = response.getJSONArray("objects");
                                MainActivity.contests = contests;
                                int[] done = new int[500];
                                for (int i=0; i<500; i++) {
                                    done[i] = 0;
                                }
                                final Menu menu = navigationView.getMenu();
                                menu.add(NONE,-1,NONE,"All");
                                for (int i = 0; i < contests.length(); i++) {
                                    JSONObject contest = contests.getJSONObject(i);
                                    JSONObject resource = contest.getJSONObject("resource");
                                    int ID = resource.getInt("id");
                                    if(done[ID] == 1) {
                                        continue;
                                    }
                                    String name = resource.getString("name");
                                    menu.add(NONE,ID,NONE,name);
                                    done[ID] = 1;
                                }
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.showfragement, new Contests());
                                transaction.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }


                    });
            queue.add(jsonObjectRequest);
            return null;
        }

    }
}
