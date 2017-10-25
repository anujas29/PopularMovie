package com.anuja.project.popularmovie.task;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anuja.project.popularmovie.R;
import com.anuja.project.popularmovie.data.MoviesContract;
import com.anuja.project.popularmovie.pojoClass.MoviData;
import com.anuja.project.popularmovie.pojoClass.Review;
import com.anuja.project.popularmovie.pojoClass.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANUJA on 15-03-2017.
 */

public class MoviesDetailActivity1 extends Fragment implements FetchTrailerTask.FetchTailerListener, TrailerAdapter.onTailerCallbacks,
FetchReviewTask.FetchReviewListener,ReviewAdapter.onReviewCallbacks{

    public static final String EXTRA_TRAILERS = "EXTRA_TRAILERS";
    public static final String ARG_MOVIE = "ARG_MOVIE";
    public static final String EXTRA_REVIEWS = "EXTRA_REVIEWS";
    View v;
    ArrayList<Trailer> mTrailer = new ArrayList<>();

    ImageView Movi_image;
    TextView release_date, rating, overview, title;
    private MoviData data;
    Button ButtonMarkAsFavorite;
    Button ButtonRemoveFavorites;
    Button ButtonWatchTrailer;

    public  TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    RecyclerView mRecyclerViewTrailers;
    RecyclerView mRecycleViewReview;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("---------------------------- Z3 ----------------------");

        if (getArguments().containsKey(ARG_MOVIE)) {
            data = getArguments().getParcelable(ARG_MOVIE);
            //data = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        }
        setHasOptionsMenu(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.detail_activity, container, false);

        Movi_image = (ImageView) v.findViewById(R.id.Movi_image);
        release_date = (TextView) v.findViewById(R.id.release_date);
        rating = (TextView) v.findViewById(R.id.rating);
        overview = (TextView) v.findViewById(R.id.overview);
        title = (TextView) v.findViewById(R.id.Movie_title);

        //data = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        //setTitle(data.getOriginal_title());
        title.setText(data.getOriginal_title());
        release_date.setText("Released: " + data.getRelease_date());
        rating.setText("Rated: " + data.getVote_average());
        overview.setText(data.getOverview());

        Uri builder2 = Uri.parse("http://image.tmdb.org/t/p/w185/").buildUpon()
                .appendEncodedPath(data.getPoster_path())
                .build();
        Picasso.with(getContext()).load(builder2.toString()).fit().into(Movi_image);

        ButtonMarkAsFavorite = (Button) v.findViewById(R.id.button_mark_as_favorite);
        ButtonRemoveFavorites = (Button) v.findViewById(R.id.button_remove_from_favorites);
        ButtonWatchTrailer = (Button)v.findViewById(R.id.button_watch_trailer);


        updateFavoriteButton();

        mRecyclerViewTrailers = (RecyclerView) v.findViewById(R.id.trailer_list);
        mTrailerAdapter = new TrailerAdapter(new ArrayList<Trailer>(), this);

        mRecyclerViewTrailers.setLayoutManager(layoutManager);
        mRecyclerViewTrailers.setAdapter(mTrailerAdapter);

        mRecycleViewReview = (RecyclerView)v.findViewById(R.id.review_list);
        mReviewAdapter = new ReviewAdapter(new ArrayList<Review>(), this);
        mRecycleViewReview.setAdapter(mReviewAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_TRAILERS)) {
            List<Trailer> trailers = savedInstanceState.getParcelableArrayList(EXTRA_TRAILERS);
            mTrailerAdapter.add(trailers);
            ButtonWatchTrailer.setEnabled(true);

        } else {
            FetchTrailerAsyncTask();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_REVIEWS)) {
            List<Review> review = savedInstanceState.getParcelableArrayList(EXTRA_REVIEWS);
            mReviewAdapter.add(review);
        } else {
            FetchReviewAsyncTask();
        }

        return v;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Trailer> trailers = mTrailerAdapter.getTrailers();
        if (trailers != null && !trailers.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_TRAILERS, trailers);
        }

        ArrayList<Review> review = mReviewAdapter.getReviews();
        if (review != null && !review.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_REVIEWS, review);
        }
    }

    private void FetchTrailerAsyncTask() {
        //new FetchTrailerTask(this).execute();
        FetchTrailerTask task = new FetchTrailerTask(this);
        task.execute(data.getId());
    }

    private void FetchReviewAsyncTask() {
        FetchReviewTask Reviewtask = new FetchReviewTask(this);
        Reviewtask.execute(data.getId());
    }

    @Override
    public void onFetchTailerFinished(List<Trailer> trailers) {
        mTrailerAdapter.add(trailers);

        ButtonWatchTrailer.setEnabled(!trailers.isEmpty());

        if (mTrailerAdapter.getItemCount() > 0) {
            Trailer trailer = mTrailerAdapter.getTrailers().get(0);

        }
    }

    @Override
    public void watch(Trailer trailer, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getTrailerUrl())));

    }

    @Override
    public void onFetchReviewFinished(List<Review> review) {
        mReviewAdapter.add(review);
    }

    @Override
    public void read(Review review, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(review.getUrl())));
    }

    private void updateFavoriteButton() {
        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                return isFavorite();
            }

            @Override
            protected void onPostExecute(Boolean isFavorite) {
                if (isFavorite) {
                    ButtonRemoveFavorites.setVisibility(View.VISIBLE);
                    ButtonMarkAsFavorite.setVisibility(View.GONE);
                } else {
                    ButtonMarkAsFavorite.setVisibility(View.VISIBLE);
                    ButtonRemoveFavorites.setVisibility(View.GONE);
                }
            }
        }.execute();

        ButtonMarkAsFavorite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        markAsFavorite();
                    }
                });

        ButtonRemoveFavorites.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeFromFavorites();
                    }
                }
        );

        ButtonWatchTrailer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTrailerAdapter.getItemCount() > 0) {
                            watch(mTrailerAdapter.getTrailers().get(0), 0);
                        }
                    }
                });
    }

    private boolean isFavorite() {
        Cursor movieCursor = getContext().getContentResolver().query(
                MoviesContract.MovieEntry.CONTENT_URI,
                new String[]{MoviesContract.MovieEntry.COLUMN_MOVIE_ID},
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + "=" + data.getId(),
                null,
                null
        );

        if (movieCursor != null && movieCursor.moveToFirst()) {

            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }

    public void markAsFavorite() {

        if (!isFavorite()) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(MoviesContract.MovieEntry.COLUMN_POSTER_IMAGE, data.getPoster_path());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_TITLE, data.getOriginal_title());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, data.getOverview());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, data.getRelease_date());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, data.getPopularity());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE, data.getVote_average());
            movieValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_ID, data.getId());
            // Uri uri = getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, movieValues);
            getContext().getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, movieValues);
            Toast.makeText(getContext(), "Added As Favorite", Toast.LENGTH_LONG).show();
            updateFavoriteButton();
        }
    }

    public void removeFromFavorites() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isFavorite()) {
                    getContext().getContentResolver().delete(MoviesContract.MovieEntry.CONTENT_URI,
                            MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " = " + data.getId(), null);
                   // Toast.makeText(getContext(), "Remove from Favorite", Toast.LENGTH_LONG).show();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(getContext(), "Remove from Favorite", Toast.LENGTH_LONG).show();
                updateFavoriteButton();
            }
        }.execute();
    }
}

