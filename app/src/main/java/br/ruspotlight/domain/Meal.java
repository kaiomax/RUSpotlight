package br.ruspotlight.domain;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String title;
    private String date;
    List<Rating> ratings;

    public Meal(String t, String d) {
        title = t;
        date = d;
        ratings = new ArrayList<>();
    }

    public String getTitle() { return  title; }
    public void setTitle(String t) { title = t; }

    public String getDate() { return  date; }
    public void setDate(String d) { date = d; }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void insertRating(Double newRating, User user){
        this.ratings.add(new Rating(newRating,user));
    }

    public Double getRatingAverage(){
        Double sum = 0.0;
        for (Rating rating: this.ratings){
            sum += rating.getRating();
        }
        return sum / this.ratings.size();
    }
}
