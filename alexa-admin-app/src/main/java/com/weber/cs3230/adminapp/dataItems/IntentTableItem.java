package com.weber.cs3230.adminapp.dataItems;

public class IntentTableItem {
    private String intentName;
    private String dateAdded;

    public IntentTableItem(String intentName, String dateAdded) {
        this.intentName = intentName;
        this.dateAdded = dateAdded;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
