package br.ruspotlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import br.ruspotlight.domain.CommentItem;

public class MealComments extends AppCompatActivity {

    private List<CommentItem> commentItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_comments);
        //Intent para receber a lista de comentarios da refeicao selecionada
        Intent intentReceived = getIntent();
        //commentItems = (ArrayList<CommentItem>) intentReceived.getExtras().get("commentItems");
    }
}
