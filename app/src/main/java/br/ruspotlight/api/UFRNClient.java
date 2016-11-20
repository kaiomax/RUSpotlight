package br.ruspotlight.api;

import android.util.Log;

import br.ruspotlight.api.objects.AccessToken;
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

    public UFRNService getResourceService() {
        if(this.token == null) {
            return null;
        }

        return ServiceGenerator.createService(UFRNService.class, this.token);
    }

    public boolean isUserLogged() {
        return this.token != null;
    }
}
