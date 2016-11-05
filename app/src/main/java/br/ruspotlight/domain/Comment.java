package br.ruspotlight.domain;

/**
 * Created by Rafael on 05/11/2016.
 */

public class Comment {
    private String text;
    private String date;
    private String author;
    
    public Comment(String text, String date, String author) {
        this.text = text;
        this.date = date;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
