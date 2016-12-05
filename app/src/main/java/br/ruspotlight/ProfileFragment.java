package br.ruspotlight;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.ruspotlight.adapters.MealAdapter;
import br.ruspotlight.api.UFRNClient;
import br.ruspotlight.api.objects.UserCard;
import br.ruspotlight.domain.Meal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private TextView usernameText, totalMealsText, cardBalanceText;
    private LinearLayout userPanel;

    private UserCard card;

    private static final String TAG = "RUSpotlight/Profile";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);

        userPanel = (LinearLayout) v.findViewById(R.id.user_panel);

        usernameText = (TextView) v.findViewById(R.id.username);
        totalMealsText = (TextView) v.findViewById(R.id.total_meals);
        cardBalanceText = (TextView) v.findViewById(R.id.card_balance);

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            showUserCard();
        }
    }

    private void showUserCard() {
        if(UFRNClient.getInstance().isUserLogged()) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            Call<UserCard> call = UFRNClient.getInstance().getResourceService().getUserCard();

            call.enqueue(new Callback<UserCard>() {
                @Override
                public void onResponse(Call<UserCard> call, Response<UserCard> response) {
                    int statusCode = response.code();
                    Log.d(TAG, "CODE: " + statusCode);

                    if (statusCode == 200) {
                        card = response.body();
                        progressBar.setVisibility(ProgressBar.GONE);
                        setUserCardContent(card);
                    } else {
                        // TODO Handle errors on a failed response
                    }
                }

                @Override
                public void onFailure(Call<UserCard> call, Throwable t) {
                    Log.d(TAG, "FAILURE" + t.getMessage());
                    // TODO Handle failure
                }
            });
        } else {
            // TODO Show info user not logged
        }
    }

    private void setUserCardContent(UserCard card) {
        //if(card.getUsername() == null) {
        usernameText.setText(card.getCode());
        //}
        totalMealsText.setText(card.getTotalMeals() + " refeições");
        cardBalanceText.setText("Saldo do cartão: Almoço - " + card.getLunchBalance() + " | Jantar - " + card.getDinnerBalance());
        userPanel.setVisibility(LinearLayout.VISIBLE);
    }
}
