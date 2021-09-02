package com.example.recyclerviewandapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//import static com.example.recyclerviewandapi.MainActivity.EXTRA_ACTORS;
//import static com.example.recyclerviewandapi.MainActivity.EXTRA_COUNTRY;
//import static com.example.recyclerviewandapi.MainActivity.EXTRA_DESC;
//import static com.example.recyclerviewandapi.MainActivity.EXTRA_DIRECTOR;
//import static com.example.recyclerviewandapi.MainActivity.EXTRA_GENRE;
//import static com.example.recyclerviewandapi.MainActivity.EXTRA_IMAGE;
//import static com.example.recyclerviewandapi.MainActivity.EXTRA_RATING;
//import static com.example.recyclerviewandapi.MainActivity.EXTRA_RELEASE;


public class DetailRow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_row);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String genre = intent.getStringExtra("genre");
        String image = intent.getStringExtra("image");
        String release = intent.getStringExtra("release");
        String actors = intent.getStringExtra("actors");
        String director = intent.getStringExtra("director");
        String country = intent.getStringExtra("country");
        String rating = intent.getStringExtra("rating");
        String imageLand = intent.getStringExtra("imageLand");

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_title_detail);
        TextView textViewInfo = findViewById(R.id.text_info_detail);
        TextView textViewDesc = findViewById(R.id.text_desc_detail);

        if (imageLand.isEmpty()) {
            Picasso.get()
                    .load(image)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.loader)
                    .error(R.mipmap.ic_app_round)
                    .into(imageView);
        } else {
            Picasso.get()
                    .load(imageLand)
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.loader)
                    .error(R.mipmap.ic_app_round)
                    .into(imageView);
        }
        textViewTitle.setText(title);
        textViewInfo.setText("Genre : " + genre +
                "\nDirector : " + director +
                "\nActors : " + actors +
                "\nCountry : " + country +
                "\nRelease : " + release +
                "\nRating : " + rating);
        textViewDesc.setText("Synopsis : " + desc);

        progressBar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
    }
}