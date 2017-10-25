package com.anuja.project.popularmovie.utilities;

import com.anuja.project.popularmovie.pojoClass.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANUJA on 16-03-2017.
 */

public class ReviewResponse {

    @SerializedName("results")
    private List<Review> reviews = new ArrayList<>() ;

    public List<Review> getReviews() {
        return reviews;
    }

}
