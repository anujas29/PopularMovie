package com.anuja.project.popularmovie;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anuja.project.popularmovie.data.MoviesContract;
import com.anuja.project.popularmovie.pojoClass.MoviData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by ANUJA on 21-01-2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyMoviesViewHolder>{

    private ArrayList<MoviData> MovieItemList;
    Context mContext;
    private Cursor mCursor;

    public MovieAdapter(ArrayList<MoviData> MovieList, Context context) {
        MovieItemList = MovieList;
        this.mContext = context;
    }

    @Override
    public MyMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_element, parent, false);
        return new MyMoviesViewHolder(mView);
    }

    @Override
    public void onViewRecycled(MyMoviesViewHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override
    public int getItemCount() {
        return MovieItemList.size();
    }

    @Override
    public void onBindViewHolder(MyMoviesViewHolder holder, int position) {

        MoviData movie = MovieItemList.get(position);
       // holder.textView.setText(movie.getOriginal_title());

        //System.out.println("------------------------------ onBindViewHolder::::");
        Uri builder = Uri.parse("http://image.tmdb.org/t/p/w500/").buildUpon()
                .appendEncodedPath(movie.getPoster_path())
                .build();
        Picasso.with(mContext).load(builder).into(holder.imageView);
    }

    public class MyMoviesViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;

        public MyMoviesViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_view);
            //textView = (TextView) view.findViewById(R.id.text_view);
        }
    }


    public ArrayList<MoviData> getMovies() {
        return MovieItemList;
    }

    public void add(ArrayList<MoviData> movie) {
        MovieItemList.clear();
        MovieItemList.addAll(movie);
        notifyDataSetChanged();
    }

    public void add(Cursor cursor) {
        MovieItemList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                //String id = cursor.getString(MoviesContract.MovieEntry.COL_MOVIE_ID);
                long id = cursor.getLong(MoviesContract.MovieEntry.COL_MOVIE_ID);
                String original_title = cursor.getString(MoviesContract.MovieEntry.COL_TITLE);
                String overview = cursor.getString(MoviesContract.MovieEntry.COL_OVERVIEW);
                String vote_average = cursor.getString(MoviesContract.MovieEntry.COL_AVERAGE_VOTE);
                String poster_path = cursor.getString(MoviesContract.MovieEntry.COL_POSTER_IMAGE);
                String release_date = cursor.getString(MoviesContract.MovieEntry.COL_RELEASE_DATE);
                String popularity = cursor.getString(MoviesContract.MovieEntry.COL_POPULARITY);
                MoviData movie = new MoviData(id,original_title,poster_path,overview,vote_average,release_date,popularity);
                System.out.println("---------------------------------------- movie:::"+movie);
                MovieItemList.add(movie);
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}

//    COLUMN_MOVIE_ID,
//    COLUMN_TITLE,
//    COLUMN_OVERVIEW,
//    COLUMN_AVERAGE_VOTE,
//    COLUMN_POSTER_IMAGE,
//    COLUMN_RELEASE_DATE,
//    COLUMN_POPULARITY
    //poster_path, original_title, overview, release_date, popularity, vote_average,id

//    public void add(Cursor cursor) {
//        MovieItemList.clear();
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                String id = cursor.getString(MoviesContract.MovieEntry.COL_MOVIE_ID);
//                String original_title = cursor.getString(MoviesContract.MovieEntry.COL_TITLE);
//                String overview = cursor.getString(MoviesContract.MovieEntry.COL_OVERVIEW);
//                String vote_average = cursor.getString(MoviesContract.MovieEntry.COL_AVERAGE_VOTE);
//                String poster_path = cursor.getString(MoviesContract.MovieEntry.COL_POSTER_IMAGE);
//                String release_date = cursor.getString(MoviesContract.MovieEntry.COL_RELEASE_DATE);
//                String popularity = cursor.getString(MoviesContract.MovieEntry.COL_POPULARITY);
//                MoviData movie = new MoviData(poster_path, original_title, overview, release_date, popularity, vote_average,id);
//                System.out.println("---------------------------------------- movie:::"+movie);
//                MovieItemList.add(movie);
//            } while (cursor.moveToNext());
//        }
//        notifyDataSetChanged();
//    }

