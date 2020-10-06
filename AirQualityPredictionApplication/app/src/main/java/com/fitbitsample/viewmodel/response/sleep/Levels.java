package com.fitbitsample.viewmodel.response.sleep;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Levels {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("summary")
    @Expose
    private Summary summary;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "Levels{" +
                "data=" + data +
                ", summary=" + summary +
                '}';
    }
}
