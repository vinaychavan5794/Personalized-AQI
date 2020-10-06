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
import com.fitbitsample.viewmodel.response.OAuthResponse;
import com.fitbitsample.viewmodel.response.Steps.ActivityInfo;
import com.fitbitsample.viewmodel.response.sleep.SleepInfo;

import java.util.List;
import java.util.Map;

public class GetActivityModel extends BaseAndroidViewModel<Integer, ActivityInfo, String, GetActivityModel> {

    public GetActivityModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetActivityModel run(final Context context, final String date) {
        restCall = new RestCall<>(context, true);
        restCall.execute(restServices.getActivitiesByDate(AppPreference.getInstance().getString(PrefConstants.USER_ID), date), new NetworkListener<ActivityInfo>() {
            @Override
            public void success(ActivityInfo activityInfo) {
                if (activityInfo != null) {
                    Log.i("Activity_info:", activityInfo.toString());
                    PaperDB.getInstance().write(PaperConstants.ACTIVITY_INFO, activityInfo);
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

