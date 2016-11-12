package com.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable{

    public String id;

    @SerializedName("is_adult")
    public boolean isAdult;

    public String title;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("original_language")
    public String originalLanguage;

    public double popularity;

    @SerializedName("vote_count")
    public long voteCount;

    @SerializedName("vote_average")
    public double voteAverage;

    @SerializedName("overview")
    public String overView;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("poster_path")
    public String posterPath;

    protected Movie(Parcel in) {
        id = in.readString();
        isAdult = in.readByte() != 0;
        title = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        popularity = in.readDouble();
        voteCount = in.readLong();
        voteAverage = in.readDouble();
        overView = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath(){
        return "http://image.tmdb.org/t/p/w185" + posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isAdult ? 1 : 0));
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeDouble(popularity);
        dest.writeLong(voteCount);
        dest.writeDouble(voteAverage);
        dest.writeString(overView);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
    }
}
