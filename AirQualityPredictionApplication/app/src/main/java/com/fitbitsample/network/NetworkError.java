package com.fitbitsample.network;

/**
 * Created by Vinay on 24-08-2018 for HugFit.
 * All rights reserved.
 */
public class NetworkError {

    private String description;
    private String recommendation;

    public NetworkError(String description, String recommendation) {
        this.description = description;
        this.recommendation = recommendation;
    }

    public String getDescription() {
        return description;
    }

    public String getRecommendation() {
        return recommendation;
    }
}
