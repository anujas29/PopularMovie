package com.anuja.project.popularmovie.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ANUJA on 07-03-2017.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.anuja.project.popularmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "movies";

    private MoviesContract() {
    }

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        //public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;

        public static final String COLUMN_MOVIE_ID = "movi_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_AVERAGE_VOTE = "vote_average";
        public static final String COLUMN_POSTER_IMAGE = "poster_image";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POPULARITY = "popularity";

        public static final String[] MOVIE_COLUMNS = {
                COLUMN_MOVIE_ID,
                COLUMN_TITLE,
                COLUMN_OVERVIEW,
                COLUMN_AVERAGE_VOTE,
                COLUMN_POSTER_IMAGE,
                COLUMN_RELEASE_DATE,
                COLUMN_POPULARITY
        };

        public static final int COL_MOVIE_ID = 0;
        public static final int COL_TITLE = 1;
        public static final int COL_OVERVIEW = 2;
        public static final int COL_AVERAGE_VOTE = 3;
        public static final int COL_POSTER_IMAGE = 4;
        public static final int COL_RELEASE_DATE = 5;
        public static final int COL_POPULARITY = 6;


    }
}
