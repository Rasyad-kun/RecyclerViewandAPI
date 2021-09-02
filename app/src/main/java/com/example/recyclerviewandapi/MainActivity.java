package com.example.recyclerviewandapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocalAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private LocalAdapter mLocalAdapter;
    private ArrayList<Model> mLocalList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Movie List");

        progressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler_view);

        AddData();
    }

    private void AddData() {
        String url = "https://api-filmapik.herokuapp.com/latest";
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mLocalList = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String title = result.getString("title");
                                String image = result.getString("thumbnailPotrait");
                                String rating = result.getString("rating");
                                JSONObject detail = result.getJSONObject("detail");
                                String desc = detail.getString("description");
                                String genre = detail.getString("genre");
                                String release = detail.getString("release");
                                String actors = detail.getString("actors");
                                String director = detail.getString("director");
                                String country = detail.getString("country");
                                String imageLand = detail.getString("thumbnailLandscape");

                                mLocalList.add(new Model(title, desc, genre, image, release, actors, director, country, rating, imageLand));
                            }

                        } catch (JSONException e) {
                            Log.d("error", e.toString());
                        }

                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())); //*
                        mLocalAdapter = new LocalAdapter(MainActivity.this, mLocalList); //*
                        mRecyclerView.setAdapter(mLocalAdapter); //*
                        mLocalAdapter.setOnItemClickListener(MainActivity.this); //Detail Activity / Callback / OnItemClickListener
                        mLocalAdapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", anError.toString());
                    }
                });

    }

    //Detail Activity / Callback / OnItemClickListener
//    public static final String EXTRA_TITLE = "title";
//    public static final String EXTRA_DESC = "desc";
//    public static final String EXTRA_GENRE = "genre";
//    public static final String EXTRA_IMAGE = "image";
//    public static final String EXTRA_RELEASE = "release";
//    public static final String EXTRA_ACTORS = "actors";
//    public static final String EXTRA_DIRECTOR = "director";
//    public static final String EXTRA_COUNTRY = "country";
//    public static final String EXTRA_RATING = "rating";
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailRow.class);
        Model clickedRow = mLocalList.get(position);

        detailIntent.putExtra("title", clickedRow.getmTitle());
        detailIntent.putExtra("desc", clickedRow.getmDesc());
        detailIntent.putExtra("genre", clickedRow.getmGenre());
        detailIntent.putExtra("image", clickedRow.getmImage());
        detailIntent.putExtra("release", clickedRow.getmRelease());
        detailIntent.putExtra("actors", clickedRow.getmActors());
        detailIntent.putExtra("director", clickedRow.getmDirector());
        detailIntent.putExtra("country", clickedRow.getmCountry());
        detailIntent.putExtra("rating", clickedRow.getmRating());
        detailIntent.putExtra("imageLand", clickedRow.getmImageLand());

        startActivity(detailIntent);
    }
}