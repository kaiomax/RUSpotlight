package br.ruspotlight.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael on 05/11/2016.
 */

public class Comment {
    private String text;
    private String date;
    private String author;
    private List<User> curtidas;

    public Comment(String text, String date, String author) {
        this.text = text;
        this.date = date;
        this.author = author;
        this.curtidas = new ArrayList<>();
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

    public void adicionarCurtida(User user){
        this.curtidas.add(user);
    }

    public void removerCurtida(User user){
        this.curtidas.remove(user);
    }

    public int getQuantidadeCurtidas(){
        return this.curtidas.size();
    }
}
