package com.fitbitsample.network;

import com.fitbitsample.viewmodel.response.Alarms;
import com.fitbitsample.viewmodel.response.Device;
import com.fitbitsample.viewmodel.response.HeartRate;
import com.fitbitsample.viewmodel.response.OAuthResponse;
import com.fitbitsample.viewmodel.response.Steps.ActivityInfo;
import com.fitbitsample.viewmodel.response.User;
import com.fitbitsample.viewmodel.response.UserInfo;
import com.fitbitsample.viewmodel.response.sleep.SleepInfo;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RestServices {

    @FormUrlEncoded
    @POST("oauth2/token?")
    Call<OAuthResponse> getAccessToken(@Field("client_id") String client_id, @Field("grant_type") String grantType,
                                       @Field("redirect_uri") String redirectUri, @Field("code") String code);

    @GET("1/user/-/devices.json")
    Call<List<Device>> getDevices();

    @GET("1/user/-/devices/tracker/{trackerId}/alarms.json")
    Call<Alarms> getAlarms(@Path("trackerId") String trackerID);

    @GET("1/user/{userId}/profile.json")
    Call<UserInfo> getUserProfile(@Path("userId") String userId);

    @GET("1/user/{userId}/activities/date/{date}.json")
    Call<ActivityInfo> getActivitiesByDate(@Path("userId") String userId, @Path("date") String date);

    @GET("1/user/{userId}/activities/heart/date/{date}/{period}.json")
    Call<HeartRate> getHrByRange(@Path("userId") String userId, @Path("date") String date, @Path("period") String period);

    @GET("1.2/user/{userId}/sleep/date/{date}.json")
    Call<SleepInfo> getSleepByDate(@Path("userId") String userId, @Path("date") String date);

    @GET("1.2/user/{userId}/sleep/date/{date}/{endDate}.json")
    Call<HeartRate> getSleepByRange(@Path("userId") String userId, @Path("date") String date, @Path("endDate") String endDate);

}
