package com.example.project_android.api;

import com.example.project_android.model.token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface apiService_token {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    apiService_token api_token = new Retrofit.Builder()
            .baseUrl("https://uiot.ixxc.dev/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(apiService_token.class);

    @POST("auth/realms/master/protocol/openid-connect/token")
    @FormUrlEncoded
    Call<token> getLoginToken(@Field("client_id") String client_id,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("grant_type") String grantType, @Header("Content-Type") String type);
}
