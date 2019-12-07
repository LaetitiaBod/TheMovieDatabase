package com.example.filmdatabase.model;

import java.util.List;

//ce qu'on voit à l'appel de /discover/movie

public class RestListResponse {

    private int page;
    private List<Movie> results;
    private int total_results;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}
