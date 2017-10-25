package com.anuja.project.popularmovie.utilities;

import com.anuja.project.popularmovie.pojoClass.Trailer;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANUJA on 14-03-2017.
 */

public class TrailerResponse {

    @SerializedName("results")
    private List<Trailer> trailers = new ArrayList<>() ;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
