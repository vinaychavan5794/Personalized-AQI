package com.fitbitsample.viewmodel.response.sleep;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restless {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("minutes")
    @Expose
    private Integer minutes;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "Restless{" +
                "count=" + count +
                ", minutes=" + minutes +
                '}';
    }
}
