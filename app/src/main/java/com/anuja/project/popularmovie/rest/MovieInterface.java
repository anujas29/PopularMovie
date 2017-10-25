package com.anuja.project.popularmovie.rest;

import com.anuja.project.popularmovie.utilities.MovieResponse;
import com.anuja.project.popularmovie.utilities.ReviewResponse;
import com.anuja.project.popularmovie.utilities.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ANUJA on 12-03-2017.
 */

public interface MovieInterface {

    @GET("3/movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("3/movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<TrailerResponse> findTrailersById(@Path("id") long movieId, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<ReviewResponse> findReviewById(@Path("id") long movieId, @Query("api_key") String apiKey);

}
