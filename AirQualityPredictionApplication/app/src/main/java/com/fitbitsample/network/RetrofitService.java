package com.fitbitsample.network;

import android.content.SharedPreferences;
import android.util.Log;


import com.fitbitsample.constant.AppConstants;
import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.BuildConfig;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.CryptoUtil;
import com.fitbitsample.util.StringUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fitbitsample.constant.PrefConstants.AUTHORIZATION;
import static com.fitbitsample.constant.PrefConstants.CONTENT_TYPE;


/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */

public class RetrofitService {
    public static final String BASE_URL = "https://api.fitbit.com/";
    private static final long CONNECT_TIMEOUT = 5000;
    private static final long WRITE_TIMEOUT = 5000;
    private static final long READ_TIMEOUT = 5000;
    private static SharedPreferences sharedPreferences;
    public static Retrofit getRestService(final boolean session, final String... accept) {

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        b.readTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
        b.writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        OkHttpClient okHttpClient = b.addInterceptor(logging).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                String fullAuthToken = AppPreference.getInstance().getString(PrefConstants.FULL_AUTHORIZATION, null);
                if (BuildConfig.DEBUG) {
                    if (session && StringUtil.isEmpty(fullAuthToken)) {
                        Log.i("OkHttp", "-----Session not found-----");
                    } else if (session) {
                        Log.i("OkHttp", fullAuthToken);
                    }
                }
                Request.Builder builder = original.newBuilder()
                        .header("Accept", accept.length == 0 ? "application/json" : accept[0])
                        .header("User-Agent", "android")
                        .header("version-name", String.valueOf(BuildConfig.VERSION_NAME))
                        .header("version-code", String.valueOf(BuildConfig.VERSION_CODE));
                if (fullAuthToken != null && session) {
                    builder.header(AUTHORIZATION, fullAuthToken);
                }else{
                    builder.header(CONTENT_TYPE, "application/x-www-form-urlencoded");
                    builder.header(AUTHORIZATION, "Basic "+ CryptoUtil.getEncodedValue(AppConstants.CLIENT_ID+":"+AppConstants.CLIENT_SECRET));
                }
                builder.method(original.method(), original.body());
                Request request = builder.build();
                return chain.proceed(request);
            }
        }).build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                .client(okHttpClient)
                .build();
    }
}