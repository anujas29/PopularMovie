package com.anuja.project.popularmovie.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ANUJA on 13-03-2017.
 */

public class Trailer implements Parcelable {

    @SuppressWarnings("unused")
    public static final String LOG_TAG = Trailer.class.getSimpleName();

    @SerializedName("id")
    private String Id;
    @SerializedName("key")
    private String Key;
    @SerializedName("size")
    private String Size;
    @SerializedName("site")
    private String Site;
    @SerializedName("name")
    private String Name;



    private Trailer() {
    }


    public String getTrailerUrl() {
        return "http://www.youtube.com/watch?v=" +Key;
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Creator<Trailer>() {
        public Trailer createFromParcel(Parcel source) {
            Trailer trailer = new Trailer();
            trailer.Id = source.readString();
            trailer.Key = source.readString();
            trailer.Name = source.readString();
            trailer.Site = source.readString();
            trailer.Size = source.readString();
            return trailer;
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(Id);
        parcel.writeString(Key);
        parcel.writeString(Name);
        parcel.writeString(Site);
        parcel.writeString(Size);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

