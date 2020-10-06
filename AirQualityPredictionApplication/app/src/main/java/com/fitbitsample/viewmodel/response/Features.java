package com.fitbitsample.viewmodel.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Features {

    @SerializedName("exerciseGoal")
    @Expose
    private Boolean exerciseGoal;

    public Boolean getExerciseGoal() {
        return exerciseGoal;
    }

    public void setExerciseGoal(Boolean exerciseGoal) {
        this.exerciseGoal = exerciseGoal;
    }


    @Override
    public String toString() {
        return "Features{" +
                "exerciseGoal=" + exerciseGoal +
                '}';
    }
}