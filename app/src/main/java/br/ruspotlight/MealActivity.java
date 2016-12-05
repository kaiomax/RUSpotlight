package br.ruspotlight;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MealActivity extends AppCompatActivity {

    private static final String TAG = "RUS/MEAL";

    private String mealKey = null;
    private boolean checkedFab = false;
    private FloatingActionButton fab;
    private View inflated;
    private ViewStub stub;
    private RatingBar mealRating;

    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);

        mealKey = getIntent().getExtras().getString("meal_key");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("meals").child(mealKey);
        user = FirebaseAuth.getInstance().getCurrentUser();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(0xFF8A8E9C));

        stub = (ViewStub) findViewById(R.id.stub_ranking);
        inflated = stub.inflate();
        inflated.setVisibility(View.GONE);

        mealRating = (RatingBar) findViewById(R.id.rating_bar);

        checkUserRated();

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(user == null) {
                    Toast.makeText(MealActivity.this, "É necessário realizar o login para poder avaliar.", Toast.LENGTH_LONG).show();
                } else {
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
            }
        });

        mealRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.d(TAG, Float.toString(mealRating.getRating()));
                mealRating.setClickable(false);
                mealRating.setFocusable(false);

                DatabaseReference ratesRef = mDatabase.child("ratings");
                ratesRef.child(user.getUid()).setValue(mealRating.getRating());
            }
        });
    }

    public void showComments(View view) {
        Intent i = new Intent(MealActivity.this, CommentsActivity.class);
        startActivity(i);
    }

    private void checkUserRated() {
        if(user != null) {
            final String userId = user.getUid();

            mDatabase.child("ratings").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataSnapshot userRatingSnapshot = dataSnapshot.child(userId);
                    if(userRatingSnapshot.getValue() != null) {
                        Float rating = userRatingSnapshot.getValue(Float.class);

                        fab.setImageResource(R.drawable.check);
                        fab.setBackgroundTintList(ColorStateList.valueOf(0xFF4CAF50));
                        checkedFab = true;
                        inflated.setVisibility(View.VISIBLE);
                        mealRating.setRating(rating);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}