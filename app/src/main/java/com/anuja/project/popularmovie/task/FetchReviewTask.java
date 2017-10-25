package com.anuja.project.popularmovie.task;

import android.os.AsyncTask;
import android.util.Log;

import com.anuja.project.popularmovie.BuildConfig;
import com.anuja.project.popularmovie.pojoClass.Review;
import com.anuja.project.popularmovie.rest.MovieClient;
import com.anuja.project.popularmovie.rest.MovieInterface;
import com.anuja.project.popularmovie.utilities.ReviewResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.anuja.project.popularmovie.pojoClass.Trailer.LOG_TAG;

/**
 * Created by ANUJA on 16-03-2017.
 */

public class FetchReviewTask extends AsyncTask<Long, Void, List<Review>> {

    private final FetchReviewListener fetchListener;

    interface FetchReviewListener {
        void onFetchReviewFinished(List<Review> review);
    }

    public FetchReviewTask(FetchReviewListener fetchListener )
    {
        this.fetchListener = fetchListener;
    }


    @Override
    protected List<Review> doInBackground(Long... params) {
        if (params.length == 0) {
            return null;
        }
        long movieId = params[0];
//        MovieInterface apiService =
//                MovieClient.getClient().create(MovieInterface.class);

        MovieInterface service = MovieClient.getClient().create(MovieInterface.class);
        Call<ReviewResponse> call = service.findReviewById(movieId,BuildConfig.MOVIE_DATABASE_API);
        try {
            Response<ReviewResponse> response = call.execute();
            ReviewResponse review = response.body();
            return review.getReviews();
        } catch (IOException e) {
            Log.e(LOG_TAG, "A problem occurred talking to the movie db ", e);
        }

       return null;
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        if(reviews!= null)
        {
            fetchListener.onFetchReviewFinished(reviews);
        }
        else
        {
            fetchListener.onFetchReviewFinished(new ArrayList<Review>());
        }
    }
}
