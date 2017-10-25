package com.anuja.project.popularmovie.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.anuja.project.popularmovie.data.MoviesContract.MovieEntry.TABLE_NAME;

/**
 * Created by ANUJA on 08-03-2017.
 */

public class MoviesContentProvider extends ContentProvider {

    private MovieDbHelper mTaskDbHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;

    public static UriMatcher buildUriMatcher() {

        // Initialize a UriMatcher with no matches by passing in NO_MATCH to the constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_TASKS, TASKS);
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_TASKS + "/#", TASK_WITH_ID);

        return uriMatcher;
    }

//    @Override
//    public String getType(@NonNull Uri uri) {
//        int match = sUriMatcher.match(uri);
//        String type;
//        switch (match) {
//            case TASKS:
//                type = MoviesContract.MovieEntry.CONTENT_TYPE;
//                break;
//            case TASK_WITH_ID:
//                type = MoviesContract.MovieEntry.CONTENT_ITEM_TYPE;
//                break;
//            default:
//                throw new UnsupportedOperationException();
//        }
//        return type;
//    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTaskDbHelper = new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            // Query for the tasks directory
            case TASKS:
                retCursor =  db.query(MoviesContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default exception
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        // Write URI matching code to identify the match for the tasks directory
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case TASKS:
                // Insert new values into the database
                // Inserting values into tasks table
//                System.out.print("--------------------- uri:::"+uri);
//                System.out.print("--------------------- values:::"+values);
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(MoviesContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDelete;

        if (null == selection) {
            selection = "1";
        }
        switch (match) {
            case TASKS:
                rowsDelete = db.delete(
                        MoviesContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDelete != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDelete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
