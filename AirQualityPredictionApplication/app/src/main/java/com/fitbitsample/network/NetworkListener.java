package com.fitbitsample.network;

import java.util.List;
import java.util.Map;

/**
 * Created by Vinay on 25-08-2018 for HugFit.
 * All rights reserved.
 */

public interface NetworkListener<T> {

    void success(T t);

    void headers(Map<String, String> header);

    void fail(int code, List<NetworkError> networkErrors);

    void failure();
}
