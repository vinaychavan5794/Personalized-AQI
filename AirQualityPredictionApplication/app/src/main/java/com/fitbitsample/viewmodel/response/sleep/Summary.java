package com.fitbitsample.viewmodel.response.sleep;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Summary {

    @SerializedName("asleep")
    @Expose
    private Asleep asleep;
    @SerializedName("awake")
    @Expose
    private Awake awake;
    @SerializedName("restless")
    @Expose
    private Restless restless;

    public Asleep getAsleep() {
        return asleep;
    }

    public void setAsleep(Asleep asleep) {
        this.asleep = asleep;
    }

    public Awake getAwake() {
        return awake;
    }

    public void setAwake(Awake awake) {
        this.awake = awake;
    }

    public Restless getRestless() {
        return restless;
    }

    public void setRestless(Restless restless) {
        this.restless = restless;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "asleep=" + asleep +
                ", awake=" + awake +
                ", restless=" + restless +
                '}';
    }
}
