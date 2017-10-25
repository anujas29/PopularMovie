package com.anuja.project.popularmovie.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ANUJA on 13-03-2017.
 */

public class Review implements Parcelable {

    @SerializedName("id")
    private String Id;
    @SerializedName("author")
    private String Author;
    @SerializedName("content")
    private String Content;
    @SerializedName("url")
    private String Url;

    public Review(String id, String author, String content, String url) {
        Id = id;
        Author = author;
        Content = content;
        Url = url;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Review()
    {}

    public static final Parcelable.Creator<Review> CREATOR = new Creator<Review>() {
        public Review createFromParcel(Parcel source) {
            Review review = new Review();
            review.Id = source.readString();
            review.Author = source.readString();
            review.Content = source.readString();
            review.Url = source.readString();
            return review;
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(Id);
        parcel.writeString(Author);
        parcel.writeString(Content);
        parcel.writeString(Url);
    }
}
