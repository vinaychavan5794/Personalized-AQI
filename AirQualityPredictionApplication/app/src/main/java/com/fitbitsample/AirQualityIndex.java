package com.fitbitsample;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AirQualityIndex implements Serializable {


    public List <Pollutant> pollutantList=new ArrayList<>();

    public List<Pollutant> getPollutantList() {
        return pollutantList;
    }

    public void setPollutantList(List<Pollutant> pollutantList) {
        this.pollutantList = pollutantList;
    }

    public static class Pollutant{
        private int aqi;
        private String pollutant;
        private String category;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public String getPollutant() {
            return pollutant;
        }

        public void setPollutant(String pollutant) {
            this.pollutant = pollutant;
        }
    }
}
