package com.example.quakereport;

public class Earthquake1 {
    private double mMagnitude;
    private String URL;
    private  String mLocation;
    private long mMilliSeconds;
    public Earthquake1(double magnitude, String location, long miliseconds,String url){
        mMagnitude=magnitude;
        mLocation=location;
        mMilliSeconds=miliseconds;
        URL=url;
    }

    public double getmMagnitude(){
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getmMilliSeconds() {
        return mMilliSeconds;
    }

    public String getURL() {
        return URL;
    }
}
