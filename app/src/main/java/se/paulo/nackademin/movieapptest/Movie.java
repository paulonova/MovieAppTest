package se.paulo.nackademin.movieapptest;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("Title")   //from JSON
    private String title;     // from Java

    @SerializedName("Year")
    private int year;

    @SerializedName("Rated")
    private char rated;

    @SerializedName("Released")
    private String released;

    @SerializedName("Poster")
    private String poster;


    //Getters
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public char getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getPoster() {
        return poster;
    }
}
