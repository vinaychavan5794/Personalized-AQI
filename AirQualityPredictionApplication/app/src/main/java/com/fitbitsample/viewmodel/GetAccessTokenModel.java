package com.fitbitsample.viewmodel;

import android.content.Context;
import android.util.Log;

import com.fitbitsample.constant.AppConstants;
import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.db.paper.PaperConstants;
import com.fitbitsample.db.paper.PaperDB;
import com.fitbitsample.network.NetworkError;
import com.fitbitsample.network.NetworkListener;
import com.fitbitsample.network.RestCall;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.Trace;
import com.fitbitsample.viewmodel.response.OAuthResponse;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import static com.fitbitsample.constant.AppConstants.REDIRECT_URI;
import static com.fitbitsample.constant.PrefConstants.CODE;
import static com.fitbitsample.constant.PrefConstants.GRANT_TYPE;

public class GetAccessTokenModel extends BaseAndroidViewModel<Integer, OAuthResponse, Void, GetAccessTokenModel> {

    public GetAccessTokenModel(int errorCode) {
        super(false, errorCode);
    }

    @Override
    public GetAccessTokenModel run(final Context context, final Void aVoid) {
        String code = AppPreference.getInstance().getString(CODE);
        restCall = new RestCall<>(context, true);
        restCall.execute(restServices.getAccessToken(AppConstants.CLIENT_ID, GRANT_TYPE, REDIRECT_URI, code), 3, new NetworkListener<OAuthResponse>() {
            @Override
            public void success(OAuthResponse response) {
                if (response != null) {
                    PaperDB.getInstance().write(PaperConstants.OAUTH_RESPONSE, response);
                    AppPreference.getInstance().putBoolean(PrefConstants.HAVE_AUTHORIZATION, true);
                    AppPreference.getInstance().putString(PrefConstants.TOKEN_TYPE, response.getTokenType());
                    AppPreference.getInstance().putString(PrefConstants.REFRESH_TOKEN, response.getTokenType());
                    AppPreference.getInstance().putString(PrefConstants.FULL_AUTHORIZATION, response.getTokenType() + " " + response.getAccessToken());
                    AppPreference.getInstance().putString(PrefConstants.USER_ID, response.getUserId());
                    Log.i("Response Access token:",":" + response.getAccessToken());

                    data.postValue(0);
                } else {
                    data.postValue(errorCode);
                }
            }

            @Override
            public void headers(Map<String, String> header) {

            }

            @Override
            public void fail(int code, List<NetworkError> networkErrors) {
                if (code == 404) {
                    data.postValue(0);
                } else {
                    data.postValue(errorCode);
                }
            }

            @Override
            public void failure() {
                data.postValue(errorCode);
            }
        });
        return this;
    }

}
