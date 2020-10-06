package com.fitbitsample.preference;

/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

public class AppPreference {
    private static AppPreference appPreference;
    private SharedPreferences mSecurePrefs;

    private AppPreference(Context context) {
        this.mSecurePrefs = new SecurePreferences(context, "pref", "app_preference.xml");
    }

    public static void init(Context context) {
        if (appPreference == null) {
            appPreference = new AppPreference(context);
        }
    }

    public static synchronized AppPreference getInstance() {
        if (appPreference == null) {
            throw new RuntimeException("Initialize appPreference before using it");
        }
        return appPreference;
    }

    public int getInt(String key, int value) {
        return mSecurePrefs.getInt(key, value);
    }

    public int getInt(String key) {
        return mSecurePrefs.getInt(key, -1);
    }

    public void putInt(String key, int value) {
        mSecurePrefs.edit().putInt(key, value).apply();
    }

    public long getLong(String key) {
        return mSecurePrefs.getLong(key, -1);
    }

    public void putLong(String key, long value) {
        mSecurePrefs.edit().putLong(key, value).apply();
    }

    public String getString(String key) {
        return mSecurePrefs.getString(key, null);
    }

    public String getString(String key, String value) {
        return mSecurePrefs.getString(key, value);
    }

    public void putString(String key, String value) {
        mSecurePrefs.edit().putString(key, value).apply();
    }

    public boolean getBoolean(String key) {
        return mSecurePrefs.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean value) {
        return mSecurePrefs.getBoolean(key, value);
    }

    public void putBoolean(String key, boolean value) {
        mSecurePrefs.edit().putBoolean(key, value).apply();
    }

    public void clear() {
        mSecurePrefs.edit().clear().apply();
    }

    public void remove(String key) {
        mSecurePrefs.edit().remove(key).apply();
    }
}