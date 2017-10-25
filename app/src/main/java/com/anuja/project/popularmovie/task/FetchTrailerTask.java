package com.anuja.project.popularmovie.task;

import android.os.AsyncTask;
import android.util.Log;

import com.anuja.project.popularmovie.BuildConfig;
import com.anuja.project.popularmovie.pojoClass.Trailer;
import com.anuja.project.popularmovie.rest.MovieClient;
import com.anuja.project.popularmovie.rest.MovieInterface;
import com.anuja.project.popularmovie.utilities.TrailerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.anuja.project.popularmovie.pojoClass.Trailer.LOG_TAG;

/**
 * Created by ANUJA on 14-03-2017.
 */

public class FetchTrailerTask extends AsyncTask<Long, Void, List<Trailer>> {


    private final FetchTailerListener mListener;

    public FetchTrailerTask(FetchTailerListener Listener )
    {
        this.mListener = Listener;
    }

    interface FetchTailerListener {
        void onFetchTailerFinished(List<Trailer> trailers);
    }


    @Override
    protected List<Trailer> doInBackground(Long... params) {

        if (params.length == 0) {
            return null;
        }
        long movieId = params[0];

//        MovieInterface apiService =
//                MovieClient.getClient().create(MovieInterface.class);

        MovieInterface service = MovieClient.getClient().create(MovieInterface.class);
        Call<TrailerResponse> call = service.findTrailersById(movieId,
                BuildConfig.MOVIE_DATABASE_API);
        try {
            Response<TrailerResponse> response = call.execute();
            TrailerResponse trailers = response.body();
            return trailers.getTrailers();
        } catch (IOException e) {
            Log.e(LOG_TAG, "A problem occurred talking to the movie db ", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {

        if (trailers != null) {
            mListener.onFetchTailerFinished(trailers);
        } else {
            mListener.onFetchTailerFinished(new ArrayList<Trailer>());
        }
    }
}
