package com.example.kookmin.item;

import java.io.Serializable;

/**
 * Created by 보운 on 2015-10-30.
 */
public class Item implements Serializable {
    private String title;
    private String location;
    private String hour;
    private String minute;
    private String people;

    public Item() {
        title = "";
        location = "";
        hour = "";
        minute = "";
        people = "";
    }

    public Item(String title, String location, String hour, String minute, String people) {
        this.title = title;
        this.location = location;
        this.hour = hour;
        this.minute = minute;
        this.people = people;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getPeople() {
        return people;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
