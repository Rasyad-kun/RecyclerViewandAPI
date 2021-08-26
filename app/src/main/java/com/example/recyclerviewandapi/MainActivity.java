package com.example.recyclerviewandapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LocalAdapter mLocalAdapter;
    private ArrayList<Model> mLocalList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}