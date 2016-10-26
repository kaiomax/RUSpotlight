package br.ruspotlight;

import android.content.res.ColorStateList;
import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.RatingBar;

public class MealActivity extends AppCompatActivity {

    private boolean checked;
    private FloatingActionButton fab;
    private View inflated;
    private ViewStub stub;
    private RatingBar mealRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("TITLE"));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(0xFFef625b));
        checked = false;

        stub = (ViewStub) findViewById(R.id.stub_ranking);
        inflated = stub.inflate();
        inflated.setVisibility(View.GONE);

        mealRating = (RatingBar) findViewById(R.id.rating_bar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checked){

                    checked = true;
                    inflated.setVisibility(View.VISIBLE);
                }else{
                    inflated.setVisibility(View.GONE);
                    checked = false;
                }

            }
        });

        /*
        mealRating.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mealRating.setFocusable(false);
            }
        });*/


        mealRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                fab.setBackgroundTintList(ColorStateList.valueOf(0xFF4CAF50));
                mealRating.setClickable(false);
                mealRating.setFocusable(false);
            }
        });
    }
}
