package com.fitbitsample.viewmodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivitiesHeartIntraday {


    @SerializedName("dataset")
    @Expose
    private List<Dataset> dataset = null;

    @SerializedName("datasetInterval")
    @Expose
    private Integer datasetInterval;
    @SerializedName("datasetType")
    @Expose
    private String datasetType;

    public List<Dataset> getDataset() {
        return dataset;
    }

    public void setDataset(List<Dataset> dataset) {
        this.dataset = dataset;
    }

    public Integer getDatasetInterval() {
        return datasetInterval;
    }

    public void setDatasetInterval(Integer datasetInterval) {
        this.datasetInterval = datasetInterval;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }

    @Override
    public String toString() {
        return "ActivitiesHeartIntraday{" +
                "dataset=" + dataset +
                ", \n datasetInterval=" + datasetInterval +
                ", \n datasetType=" + datasetType +
                '}';
    }
}
