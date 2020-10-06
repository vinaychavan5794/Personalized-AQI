package com.fitbitsample.viewmodel;

import android.content.Context;

import com.fitbitsample.db.paper.PaperConstants;
import com.fitbitsample.db.paper.PaperDB;
import com.fitbitsample.network.NetworkError;
import com.fitbitsample.network.NetworkListener;
import com.fitbitsample.network.RestCall;
import com.fitbitsample.util.Trace;
import com.fitbitsample.viewmodel.response.Device;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetDeviceModel extends BaseAndroidViewModel<Integer, List<Device>, Void, GetDeviceModel> {

    public GetDeviceModel(int errorCode) {
        super(true, errorCode);
    }

    @Override
    public GetDeviceModel run(final Context context, final Void aVoid) {
        restCall = new RestCall<>(context, true);
        restCall.execute(restServices.getDevices(), 3, new NetworkListener<List<Device>>() {
            @Override
            public void success(List<Device> devices) {
                if (devices != null) {
                    PaperDB.getInstance().write(PaperConstants.DEVICES, devices);
                    for (Device device : devices) {
                        Trace.i("Device:" +device.toString());
                    }
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
