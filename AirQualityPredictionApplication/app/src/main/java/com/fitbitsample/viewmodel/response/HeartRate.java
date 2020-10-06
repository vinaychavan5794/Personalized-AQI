package com.fitbitsample.viewmodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeartRate {

    @SerializedName("activities-heart")
    @Expose
    private List<ActivitiesHeart> activitiesHeart = null;

    @SerializedName("activities-heart-intraday")
    @Expose
    private ActivitiesHeartIntraday activitiesHeartIntraday;

    public ActivitiesHeartIntraday getActivitiesHeartIntraday() {
        return activitiesHeartIntraday;
    }

    public void setActivitiesHeartIntraday(ActivitiesHeartIntraday activitiesHeartIntraday) {
        this.activitiesHeartIntraday = activitiesHeartIntraday;
    }

    public List<ActivitiesHeart> getActivitiesHeart() {
        return activitiesHeart;
    }

    public void setActivitiesHeart(List<ActivitiesHeart> activitiesHeart) {
        this.activitiesHeart = activitiesHeart;
    }

    @Override
    public String toString() {
        return "HeartRate{" +
                "activitiesHeart=" + activitiesHeart +
                ", activitiesHeartIntraday=" + activitiesHeartIntraday +
                '}';
    }
}
