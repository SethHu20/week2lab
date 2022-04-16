package com.fit2099.week7lab;

import androidx.annotation.NonNull;

public class Movie {
    private String title;
    private int year;
    private String country;
    private String genre;
    private float cost;
    private String keywords;
    private int actors;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getActors() {
        return actors;
    }

    public void setActors(int actors) {
        this.actors = actors;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getTitle() + " | " + this.getYear();
    }
}
