package br.ruspotlight.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 14/11/2016.
 */

public class Comment extends CommentComponent {
    private List<CommentComponent> commentComponents;
    private String comment;
    private String date;
    private User author;
    private List<User> likes;

    public Comment(String comment, String date, User author) {
        this.commentComponents = new ArrayList();
        this.comment = comment;
        this.date = date;
        this.author = author;
        this.likes = new ArrayList();
    }

    public void add(CommentComponent commentComponent){
        this.commentComponents.add(commentComponent);
    }

    public void remove(CommentComponent commentComponent){
        this.commentComponents.remove(commentComponent);
    }

    public CommentComponent getChild(int i){
        return this.commentComponents.get(i);
    }

    public String getComment(){
        return this.comment;
    }

    public String getDate(){
        return this.date;
    }

    public User getAuthor(){
        return this.author;
    }

    public List<User> getLikes(){
        return this.likes;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setAuthor(User author){
        this.author = author;
    }

    public void setLikes(List<User> likes){
        this.likes = likes;
    }
}
