package com.anuja.project.popularmovie.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ANUJA on 20-01-2017.
 */

public class MoviData implements Parcelable {

    @SerializedName("id")
    private long id;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String vote_average;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("popularity")
    private String popularity;

    private MoviData() {
    }


    public MoviData(long id, String original_title, String poster_path, String overview, String vote_average, String release_date, String popularity) {
        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.popularity = popularity;
    }

    public static final Parcelable.Creator<MoviData> CREATOR = new Creator<MoviData>() {
        public MoviData createFromParcel(Parcel source) {
            MoviData movie = new MoviData();
            movie.id = source.readLong();
            movie.original_title = source.readString();
            movie.poster_path = source.readString();
            movie.overview = source.readString();
            movie.vote_average = source.readString();
            movie.release_date = source.readString();
            movie.popularity = source.readString();
            return movie;
        }

        public MoviData[] newArray(int size) {
            return new MoviData[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(original_title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(vote_average);
        parcel.writeString(release_date);
        parcel.writeString(popularity);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
}
