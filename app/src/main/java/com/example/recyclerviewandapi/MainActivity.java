package com.example.recyclerviewandapi;

import androidx.appcompat.app.AppCompatActivity;
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
    //Detail Activity / Callback / OnItemClickListener
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_DESC = "desc";
    public static final String EXTRA_GENRE = "genre";
    public static final String EXTRA_IMAGE = "image";

    private RecyclerView mRecyclerView;
    private LocalAdapter mLocalAdapter;
    private ArrayList<Model> mLocalList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Movie List");

        progressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLocalList = new ArrayList<>();

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
                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);

                                String title = result.getString("title");
                                String image = result.getString("thumbnailPotrait");
                                String desc = result.getJSONObject("detail").getString("description");
                                String genre = result.getJSONObject("detail").getString("genre");

                                mLocalList.add(new Model(title, desc, genre, image));
                            }

                            mLocalAdapter = new LocalAdapter(MainActivity.this, mLocalList);
                            mRecyclerView.setAdapter(mLocalAdapter);
                            mLocalAdapter.setOnItemClickListener(MainActivity.this); //Detail Activity / Callback / OnItemClickListener

                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            Log.d("error", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", anError.toString());
                    }
                });

    }

    //Detail Activity / Callback / OnItemClickListener
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailRow.class);
        Model clickedRow = mLocalList.get(position);

        detailIntent.putExtra(EXTRA_TITLE, clickedRow.getmTitle());
        detailIntent.putExtra(EXTRA_DESC, clickedRow.getmDesc());
        detailIntent.putExtra(EXTRA_GENRE, clickedRow.getmGenre());
        detailIntent.putExtra(EXTRA_IMAGE, clickedRow.getmImage());

        startActivity(detailIntent);
    }
}