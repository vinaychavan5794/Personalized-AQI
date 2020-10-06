package com.fitbitsample.viewmodel.response.sleep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sleep {

    @SerializedName("dateOfSleep")
    @Expose
    private String dateOfSleep;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("efficiency")
    @Expose
    private Integer efficiency;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("infoCode")
    @Expose
    private Integer infoCode;

    @SerializedName("isMainSleep")
    @Expose
    private boolean isMainSleep;

    @SerializedName("levels")
    @Expose
    private Levels levels;
    @SerializedName("logId")
    @Expose
    private Long logId;
    @SerializedName("minutesAfterWakeup")
    @Expose
    private Integer minutesAfterWakeup;
    @SerializedName("minutesAsleep")
    @Expose
    private Integer minutesAsleep;
    @SerializedName("minutesAwake")
    @Expose
    private Integer minutesAwake;
    @SerializedName("minutesToFallAsleep")
    @Expose
    private Integer minutesToFallAsleep;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("timeInBed")
    @Expose
    private Integer timeInBed;
    @SerializedName("type")
    @Expose
    private String type;

    public String getDateOfSleep() {
        return dateOfSleep;
    }

    public void setDateOfSleep(String dateOfSleep) {
        this.dateOfSleep = dateOfSleep;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(Integer infoCode) {
        this.infoCode = infoCode;
    }

    public boolean isMainSleep() {
        return isMainSleep;
    }

    public void setMainSleep(boolean mainSleep) {
        isMainSleep = mainSleep;
    }

    public Levels getLevels() {
        return levels;
    }

    public void setLevels(Levels levels) {
        this.levels = levels;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getMinutesAfterWakeup() {
        return minutesAfterWakeup;
    }

    public void setMinutesAfterWakeup(Integer minutesAfterWakeup) {
        this.minutesAfterWakeup = minutesAfterWakeup;
    }

    public Integer getMinutesAsleep() {
        return minutesAsleep;
    }

    public void setMinutesAsleep(Integer minutesAsleep) {
        this.minutesAsleep = minutesAsleep;
    }

    public Integer getMinutesAwake() {
        return minutesAwake;
    }

    public void setMinutesAwake(Integer minutesAwake) {
        this.minutesAwake = minutesAwake;
    }

    public Integer getMinutesToFallAsleep() {
        return minutesToFallAsleep;
    }

    public void setMinutesToFallAsleep(Integer minutesToFallAsleep) {
        this.minutesToFallAsleep = minutesToFallAsleep;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getTimeInBed() {
        return timeInBed;
    }

    public void setTimeInBed(Integer timeInBed) {
        this.timeInBed = timeInBed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Sleep{" +
                "\ndateOfSleep='" + dateOfSleep + '\'' +
                ",\n duration=" + duration +
                ",\nefficiency=" + efficiency +
                ",\n endTime='" + endTime + '\'' +
                ", \ninfoCode=" + infoCode +
                ", \nlevels=" + levels +
                ", \nlogId=" + logId +
                ", \nminutesAfterWakeup=" + minutesAfterWakeup +
                ", \nminutesAsleep=" + minutesAsleep +
                ", \nminutesAwake=" + minutesAwake +
                ", \nminutesToFallAsleep=" + minutesToFallAsleep +
                ", \nstartTime='" + startTime + '\'' +
                ", \ntimeInBed=" + timeInBed +
                ", \ntype='" + type + '\'' +
                '}';
    }
}
