package com.example.filmdatabase.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filmdatabase.R;
import com.example.filmdatabase.controller.MainController;
import com.example.filmdatabase.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class FavoritesFragment extends Fragment {


    private static final String PREFS_FAVORITE = "PREFS_FAVORITE";
    private static final String PREFS = "PREFS";
    SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    List<Movie> movieList;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String listJson = sharedPreferences.getString(PREFS_FAVORITE, null);
        Type listType = new TypeToken<List<Movie>>(){}.getType();
        movieList = gson.fromJson(listJson, listType);

        if(movieList == null || movieList.isEmpty()) {

            view = inflater.inflate(R.layout.fragment_empty_list, container, false);
            ImageView img = view.findViewById(R.id.imageView);
            img.setImageResource(R.drawable.empty_list_fav);
            TextView txtTop = view.findViewById(R.id.toseetext);
            txtTop.setText("Ta liste de films favoris est vide !");
            TextView txtBot = view.findViewById(R.id.gofindmovies);
            txtBot.setText("Ajoutez des films que vous préférez");

        } else {

            view = inflater.inflate(R.layout.fragment_layout, container, false);
            recyclerView = view.findViewById(R.id.my_recycler_view);
            showList(movieList);
        }

        return view;
    }

    public void showList(List<Movie> input) {
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new FilmDatabaseAdapter(input, movieList, null, getActivity(), new FilmDatabaseAdapter.OnFavoriteClickListener() {
            @Override
            public void onFavoriteAdded(Movie item) { }
            @Override
            public void onFavoriteRemove(Movie item) { }
        }, null);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
