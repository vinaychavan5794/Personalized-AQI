package com.fitbitsample.viewmodel.response.Steps;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("activeScore")
    @Expose
    private Integer activeScore;
    @SerializedName("activityCalories")
    @Expose
    private Integer activityCalories;
    @SerializedName("caloriesBMR")
    @Expose
    private Integer caloriesBMR;
    @SerializedName("caloriesOut")
    @Expose
    private Integer caloriesOut;
    @SerializedName("distances")
    @Expose
    private List<Distance> distances = null;
    @SerializedName("fairlyActiveMinutes")
    @Expose
    private Integer fairlyActiveMinutes;
    @SerializedName("lightlyActiveMinutes")
    @Expose
    private Integer lightlyActiveMinutes;
    @SerializedName("marginalCalories")
    @Expose
    private Integer marginalCalories;
    @SerializedName("sedentaryMinutes")
    @Expose
    private Integer sedentaryMinutes;
    @SerializedName("steps")
    @Expose
    private Integer steps;
    @SerializedName("veryActiveMinutes")
    @Expose
    private Integer veryActiveMinutes;

    public Integer getActiveScore() {
        return activeScore;
    }

    public void setActiveScore(Integer activeScore) {
        this.activeScore = activeScore;
    }

    public Integer getActivityCalories() {
        return activityCalories;
    }

    public void setActivityCalories(Integer activityCalories) {
        this.activityCalories = activityCalories;
    }

    public Integer getCaloriesBMR() {
        return caloriesBMR;
    }

    public void setCaloriesBMR(Integer caloriesBMR) {
        this.caloriesBMR = caloriesBMR;
    }

    public Integer getCaloriesOut() {
        return caloriesOut;
    }

    public void setCaloriesOut(Integer caloriesOut) {
        this.caloriesOut = caloriesOut;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }

    public Integer getFairlyActiveMinutes() {
        return fairlyActiveMinutes;
    }

    public void setFairlyActiveMinutes(Integer fairlyActiveMinutes) {
        this.fairlyActiveMinutes = fairlyActiveMinutes;
    }

    public Integer getLightlyActiveMinutes() {
        return lightlyActiveMinutes;
    }

    public void setLightlyActiveMinutes(Integer lightlyActiveMinutes) {
        this.lightlyActiveMinutes = lightlyActiveMinutes;
    }

    public Integer getMarginalCalories() {
        return marginalCalories;
    }

    public void setMarginalCalories(Integer marginalCalories) {
        this.marginalCalories = marginalCalories;
    }

    public Integer getSedentaryMinutes() {
        return sedentaryMinutes;
    }

    public void setSedentaryMinutes(Integer sedentaryMinutes) {
        this.sedentaryMinutes = sedentaryMinutes;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Integer getVeryActiveMinutes() {
        return veryActiveMinutes;
    }

    public void setVeryActiveMinutes(Integer veryActiveMinutes) {
        this.veryActiveMinutes = veryActiveMinutes;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "\nactiveScore=" + activeScore +
                ", \nactivityCalories=" + activityCalories +
                ", \ncaloriesBMR=" + caloriesBMR +
                ", \ncaloriesOut=" + caloriesOut +
                ", \ndistances=" + distances +
                ", \nfairlyActiveMinutes=" + fairlyActiveMinutes +
                ", \nlightlyActiveMinutes=" + lightlyActiveMinutes +
                ", \nmarginalCalories=" + marginalCalories +
                ", \nsedentaryMinutes=" + sedentaryMinutes +
                ", \nsteps=" + steps +
                ", \nveryActiveMinutes=" + veryActiveMinutes +
                '}';
    }
}
