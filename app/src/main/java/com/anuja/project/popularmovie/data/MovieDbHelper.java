package com.anuja.project.popularmovie.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ANUJA on 07-03-2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "popularmovie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_MOVIE = "CREATE TABLE " + MoviesContract.MovieEntry.TABLE_NAME + "(" +
                MoviesContract.MovieEntry.COLUMN_MOVIE_ID + " TEXT PRIMARY KEY," +
                MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                MoviesContract.MovieEntry.COLUMN_OVERVIEW+ " TEXT NOT NULL," +
                MoviesContract.MovieEntry.COLUMN_POSTER_IMAGE + " TEXT NOT NULL," +
                MoviesContract.MovieEntry.COLUMN_RELEASE_DATE+ " TEXT NOT NULL," +
                MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE+ " REAL TEXT NOT NULL," +
                MoviesContract.MovieEntry.COLUMN_POPULARITY+ " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_TABLE_MOVIE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
