package com.fitbitsample.viewmodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Value {
    @SerializedName("customHeartRateZones")
    @Expose
    private List<Object> customHeartRateZones = null;
    @SerializedName("heartRateZones")
    @Expose
    private List<HeartRateZone> heartRateZones = null;
    @SerializedName("restingHeartRate")
    @Expose
    private Integer restingHeartRate;

    public List<Object> getCustomHeartRateZones() {
        return customHeartRateZones;
    }

    public void setCustomHeartRateZones(List<Object> customHeartRateZones) {
        this.customHeartRateZones = customHeartRateZones;
    }

    public List<HeartRateZone> getHeartRateZones() {
        return heartRateZones;
    }

    public void setHeartRateZones(List<HeartRateZone> heartRateZones) {
        this.heartRateZones = heartRateZones;
    }

    public Integer getRestingHeartRate() {
        return restingHeartRate;
    }

    public void setRestingHeartRate(Integer restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

    @Override
    public String toString() {
        return "Value{" +
                "customHeartRateZones=" + customHeartRateZones +
                ", \n heartRateZones=" + heartRateZones +
                ", \n restingHeartRate=" + restingHeartRate +
                '}';
    }
}
