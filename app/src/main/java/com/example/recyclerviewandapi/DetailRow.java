package com.example.recyclerviewandapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.recyclerviewandapi.MainActivity.EXTRA_ACTORS;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_COUNTRY;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_DESC;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_DIRECTOR;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_GENRE;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_IMAGE;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_RATING;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_RELEASE;
import static com.example.recyclerviewandapi.MainActivity.EXTRA_TITLE;

public class DetailRow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_row);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String desc = intent.getStringExtra(EXTRA_DESC);
        String genre = intent.getStringExtra(EXTRA_GENRE);
        String image = intent.getStringExtra(EXTRA_IMAGE);
        String release = intent.getStringExtra(EXTRA_RELEASE);
        String actors = intent.getStringExtra(EXTRA_ACTORS);
        String director = intent.getStringExtra(EXTRA_DIRECTOR);
        String country = intent.getStringExtra(EXTRA_COUNTRY);
        String rating = intent.getStringExtra(EXTRA_RATING);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_title_detail);
        TextView textViewDesc = findViewById(R.id.text_desc_detail);

        Picasso.get()
                .load(image)
                .fit()
                .centerInside()
                .placeholder(R.drawable.loader)
                .error(R.mipmap.ic_app_round)
                .into(imageView);
        textViewTitle.setText(title);
        textViewDesc.setText("Genre : " + genre +
                "\nDirector : " + director +
                "\nActors : " + actors +
                "\nCountry : " + country +
                "\nRelease : " + release +
                "\nRating : " + rating +
                "\nSinopsis : " + desc);

        progressBar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
    }
}