package com.fitbitsample.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.R;
import com.fitbitsample.dialog.LoadingDialog;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.StringUtil;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */

class NetworkHandler<T> {
    private Context context;
    private Call<T> call;
    private int retryCount;
    private boolean askUserForRetry;
    private NetworkListener<T> networkListener;
    private int currentRetryCount = 0;
    private int renewRetryCount = 0;
    private boolean progressBar = true;
    private LoadingDialog loadingDialog;
    private Integer[] acceptCodes = {200, 201, 204};

    private NetworkHandler() {
    }

    NetworkHandler(Context context, Call<T> call) {
        this.context = context;
        this.call = call;
        if (context instanceof Activity) {
            loadingDialog = new LoadingDialog(context, false, context.getString(R.string.processing));
        }
    }

    NetworkHandler setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    NetworkHandler askUserForRetry(boolean askUserForRetry) {
        this.askUserForRetry = askUserForRetry;
        return this;
    }

    NetworkHandler setProgressBar(boolean progressBar) {
        this.progressBar = progressBar;
        return this;
    }

    void execute(NetworkListener<T> networkListener) {
        currentRetryCount = 0;
        this.networkListener = networkListener;
        if (call != null) {
            enqueue();
        }
    }

    private void enqueue() {
        if (!isAlive()) {
            return;
        }
        if (progressBar && loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                for (String name : response.headers().names()) {
//                    if (StringUtil.fixString(name).equals(AppPrefConstants.ACCESS_TOKEN)) {
//                        AppPreference.getInstance().putString(AppPrefConstants.SESSION_ID, response.headers().get(AppPrefConstants.ACCESS_TOKEN));
//                    }
                }
                if (isAlive() && networkListener != null) {
                    if (Arrays.asList(acceptCodes).contains(response.code())) {
                        Map<String, String> headers = new HashMap<>();
                        if (response.headers() != null) {
                            for (String name : response.headers().names()) {
                                headers.put(name, response.headers().get(name));
                            }
                        }
                        networkListener.success(response.body());
                        networkListener.headers(headers);
                    } else if (response.code() == 401) {
                        renewRetryCount = 0;
                        //refreshAccessToken();
                    } else {
                        networkListener.fail(response.code(), parseError(response));
                    }

                    logResponse(response);
                }
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (askUserForRetry) {
                    if (context instanceof Activity) {
                        try {
                            if (isAlive() && networkListener != null) {
                                networkListener.failure();
                            }
                            if (loadingDialog != null && loadingDialog.isShowing()) {
                                loadingDialog.dismiss();
                            }
                            /*new AskForRetryDialog(context, new AskForRetryListener() {
                                @Override
                                public void retry() {
                                    restart();
                                }

                                @Override
                                public void cancel() {
                                    if (isAlive() && networkListener != null) {
                                        networkListener.failure();
                                    }
                                    if (loadingDialog != null && loadingDialog.isShowing()) {
                                        loadingDialog.dismiss();
                                    }
                                }
                            }).show();*/
                        } catch (Exception ignored) {
                        }
                    } else {
                        if (isAlive() && networkListener != null) {
                            networkListener.failure();
                        }
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                } else {
                    if (retryCount > currentRetryCount) {
                        currentRetryCount++;
                        restart();
                    } else {
                        if (isAlive() && networkListener != null) {
                            networkListener.failure();
                        }
                        if (loadingDialog != null && loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                }
            }
        });
    }

    private boolean isAlive() {
        return !(context instanceof Activity) || !(((Activity) context).isFinishing() || ((Activity) context).isDestroyed());
    }

    private void logResponse(final Response<T> response) {
        try {
            if (AppPreference.getInstance().getBoolean(PrefConstants.START_LOG)) {
                String url = response.raw().request().url().toString();
                String method = response.raw().request().method();
                String requestBody = null;
                String responseBody = null;
                if (response.raw().request().body() != null) {
                    final RequestBody copy = response.raw().request().body();
                    final Buffer buffer = new Buffer();
                    if (copy != null) {
                        copy.writeTo(buffer);
                        requestBody = buffer.readUtf8();
                    }
                }
                if (Arrays.asList(acceptCodes).contains(response.code())) {
                    if (response.body() != null) {
                        responseBody = response.body().toString();
                    }
                } else {
                    responseBody = "Failed";
                }
                StringBuilder builder = new StringBuilder();
                builder.append(String.format("Url: %s \n", url));
                builder.append(String.format("method: %s \n", method));
                if (!StringUtil.isEmpty(requestBody)) {
                    builder.append(String.format("Request Body --> %s \n", requestBody));
                }
                builder.append(String.format("Response code <-- %s \n", response.code()));
                if (!StringUtil.isEmpty(responseBody)) {
                    builder.append(String.format("Response <-- %s \n", responseBody));
                }
                //DataHandler.getInstance().addLog(builder.toString());
                Log.i("HRResponse",builder.toString());
            }
        } catch (final IOException e) {
        }

    }

//    private void refreshAccessToken() {
//        String mobile = AppPreference.getInstance().getString(AppPrefConstants.USER_PHONE, "");
//        String pin = AppPreference.getInstance().getString(AppPrefConstants.USER_PASS, "");
//        SignInRequest signInRequest = new SignInRequest(mobile, pin, AppUtil.getAppId());
//        Call<SignInResponse> renew = RetrofitService.getRestService(false)
//                .create(RestServices.class).renew(signInRequest);
//        renew.enqueue(new Callback<SignInResponse>() {
//            @Override
//            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
//                for (String name : response.headers().names()) {
//                    if (StringUtil.fixString(name).equals(AppPrefConstants.ACCESS_TOKEN)) {
//                        AppPreference.getInstance().putString(AppPrefConstants.SESSION_ID, response.headers().get(AppPrefConstants.ACCESS_TOKEN));
//                    }
//                }
//                if (response.code() == 200) {
//                    restart();
//                } else if (response.code() == 412) {
//                    AppUtil.signOut(context);
//                } else {
//                    networkListener.fail(response.code(), parseError(response));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SignInResponse> call, Throwable t) {
//                if (3 > renewRetryCount) {
//                    renewRetryCount++;
//                    refreshAccessToken();
//                } else {
//                    restart();
//                }
//            }
//        });
//    }

    private void restart() {
        if (call != null) {
            call = call.clone();
            enqueue();
        }
    }

    private List<NetworkError> parseError(Response<?> response) {
        Type type = new TypeToken<List<NetworkError>>() {
        }.getType();
        Converter<ResponseBody, List<NetworkError>> converter =
                RetrofitService.getRestService(false)
                        .responseBodyConverter(type, new Annotation[0]);
        List<NetworkError> networkError;
        try {
            networkError = converter.convert(response.errorBody());
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return networkError;
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}