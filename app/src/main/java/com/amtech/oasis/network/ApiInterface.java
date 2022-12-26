package com.amtech.oasis.network;

import com.amtech.oasis.model.DashboardObject;
import com.amtech.oasis.model.Login;
import com.amtech.oasis.model.ProfileObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("login")
    Call<Login> loginUser(@Body Login login);

    @GET("user-info")
    Call<ProfileObject> getProfile(@HeaderMap Map<String,String> headers);

    @GET("merchandiser/dashboard")
    Call<DashboardObject> getDashboard(@HeaderMap Map<String,String> headers);
}
