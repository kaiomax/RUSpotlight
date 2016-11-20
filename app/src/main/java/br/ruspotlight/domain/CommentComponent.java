package br.ruspotlight.domain;

import java.util.List;

/**
 * Created by Rafael on 14/11/2016.
 */

public abstract class CommentComponent {

    public void add(CommentComponent commentComponent){
        throw new UnsupportedOperationException();
    }

    public void remove(CommentComponent commentComponent){
        throw new UnsupportedOperationException();
    }

    public CommentComponent getChild(int i){
        throw new UnsupportedOperationException();
    }

    public String getComment(){
        throw new UnsupportedOperationException();
    }

    public String getDate(){
        throw new UnsupportedOperationException();
    }

    public User getAuthor(){
        throw new UnsupportedOperationException();
    }

    public List<User> getLikes(){
        throw new UnsupportedOperationException();
    }

    public void setComment(String comment){
        throw new UnsupportedOperationException();
    }

    public void setDate(String date){
        throw new UnsupportedOperationException();
    }

    public void setAuthor(User user){
        throw new UnsupportedOperationException();
    }

    public void setLikes(List<User> likes){
        throw new UnsupportedOperationException();
    }
}
