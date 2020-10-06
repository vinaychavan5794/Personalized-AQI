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

import java.util.List;
import java.util.Map;

public class GetHrModel extends BaseAndroidViewModel<Integer, HeartRate, String[], GetHrModel> {

    public GetHrModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetHrModel run(final Context context, final String... params) {
        restCall = new RestCall<>(context, true);
        restCall.execute(restServices.getHrByRange(AppPreference.getInstance().getString(PrefConstants.USER_ID), params[0], params[1]), new NetworkListener<HeartRate>() {
            @Override
            public void success(HeartRate heartRate) {
                if (heartRate != null) {
                    Log.i("Hr:", heartRate.toString());
                    PaperDB.getInstance().write(PaperConstants.HEART_RATE, heartRate);
                    data.postValue(0);
                } else {
                    data.postValue(errorCode);
                    Log.i("Error Code",Integer.toString(errorCode));
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
                Log.i("Error Code",Integer.toString(errorCode));
            }

            @Override
            public void failure() {
                data.postValue(errorCode);
            }
        });
        return this;
    }
}
