package com.anuja.project.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.anuja.project.popularmovie.task.MoviesDetailActivity1;

/**
 * Created by ANUJA on 14-03-2017.
 */

public class MovieDetailFragment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_fragment);

        if(savedInstanceState == null)
        {

            Bundle arguments = new Bundle();
            arguments.putParcelable(MoviesDetailActivity1.ARG_MOVIE,
                    getIntent().getParcelableExtra(Intent.EXTRA_TEXT));
            MoviesDetailActivity1 fragment = new MoviesDetailActivity1();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();

    }

    }
}
