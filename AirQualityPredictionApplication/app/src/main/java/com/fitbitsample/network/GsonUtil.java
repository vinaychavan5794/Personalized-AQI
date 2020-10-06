package com.fitbitsample.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */

public class GsonUtil {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();

    public static Gson getGson() {
        return gson;
    }
}
