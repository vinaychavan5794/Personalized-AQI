package com.fitbitsample.viewmodel.response.sleep;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SleepInfo {

    @SerializedName("sleep")
    @Expose
    private List<Sleep> sleep = null;

    @SerializedName("summary")
    @Expose
    private TotalSummary summary;

    public List<Sleep> getSleep() {
        return sleep;
    }

    public void setSleep(List<Sleep> sleep) {
        this.sleep = sleep;
    }


    public TotalSummary getSummary() {
        return summary;
    }

    public void setSummary(TotalSummary summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "SleepInfo{" +
                "sleep=" + sleep +
                ", summary=" + summary +
                '}';
    }
}
