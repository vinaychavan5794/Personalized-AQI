package com.fitbitsample.receiver;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.util.Log;

import com.fitbitsample.constant.PrefConstants;
import com.fitbitsample.activity.MainActivity;
import com.fitbitsample.preference.AppPreference;
import com.fitbitsample.util.Trace;
import com.fitbitsample.viewmodel.GetAccessTokenModel;

import java.util.Date;


public class AccessTokenReceiver extends AppCompatActivity {

    String data;
    @Override
    protected void onNewIntent(Intent intent) {
        data = intent.getDataString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
        if (data.contains("code=")) {
            String code = data.substring(data.indexOf("code=") + 5).replace("#_=_", "");
            Log.i("TAG code: ", code);
            AppPreference.getInstance().putString(PrefConstants.CODE, code);
            AppPreference.getInstance().putBoolean(PrefConstants.IS_CODE_RECEIVED, true);
        }
        verifyAuthCode();

    }

    private void verifyAuthCode() {
        Log.i("AccessToken","inside Verify");
        if (AppPreference.getInstance().getBoolean(PrefConstants.IS_CODE_RECEIVED) ) {
            Log.i("AccessToken","inside if");
            GetAccessTokenModel accessTokenModel = new GetAccessTokenModel(2);
            accessTokenModel.run(AccessTokenReceiver.this, null).getData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    if (integer != null && integer > 0) {
                        Trace.i("AccessToken fetching failed");
                    } else {
                        Trace.i("Access Token fetching is done");
                        AppPreference.getInstance().putLong(PrefConstants.TIME_STAMP,new Date().getTime());
                        Intent intent = new Intent(AccessTokenReceiver.this, MainActivity.class);
                        startActivity(intent);

                        //showDashboard();
                    }
                }
            });
        }
    }
}
