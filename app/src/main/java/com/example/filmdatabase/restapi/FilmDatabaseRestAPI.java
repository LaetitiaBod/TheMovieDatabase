package com.example.filmdatabase.restapi;


import com.example.filmdatabase.model.RestDetailResponse;
import com.example.filmdatabase.model.RestListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FilmDatabaseRestAPI {

    @GET ("discover/movie?api_key=7ab37008e76e29b77ad111d465cf446c")
    Call<RestListResponse> getListFilm(@Query("page") int page, @Query("sort_by") String choiceSort);

    @GET ("movie/{id}?api_key=7ab37008e76e29b77ad111d465cf446c")
    Call<RestDetailResponse>getId(@Path("id") int id);

}
