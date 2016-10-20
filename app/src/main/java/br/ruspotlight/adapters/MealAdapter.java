package br.ruspotlight.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import br.ruspotlight.R;
import br.ruspotlight.domain.Meal;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private ArrayList<Meal> mDataset;
    ColorGenerator colorGenerator = ColorGenerator.MATERIAL;

    public MealAdapter(ArrayList<Meal> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);

        return new ViewHolder(v);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconLetter;
        public TextView txtHeader;

        public ViewHolder(View itemView) {
            super(itemView);
            iconLetter = (ImageView) itemView.findViewById(R.id.icon);
            txtHeader = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
