package com.example.filmdatabase.model;

import java.util.List;

public class Movie {

    private String poster_path;
    private Boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private float popularity;
    private int vote_count;
    private Boolean video;
    private float vote_average;
    private Boolean is_favorite;

    public Boolean getIs_to_see() {
        if(is_to_see == null) {
            return false;
        } else {
            return  is_to_see;
        }
    }

    public void setIs_to_see(Boolean is_to_see) { this.is_to_see = is_to_see; }

    private Boolean is_to_see;

    public String getPoster_path() {
        return poster_path;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public Boolean getIs_favorite() {
        if(is_favorite == null) {
            return false;
        } else {
            return  is_favorite;
        }
    }

    public void setIs_favorite(Boolean is_favorite) { this.is_favorite = is_favorite; }
}
