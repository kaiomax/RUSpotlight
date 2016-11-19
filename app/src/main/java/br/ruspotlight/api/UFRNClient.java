package br.ruspotlight.api;

import android.util.Log;

import br.ruspotlight.api.objects.AccessToken;
import br.ruspotlight.api.objects.UserCard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UFRNClient {

    private static final String TAG = "RUSpotlight/UFRNClient";

    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_SECRET = "CLIENT_SECRET";
    public static final String RESPONSE_TYPE = "code";
    public static final String REDIRECT_URI = "br.ruspotlight://oauth";

    private static UFRNClient client;

    private AccessToken token;
    private UserCard card;

    public static UFRNClient getInstance(){
        if(client == null) {
            client = new UFRNClient();
        }

        return client;
    }

    private UFRNClient() {
    }

    public void getToken(String code) {
        UFRNService client = ServiceGenerator.createService(UFRNService.class);
        Call<AccessToken> call = client.getAccessToken(code, CLIENT_ID,
                CLIENT_SECRET, REDIRECT_URI, "authorization_code");

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                int statusCode = response.code();

                if (statusCode == 200) {
                    Log.d(TAG, "TOKEN: " + response.body().getAccessToken());
                    token = response.body();
                } else {
                    // TODO Handle errors on a failed response
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                // TODO Handle failure
            }
        });
    }

    public UserCard getUserCard() {
        if(this.token == null) {
            return null;
        }

        UFRNService serviceClient = ServiceGenerator.createService(UFRNService.class, this.token);
        Call<UserCard> call = serviceClient.getUserCard();

        call.enqueue(new Callback<UserCard>() {
            @Override
            public void onResponse(Call<UserCard> call, Response<UserCard> response) {
                int statusCode = response.code();
                Log.d(TAG, "CODE: " + statusCode);

                if (statusCode == 200) {
                    card = response.body();
                    Log.d(TAG, "janta: " + card.getDinnerBalance());
                    Log.d(TAG, "almoco: " + card.getLunchBalance());
                    Log.d(TAG, "nomeUsuario: " + card.getUsername());
                    Log.d(TAG, "codigoCartao: " + card.getCode());
                    Log.d(TAG, "tipoVinculo: " + card.getLinkType());
                    Log.d(TAG, "totalRefeicoes: " + card.getTotalMeals());
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

        return card;
    }
}
