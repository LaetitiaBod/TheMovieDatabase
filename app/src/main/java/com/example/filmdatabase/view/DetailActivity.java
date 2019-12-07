package com.example.filmdatabase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filmdatabase.R;
import com.example.filmdatabase.controller.DetailController;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    public DetailActivity(){ }
    private DetailController detailcontroller;

    private static final String BASE_URL_BACKDROP = "https://image.tmdb.org/t/p/w1280";
    public int idFilm = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        this.idFilm = intent.getIntExtra(FilmDatabaseAdapter.CLE_DONNEES_ID_FILM, 1);

        detailcontroller = new DetailController(this);
        detailcontroller.onCreate();
    }

    public void showDetails (String overview, String backdrop_path, String release_date, int runtime, String tagline, String title, Boolean video, float vote_average) {

        if (backdrop_path != null) {
            ImageView backdrop_pathView = findViewById(R.id.backdrop_path);
            Picasso.with(this).load(BASE_URL_BACKDROP + backdrop_path).into(backdrop_pathView);
        }

        TextView textOverview = findViewById(R.id.overviewContent);
        textOverview.setText(overview);

        TextView textVote_average = findViewById(R.id.vote_average);
        DecimalFormat df = new DecimalFormat("#.##");
        if(vote_average<2) {
            textVote_average.setText("★☆☆☆☆   "+df.format(vote_average));
        }else if(vote_average<4) {
            textVote_average.setText("★★☆☆☆   "+df.format(vote_average));
        }else if(vote_average<6) {
            textVote_average.setText("★★★☆☆   "+df.format(vote_average));
        }else if(vote_average<8) {
            textVote_average.setText("★★★★☆   "+df.format(vote_average));
        }else if(vote_average<10) {
            textVote_average.setText("★★★★★   "+df.format(vote_average));
        }else {
            textVote_average.setText("☆☆☆☆☆   "+df.format(vote_average));
        }

        TextView textRuntime = findViewById(R.id.runtime);
        int hour = runtime / 60;
        int minute = runtime % 60;
        textRuntime.setText(hour+"h "+minute+"min");

        TextView textRelease = findViewById(R.id.release);
        textRelease.setText(release_date+"\n");

        TextView textTagline = findViewById(R.id.tagline);
        textTagline.setText(tagline);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title);

    }
}