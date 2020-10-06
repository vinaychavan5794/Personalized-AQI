package com.fitbitsample.viewmodel.response.Steps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Activity {
    @SerializedName("activityId")
    @Expose
    private Integer activityId;
    @SerializedName("activityParentId")
    @Expose
    private Integer activityParentId;
    @SerializedName("calories")
    @Expose
    private Integer calories;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("hasStartTime")
    @Expose
    private Boolean hasStartTime;
    @SerializedName("isFavorite")
    @Expose
    private Boolean isFavorite;
    @SerializedName("logId")
    @Expose
    private Integer logId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("steps")
    @Expose
    private Integer steps;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityParentId() {
        return activityParentId;
    }

    public void setActivityParentId(Integer activityParentId) {
        this.activityParentId = activityParentId;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getHasStartTime() {
        return hasStartTime;
    }

    public void setHasStartTime(Boolean hasStartTime) {
        this.hasStartTime = hasStartTime;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityParentId=" + activityParentId +
                ", calories=" + calories +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", duration=" + duration +
                ", hasStartTime=" + hasStartTime +
                ", isFavorite=" + isFavorite +
                ", logId=" + logId +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", steps=" + steps +
                '}';
    }
}
