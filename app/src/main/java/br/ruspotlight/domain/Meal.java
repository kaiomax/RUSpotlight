package br.ruspotlight.domain;

public class Meal {
    public String type;
    public String date;

    public Meal() {
    }

    public Meal(String type, String date) {
        this.type = type;
        this.date = date;
    }

    @Override
    public String toString() {
        String title;
        switch (type) {
            case "LUNCH":
                title = "Almoço";
                break;
            case "DINNER":
                title = "Jantar";
                break;
            default:
                title = "X";
        };
        return title + " - " + date;
    }
}
