package com.example.filmdatabase.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.filmdatabase.controller.DetailController;
import com.example.filmdatabase.model.Movie;
import com.example.filmdatabase.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class ToSeeFragment extends Fragment {

    private static final String PREFS_TO_SEE = "PREFS_TO_SEE";
    private static final String PREFS = "PREFS";
    SharedPreferences sharedPreferences;

    private RecyclerView recyclerView;
    List<Movie> movieList;

    public ToSeeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String listJson = sharedPreferences.getString(PREFS_TO_SEE, null);
        Type listType = new TypeToken<List<Movie>>(){}.getType();
        movieList = gson.fromJson(listJson, listType);

        if(movieList == null || movieList.isEmpty()) {

            view = inflater.inflate(R.layout.fragment_empty_list, container, false);
            TextView txtTop = view.findViewById(R.id.toseetext);
            txtTop.setText("Ta liste de films à voir est vide !");
            TextView txtBot = view.findViewById(R.id.gofindmovies);
            txtBot.setText("Ajoutez les films que vous voulez voir");
        } else  {
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
        RecyclerView.Adapter mAdapter = new FilmDatabaseAdapter(input, null, movieList, getActivity(), null, new FilmDatabaseAdapter.OnToSeeClickListener() {
            @Override
            public void onToSeeAdded(Movie item) { }
            @Override
            public void onToSeeRemove(Movie item) { }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
