package br.ruspotlight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.ruspotlight.adapters.MealAdapter;
import br.ruspotlight.api.UFRNClient;
import br.ruspotlight.api.objects.UserCard;
import br.ruspotlight.domain.Meal;


public class ProfileFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "RUSpotlight/Profile";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserCard card =  UFRNClient.getInstance().getUserCard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Meal> list = new ArrayList<Meal>();
        list.add(new Meal("Almoço", "01/10/2016"));

        MealAdapter adapter = new MealAdapter(list, getActivity());
        mRecyclerView.setAdapter(adapter);

        return  v;
    }


}
