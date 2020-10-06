package com.fitbitsample.viewmodel.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.transform.dom.DOMLocator;

public class User {

    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("ambassador")
    @Expose
    private Boolean ambassador;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("avatar150")
    @Expose
    private String avatar150;
    @SerializedName("avatar640")
    @Expose
    private String avatar640;
    @SerializedName("averageDailySteps")
    @Expose
    private Integer averageDailySteps;
    @SerializedName("clockTimeDisplayFormat")
    @Expose
    private String clockTimeDisplayFormat;
    @SerializedName("corporate")
    @Expose
    private Boolean corporate;
    @SerializedName("corporateAdmin")
    @Expose
    private Boolean corporateAdmin;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("displayNameSetting")
    @Expose
    private String displayNameSetting;
    @SerializedName("distanceUnit")
    @Expose
    private String distanceUnit;
    @SerializedName("encodedId")
    @Expose
    private String encodedId;
    @SerializedName("familyGuidanceEnabled")
    @Expose
    private Boolean familyGuidanceEnabled;
    @SerializedName("features")
    @Expose
    private Features features;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("foodsLocale")
    @Expose
    private String foodsLocale;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("glucoseUnit")
    @Expose
    private String glucoseUnit;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("heightUnit")
    @Expose
    private String heightUnit;
    @SerializedName("isChild")
    @Expose
    private Boolean isChild;
    @SerializedName("isGraduationAvailable")
    @Expose
    private Boolean isGraduationAvailable;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("memberSince")
    @Expose
    private String memberSince;
    @SerializedName("mfaEnabled")
    @Expose
    private Boolean mfaEnabled;
    @SerializedName("offsetFromUTCMillis")
    @Expose
    private Integer offsetFromUTCMillis;
    @SerializedName("startDayOfWeek")
    @Expose
    private String startDayOfWeek;
    @SerializedName("strideLengthRunning")
    @Expose
    private Double strideLengthRunning;
    @SerializedName("strideLengthRunningType")
    @Expose
    private String strideLengthRunningType;
    @SerializedName("strideLengthWalking")
    @Expose
    private Double strideLengthWalking;
    @SerializedName("strideLengthWalkingType")
    @Expose
    private String strideLengthWalkingType;
    @SerializedName("swimUnit")
    @Expose
    private String swimUnit;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("topBadges")
    @Expose
    private List<Object> topBadges = null;
    @SerializedName("waterUnit")
    @Expose
    private String waterUnit;
    @SerializedName("waterUnitName")
    @Expose
    private String waterUnitName;
    @SerializedName("weight")
    @Expose
    private Double weight;
    @SerializedName("weightUnit")
    @Expose
    private String weightUnit;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getAmbassador() {
        return ambassador;
    }

