package com.example.primemovie.Retrofif;

import com.example.primemovie.model.AllCategories;
import com.example.primemovie.model.BannerMovie;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("banner_movie.json")
    Observable<List<BannerMovie>> getAllBanners();

    @GET("{categoryId}/all_movies.json")
    Observable<List<AllCategories>> getAllCategoryMovies(@Path("categoryId") int categoryId);
}
