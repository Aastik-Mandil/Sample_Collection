package com.game.samplecollection;

public class SampleInfo {
    String state,city,monsoon,sampleType,desc;
    String latlon;

    public SampleInfo() {
    }

    public SampleInfo(String state, String city, String monsoon, String sampleType, String desc, String latlon) {
        this.state = state;
        this.city = city;
        this.monsoon = monsoon;
        this.sampleType = sampleType;
        this.desc = desc;
        this.latlon = latlon;
    }

    public String getLatitude() { return latlon; }

    public void setLatitude(String latlon) { this.latlon = latlon; }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMonsoon() {
        return monsoon;
    }

    public void setMonsoon(String monsoon) {
        this.monsoon = monsoon;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }
}
