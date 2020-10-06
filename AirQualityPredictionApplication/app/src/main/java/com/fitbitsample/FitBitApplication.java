package com.fitbitsample;

import android.content.Context;
import androidx.multidex.MultiDexApplication;

import com.fitbitsample.preference.AppPreference;

import io.paperdb.Paper;

public class FitBitApplication extends MultiDexApplication {
    private static FitBitApplication application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        AppPreference.init(application);
        Paper.init(application);
    }

    public static synchronized FitBitApplication getInstance() {
        return application;
    }
}
