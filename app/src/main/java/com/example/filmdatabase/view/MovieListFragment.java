package com.example.filmdatabase.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.filmdatabase.R;
import com.example.filmdatabase.controller.MainController;
import com.example.filmdatabase.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class MovieListFragment extends Fragment {

    public int idpage = 1;
    public String choiceSort = "";
    public static final String CLE_DONNEE_CHOIX_TRI = "choiceSort";

    private RecyclerView recyclerView;
    private MainController mainController;

    SharedPreferences sharedPreferences;
    private static final String PREFS = "PREFS";
    private static final String PREFS_FAVORITE = "PREFS_FAVORITE";
    private static final String PREFS_TO_SEE = "PREFS_TO_SEE";

    public MovieListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        sharedPreferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        mainController = new MainController(this);
        init_layout(view);

        return view;
    }

    public void showList(List<Movie> input) {
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        String listJson = sharedPreferences.getString(PREFS_FAVORITE, null);
        Type listType = new TypeToken<List<Movie>>() {}.getType();
        Gson gson = new Gson();
        List<Movie> fl = gson.fromJson(listJson, listType);

        String listJson2 = sharedPreferences.getString(PREFS_TO_SEE, null);
        Type listType2 = new TypeToken<List<Movie>>(){}.getType();
        List<Movie> tsl = gson.fromJson(listJson2, listType2);

        RecyclerView.Adapter mAdapter = new FilmDatabaseAdapter(input, fl, tsl, getActivity(), new FilmDatabaseAdapter.OnFavoriteClickListener() {
            @Override
            public void onFavoriteAdded(Movie item) {
                mainController.onFavoriteAdded(item);
            }

            @Override
            public void onFavoriteRemove(Movie item) {
                mainController.onFavoriteRemove(item);
            }
        }, new FilmDatabaseAdapter.OnToSeeClickListener() {
            @Override
            public void onToSeeAdded(Movie item) { mainController.onToSeeAdded(item); }

            @Override
            public void onToSeeRemove(Movie item) { mainController.onToSeeRemove(item); }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void init_layout(View view) {

        TextView txtPage = view.findViewById(R.id.textPage);
        txtPage.setText(String.format("Page %d", this.idpage));

        Button next = view.findViewById(R.id.next);
        Button previous = view.findViewById(R.id.previous);

        final Spinner spinnerSort = view.findViewById(R.id.spinner);
        String[] sortList = {"Sorted by", "Popularity desc.", "Popularity asc.", "Release date desc.", "Release date asc.", "Vote average desc.", "Vote average asc.", "Vote count desc.", "Vote count asc."};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sortList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(dataAdapter);

        next.setOnClickListener(v -> next(txtPage));
        previous.setOnClickListener(v -> previous(txtPage));
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(spinnerSort.getSelectedItem()).equals("")) {
                    choiceSort = "popularity.desc";
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Popularity desc.")) {
                    choiceSort = "popularity.desc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Popularity asc.")) {
                    choiceSort = "popularity.asc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Release date desc.")) {
                    choiceSort = "release_date.desc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Release date asc.")) {
                    choiceSort = "release_date.asc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Vote average desc.")) {
                    choiceSort = "vote_average.desc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Vote average asc.")) {
                    choiceSort = "vote_average.asc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Vote count desc.")) {
                    choiceSort = "vote_count.desc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Vote count asc.")) {
                    choiceSort = "vote_count.asc";
                    reload();
                } else if(String.valueOf(spinnerSort.getSelectedItem()).equals("Sorted by")) {
                } else{
                    choiceSort = "popularity.desc";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.choiceSort = getActivity().getIntent().getStringExtra(MovieListFragment.CLE_DONNEE_CHOIX_TRI);

        reload();

    }

    public void next (TextView txtPage) {
        idpage++;

        txtPage.setText(String.format("Page %d", this.idpage));
        reload();
    }

    public void previous (TextView txtPage) {
        if(idpage!=1) {
            idpage--;
        }

        txtPage.setText(String.format("Page %d", this.idpage));
        reload();
    }

    public void reload() {
        mainController.onCreate();
    }
}