package com.example.filmdatabase.model;

import java.util.ArrayList;
import java.util.List;

//ce qu'on voit à l'appel de /movie/{id}

public class RestDetailResponse {
    private Boolean adult;
    private List<Genre> listGenre;
    private int id;
    private String original_title;
    private String overview;
    private float popularity;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private int runtime;
    private String tagline;
    private String title;
    private Boolean video;
    private float vote_average;
    private int vote_count;

    public List<Genre> getListGenre() {
        return listGenre;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getVideo() {
        return video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }
}
