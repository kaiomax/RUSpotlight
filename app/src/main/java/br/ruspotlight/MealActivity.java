package br.ruspotlight;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
//import android.media.Rating;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ruspotlight.domain.Rating;

public class MealActivity extends AppCompatActivity {

    private boolean checkedFab;
    private FloatingActionButton fab;
    private View inflated;
    private ViewStub stub;
    private RatingBar mealRating;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("TITLE"));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(0xFF8A8E9C));
        checkedFab = false;

        stub = (ViewStub) findViewById(R.id.stub_ranking);
        inflated = stub.inflate();
        inflated.setVisibility(View.GONE);

        mealRating = (RatingBar) findViewById(R.id.rating_bar);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("rates");

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (!checkedFab) {
                    fab.setImageResource(R.drawable.check);
                    fab.setBackgroundTintList(ColorStateList.valueOf(0xFF4CAF50));
                    checkedFab = true;
                    inflated.setVisibility(View.VISIBLE);
                }else{
                    inflated.setVisibility(View.GONE);
                    checkedFab = false;
                }
            }
        });

        mealRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rating: " + mealRating.getRating());
                mealRating.setClickable(false);
                mealRating.setFocusable(false);

                new AlertDialog.Builder(MealActivity.this)
                        .setIcon(R.mipmap.warning)
                        .setTitle("Confirmar Avaliação")
                        .setMessage("Esta é a sua nota final?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mealRating.setIsIndicator(true);
                                mealRating.setEnabled(false);

                                DatabaseReference newRateRef = mDatabase.push();
                                //newRateRef.setValue(new Rating((double) mealRating.getRating(), user));
                                newRateRef.setValue(new Rating((double) mealRating.getRating()));
                            }

                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });
    }

    public void verComentarios(View view) {
        Intent i = new Intent(MealActivity.this, CommentsActivity.class);
        startActivity(i);
    }
}