    public void setAmbassador(Boolean ambassador) {
        this.ambassador = ambassador;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar150() {
        return avatar150;
    }

    public void setAvatar150(String avatar150) {
        this.avatar150 = avatar150;
    }

    public String getAvatar640() {
        return avatar640;
    }

    public void setAvatar640(String avatar640) {
        this.avatar640 = avatar640;
    }

    public Integer getAverageDailySteps() {
        return averageDailySteps;
    }

    public void setAverageDailySteps(Integer averageDailySteps) {
        this.averageDailySteps = averageDailySteps;
    }

    public String getClockTimeDisplayFormat() {
        return clockTimeDisplayFormat;
    }

    public void setClockTimeDisplayFormat(String clockTimeDisplayFormat) {
        this.clockTimeDisplayFormat = clockTimeDisplayFormat;
    }

    public Boolean getCorporate() {
        return corporate;
    }

    public void setCorporate(Boolean corporate) {
        this.corporate = corporate;
    }

    public Boolean getCorporateAdmin() {
        return corporateAdmin;
    }

    public void setCorporateAdmin(Boolean corporateAdmin) {
        this.corporateAdmin = corporateAdmin;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayNameSetting() {
        return displayNameSetting;
    }

    public void setDisplayNameSetting(String displayNameSetting) {
        this.displayNameSetting = displayNameSetting;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getEncodedId() {
        return encodedId;
    }

    public void setEncodedId(String encodedId) {
        this.encodedId = encodedId;
    }

    public Boolean getFamilyGuidanceEnabled() {
        return familyGuidanceEnabled;
    }

    public void setFamilyGuidanceEnabled(Boolean familyGuidanceEnabled) {
        this.familyGuidanceEnabled = familyGuidanceEnabled;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFoodsLocale() {
        return foodsLocale;
    }

    public void setFoodsLocale(String foodsLocale) {
        this.foodsLocale = foodsLocale;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGlucoseUnit() {
        return glucoseUnit;
    }

    public void setGlucoseUnit(String glucoseUnit) {
        this.glucoseUnit = glucoseUnit;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(String heightUnit) {
        this.heightUnit = heightUnit;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean isChild) {
        this.isChild = isChild;
    }

    public Boolean getIsGraduationAvailable() {
        return isGraduationAvailable;
    }

    public void setIsGraduationAvailable(Boolean isGraduationAvailable) {
        this.isGraduationAvailable = isGraduationAvailable;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public Boolean getMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(Boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public Integer getOffsetFromUTCMillis() {
        return offsetFromUTCMillis;
    }

    public void setOffsetFromUTCMillis(Integer offsetFromUTCMillis) {
        this.offsetFromUTCMillis = offsetFromUTCMillis;
    }

    public String getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public void setStartDayOfWeek(String startDayOfWeek) {
        this.startDayOfWeek = startDayOfWeek;
    }

    public Double getStrideLengthRunning() {
        return strideLengthRunning;
    }

    public void setStrideLengthRunning(Double strideLengthRunning) {
        this.strideLengthRunning = strideLengthRunning;
    }

    public String getStrideLengthRunningType() {
        return strideLengthRunningType;
    }

    public void setStrideLengthRunningType(String strideLengthRunningType) {
        this.strideLengthRunningType = strideLengthRunningType;
    }

    public Double getStrideLengthWalking() {
        return strideLengthWalking;
    }

    public void setStrideLengthWalking(Double strideLengthWalking) {
        this.strideLengthWalking = strideLengthWalking;
    }

    public String getStrideLengthWalkingType() {
        return strideLengthWalkingType;
    }

    public void setStrideLengthWalkingType(String strideLengthWalkingType) {
        this.strideLengthWalkingType = strideLengthWalkingType;
    }

    public String getSwimUnit() {
        return swimUnit;
    }

    public void setSwimUnit(String swimUnit) {
        this.swimUnit = swimUnit;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<Object> getTopBadges() {
        return topBadges;
    }

    public void setTopBadges(List<Object> topBadges) {
        this.topBadges = topBadges;
    }

    public String getWaterUnit() {
        return waterUnit;
    }

    public void setWaterUnit(String waterUnit) {
        this.waterUnit = waterUnit;
    }

    public String getWaterUnitName() {
        return waterUnitName;
    }

    public void setWaterUnitName(String waterUnitName) {
        this.waterUnitName = waterUnitName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    @Override
    public String toString() {
        return "{" +
                "age=" + age +
                ", \n ambassador=" + ambassador +
                ", \n avatar='" + avatar + '\'' +
                ", \n avatar150='" + avatar150 + '\'' +
                ", \n avatar640='" + avatar640 + '\'' +
                ", \n averageDailySteps=" + averageDailySteps +
                ", \nclockTimeDisplayFormat='" + clockTimeDisplayFormat + '\'' +
                ", \ncorporate=" + corporate +
                ", \ncorporateAdmin=" + corporateAdmin +
                ", \ndateOfBirth='" + dateOfBirth + '\'' +
                ", \ndisplayName='" + displayName + '\'' +
                ", \ndistanceUnit='" + distanceUnit + '\'' +
                ", \nfeatures=" + features +
                ", \nfirstName='" + firstName + '\'' +
                ", \nfoodsLocale='" + foodsLocale + '\'' +
                ", \nfullName='" + fullName + '\'' +
                ", \ngender='" + gender + '\'' +
                ", \nglucoseUnit='" + glucoseUnit + '\'' +
                ", \nheight=" + height +
                ", \nheightUnit='" + heightUnit + '\'' +
                ", \nisChild=" + isChild +
                ", \nlastName='" + lastName + '\'' +
                ", \nlocale='" + locale + '\'' +
                ", \nmemberSince='" + memberSince + '\'' +
                ", \nstartDayOfWeek='" + startDayOfWeek + '\'' +
                ", \nstrideLengthRunning=" + strideLengthRunning +
                ", \nstrideLengthRunningType='" + strideLengthRunningType + '\'' +
                ", \nstrideLengthWalking=" + strideLengthWalking +
                ", \nstrideLengthWalkingType='" + strideLengthWalkingType + '\'' +
                ", \nswimUnit='" + swimUnit + '\'' +
                ", \ntimezone='" + timezone + '\'' +
                ", \ntopBadges=" + topBadges +
                ", \nwaterUnit='" + waterUnit + '\'' +
                ", \nwaterUnitName='" + waterUnitName + '\'' +
                ", \nweight=" + weight +
                ", \nweightUnit='" + weightUnit + '\'' +
                '}';
    }
}