package br.ruspotlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.ruspotlight.domain.Comment;

public class MealComments extends AppCompatActivity {

    private List<Comment> comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_comments);
        //Intent para receber a lista de comentarios da refeicao selecionada
        Intent intentReceived = getIntent();
        //comments = (ArrayList<Comment>) intentReceived.getExtras().get("comments");
    }
}
