package br.ruspotlight.api;

import br.ruspotlight.api.objects.AccessToken;
import br.ruspotlight.api.objects.UserCard;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UFRNService {
    @FormUrlEncoded
    @POST("/authz-server/oauth/token")
    Call<AccessToken> getAccessToken(
            @Field("code") String code,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("grant_type") String grantType);

    @GET("/restaurante-services/services/consulta/cartao/usuario")
    Call<UserCard> getUserCard();
}
