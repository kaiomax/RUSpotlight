package br.ruspotlight.domain;

public class Meal {
    private String title;
    private String date;

    public Meal(String t, String d) {
        title = t;
        date = d;
    }

    public String getTitle() { return  title; }
    public void setTitle(String t) { title = t; }

    public String getDate() { return  date; }
    public void setDate(String d) { date = d; }
}
