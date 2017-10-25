package com.anuja.project.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anuja.project.popularmovie.data.MoviesContract;
import com.anuja.project.popularmovie.pojoClass.MoviData;
import com.anuja.project.popularmovie.rest.MovieClient;
import com.anuja.project.popularmovie.rest.MovieInterface;
import com.anuja.project.popularmovie.utilities.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ANUJA on 18-01-2017.
 */
public class MoviFragement extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {

    ArrayList<MoviData> MovieDataList = new ArrayList<>();
    public static MovieAdapter adapter;
    String choice = "popular";
    View v;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private static final int TASK_LOADER_ID = 0;
    private static final String EXTRA_CHOICE = "EXTRA_CHOICE";
    private static final String EXTRA_MOVIES = "EXTRA_MOVIES";
    private static final String TAG = MoviFragement.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
         layoutManager = new GridLayoutManager(getContext(), 2);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(getContext(), 2);
        }
        else{
            layoutManager = new GridLayoutManager(getContext(), 4);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<MoviData> movie = adapter.getMovies();
        if (movie != null && !movie.isEmpty()) {
            outState.putParcelableArrayList(EXTRA_MOVIES, movie);
        }
        outState.putString(EXTRA_CHOICE, choice);

        if (!choice.equals("favourites")) {
            getLoaderManager().destroyLoader(TASK_LOADER_ID);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SortByMenu:
                startActivity(new Intent(getContext(), SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.grid_view, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.gridview);
        adapter = new MovieAdapter(MovieDataList, getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            choice = savedInstanceState.getString(EXTRA_CHOICE);
            if (savedInstanceState.containsKey(EXTRA_MOVIES)) {
                ArrayList<MoviData> movies = savedInstanceState.getParcelableArrayList(EXTRA_MOVIES);
                adapter.add(movies);

                if (choice.equals("favourites")) {
                    getLoaderManager().initLoader(TASK_LOADER_ID,null,this);
                }
            }
        }
        recyclerView.addOnItemTouchListener(new RecyclerviewTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(getContext(), MovieDataList.get(position)+ " is selected!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MovieDetailFragment.class);
                intent.putExtra(Intent.EXTRA_TEXT, MovieDataList.get(position));
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isNetworkConnected()) {
                getMovies();
            }
        else
        Toast.makeText(getContext(), getString(R.string.no_internet_available), Toast.LENGTH_LONG).show();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager conMgr =  (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MoviesContract.MovieEntry.CONTENT_URI;
        return new CursorLoader(getContext(),
                uri,
                MoviesContract.MovieEntry.MOVIE_COLUMNS,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.add(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void getMovies()
    {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String value = prefs.getString(getString(R.string.pref_sort_key),getString(R.string.pref_highest_rate_value));
        choice = value;
        String log = "hiii";
       // Toast.makeText(getContext(),choice, Toast.LENGTH_LONG).show();
        if(choice.equals("favourites"))
        {
            getLoaderManager().initLoader(TASK_LOADER_ID, null, this);
        }
        if(choice.equals("top_rated"))
        {
            MovieInterface apiService =
                    MovieClient.getClient().create(MovieInterface.class);
            Call<MovieResponse> call = apiService.getTopRatedMovies(BuildConfig.MOVIE_DATABASE_API);

           call.enqueue(new Callback<MovieResponse>() {
               @Override
               public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                   ArrayList<MoviData> movies = response.body().getResults();
                   Log.d(TAG, "Number of movies received: " + movies.size());
                   adapter.add(movies);
               }
               @Override
               public void onFailure(Call<MovieResponse> call, Throwable t) {

               }
           });
        }
        if(choice.equals("popular"))
        {
            MovieInterface apiService =
                    MovieClient.getClient().create(MovieInterface.class);

            Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.MOVIE_DATABASE_API);

            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    ArrayList<MoviData> movies = response.body().getResults();
                    Log.d(TAG, "Number of movies received: " + movies.size());
                    adapter.add(movies);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    public static class RecyclerviewTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
       // private MainActivity.ClickListener clickListener;
        private ClickListener clickListener;

        public RecyclerviewTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}

