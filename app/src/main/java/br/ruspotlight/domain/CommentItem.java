package br.ruspotlight.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 05/11/2016.
 */

public class CommentItem extends CommentComponent{
    private String comment;
    private String date;
    private User author;
    private List<User> likes;


    public CommentItem(String comment, String date, User author) {
        this.comment = comment;
        this.date = date;
        this.author = author;
        this.likes = new ArrayList<>();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void addLike(User user){
        this.likes.add(user);
    }

    public void removeLike(User user){
        this.likes.remove(user);
    }

    public int getNumberOfLikes(){
        return this.likes.size();
    }

}
