package br.ruspotlight.domain;

/**
 * Created by Rafael on 25/11/2016.
 */

public class Rating {

    Double rating;
    User user;

    public Rating(){
    }

    public Rating(Double rating, User user){
        this.rating = rating;
        this.user = user;
    }

    public Rating(Double rating){
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

