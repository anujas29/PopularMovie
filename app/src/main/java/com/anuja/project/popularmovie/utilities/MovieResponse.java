package com.anuja.project.popularmovie.utilities;

import com.anuja.project.popularmovie.pojoClass.MoviData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ANUJA on 12-03-2017.
 */

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<MoviData> results;

    public ArrayList<MoviData> getResults() {
        return results;
    }

}
