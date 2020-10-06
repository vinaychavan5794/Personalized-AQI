package com.fitbitsample.viewmodel;

import android.content.Context;
import android.util.Log;

import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.db.paper.PaperConstants;
import com.fitbitsample.db.paper.PaperDB;
import com.fitbitsample.network.NetworkError;
import com.fitbitsample.network.NetworkListener;
import com.fitbitsample.network.RestCall;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.Trace;
import com.fitbitsample.viewmodel.response.HeartRate;
import com.fitbitsample.viewmodel.response.OAuthResponse;
import com.fitbitsample.viewmodel.response.UserInfo;
import com.fitbitsample.viewmodel.response.sleep.SleepInfo;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class GetSleepModel extends BaseAndroidViewModel<Integer, SleepInfo, String, GetSleepModel> {

    public GetSleepModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetSleepModel run(final Context context, final String date) {
        restCall = new RestCall<>(context, true);
        restCall.execute(restServices.getSleepByDate(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<SleepInfo>() {
            @Override
            public void success(SleepInfo sleep) {
                if (sleep != null) {
                    Log.i("Sleep:", sleep.toString());
                    PaperDB.getInstance().write(PaperConstants.SLEEP, sleep);
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
