package com.fitbitsample.viewmodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeartRateZone {

    @SerializedName("caloriesOut")
    @Expose
    private Integer caloriesOut;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("minutes")
    @Expose
    private Integer minutes;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getCaloriesOut() {
        return caloriesOut;
    }

    public void setCaloriesOut(Integer caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeartRateZone{" +
                "\ncaloriesOut=" + caloriesOut +
                ", \nmax=" + max +
                ", \nmin=" + min +
                ", \nminutes=" + minutes +
                ", \nname='" + name + '\'' +
                '}';
    }
}
