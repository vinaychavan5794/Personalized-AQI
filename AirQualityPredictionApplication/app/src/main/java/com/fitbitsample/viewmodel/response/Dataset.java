package com.fitbitsample.viewmodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dataset {

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("value")
    @Expose
    private Integer value;


    @Override
    public String toString() {
        return "Dataset{" +
                "\ntime=" + time +
                ", \nvalue='" + value + '\'' +
                '}';
    }

}
