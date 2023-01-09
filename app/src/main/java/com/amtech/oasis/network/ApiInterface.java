package com.amtech.oasis.network;

import com.amtech.oasis.model.AssignStorArr;
import com.amtech.oasis.model.DashboardObject;
import com.amtech.oasis.model.Login;
import com.amtech.oasis.model.Password;
import com.amtech.oasis.model.ProfileObject;
import com.amtech.oasis.model.StoreDataObj;
import com.amtech.oasis.model.Stores;
import com.amtech.oasis.model.Tasks;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("login")
    Call<Login> loginUser(@Body Login login);

    @GET("user-info")
    Call<ProfileObject> getProfile(@HeaderMap Map<String,String> headers);

    @GET("merchandiser/dashboard")
    Call<DashboardObject> getDashboard(@HeaderMap Map<String,String> headers);

    @GET("merchandiser/assignee-store")
    Call<StoreDataObj> getAssignStores(@HeaderMap Map<String,String> headers);

    @GET("merchandiser/task")
    Call<StoreDataObj> getAllTasks(@HeaderMap Map<String,String> headers);

    @GET("merchandiser/view-task/{id}")
    Call<StoreDataObj> getPendingTask(@HeaderMap Map<String,String> headers, @Path("id")String taskId);

    @Multipart
    @POST("merchandiser/task-update")
    Call<Tasks> taskUpdateImage(@HeaderMap Map<String,String> headers,
                                @Part("id") RequestBody checkId,
                                @Part("task_id") RequestBody taskId,
                                @Part MultipartBody.Part image);

    @Multipart
    @POST("merchandiser/task-update")
    Call<Tasks> taskUpdate(@HeaderMap Map<String,String> headers,
                                       @Part("id") RequestBody checkId,
                                       @Part("task_id") RequestBody taskId,
                                       @Part("image") RequestBody checkData);

    @POST("merchandiser/changePassword")
    Call<Password> passwordChange(@HeaderMap Map<String,String> headers,@Body Password password);

    @GET("merchandiser/view-store/{id}")
    Call<StoreDataObj> getStoreInfo(@HeaderMap Map<String,String> headers, @Path("id")String storeId);

    @POST("merchandiser/update-profile")
    Call<StoreDataObj> profileUpdate(@HeaderMap Map<String,String> headers, @Path("id")String storeId);
}
