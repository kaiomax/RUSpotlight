package br.ruspotlight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ruspotlight.domain.Meal;

public class HomeFragment extends Fragment {
    private RecyclerView mealsList;

    private DatabaseReference mDatabaseMeals;

    private ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mDatabaseMeals = FirebaseDatabase.getInstance().getReference().child("meals");

        mealsList = (RecyclerView) v.findViewById(R.id.meals_list);
        mealsList.setHasFixedSize(true);
        mealsList.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerAdapter<Meal, MealViewHolder> adapter = new FirebaseRecyclerAdapter<Meal, MealViewHolder>(
                Meal.class,
                R.layout.item_meal,
                MealViewHolder.class,
                mDatabaseMeals
        ) {
            @Override
            protected void populateViewHolder(MealViewHolder viewHolder, final Meal model, int position) {
                final String mealKey = getRef(position).getKey();

                viewHolder.setContent(model);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mealIntent = new Intent(getActivity(), MealActivity.class);
                        mealIntent.putExtra("title", model.toString());
                        mealIntent.putExtra("meal_key", mealKey);
                        getActivity().startActivity(mealIntent);
                    }
                });
            }
        };

        mealsList.setAdapter(adapter);

        return  v;
    }

    private static class MealViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconLetter;
        private TextView textTitle;
        private TextView textSubtitle;
        public View mView;

        public MealViewHolder(View v) {
            super(v);
            mView = v;

            iconLetter = (ImageView) v.findViewById(R.id.icon);
            textTitle = (TextView) v.findViewById(R.id.title);
            textSubtitle = (TextView) v.findViewById(R.id.subtitle);
        }

        public void setContent(Meal meal) {
            String letter, title;
            int color;

            switch (meal.type) {
                case "LUNCH":
                    color = 0xffffd54f;
                    letter = "A";
                    title = "Almoço";
                    break;
                case "DINNER":
                    color = 0xff2c3e50;
                    letter = "J";
                    title = "Jantar";
                    break;
                default:
                    color = 0xff90a4ae;
                    letter = "X";
                    title = "X";
            }

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(letter, color);

            iconLetter.setImageDrawable(drawable);
            textTitle.setText(title);
            textSubtitle.setText(meal.date);
        }
    }
}
