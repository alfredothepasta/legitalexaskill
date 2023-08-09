package com.weber.cs3230.adminapp.dto;

public class MetricDetail {
    private String eventName;
    private long count;
    private String mostRecentDate;


    public String getEventName() {
        return eventName;
    }


    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public long getCount() {
        return count;
    }


    public void setCount(long count) {
        this.count = count;
    }


    public String getMostRecentDate() {
        return mostRecentDate;
    }


    public void setMostRecentDate(String mostRecentDate) {
        this.mostRecentDate = mostRecentDate;
    }
}
