package br.ruspotlight.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import br.ruspotlight.MealActivity;
import br.ruspotlight.R;
import br.ruspotlight.domain.Meal;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private ArrayList<Meal> mDataset;
    private Context ctx;
    ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    public MealAdapter(ArrayList<Meal> myDataset, Context ctx) {
        this.mDataset = myDataset;
        this.ctx = ctx;
    }

    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);

        return new ViewHolder(v, ctx, mDataset);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String letter;
        Meal m = mDataset.get(position);

        letter = String.valueOf(m.getTitle().charAt(0));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, colorGenerator.getRandomColor());

        holder.iconLetter.setImageDrawable(drawable);
        holder.txtHeader.setText(m.getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView iconLetter;
        public TextView txtHeader;
        private ArrayList<Meal> meals;
        private Context ctx;

        public ViewHolder(View itemView, Context ctx, ArrayList<Meal> meals) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.ctx = ctx;
            this.meals = meals;
            iconLetter = (ImageView) itemView.findViewById(R.id.icon);
            txtHeader = (TextView) itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Meal meal = meals.get(position);
            Intent i = new Intent(this.ctx, MealActivity.class);
            i.putExtra("TITLE", meal.getTitle() + " - " + meal.getDate());
            this.ctx.startActivity(i);
//            Intent i = new Intent(this.ctx, cardapioActivity.class);
//            i.putExtra("TITLE", meal.getTitle() + " - " + meal.getDate());
//            this.ctx.startActivity(i);
        }
    }
}
