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
    private String word;
    private int join;

    public Item() {
        title = "";
        location = "";
        hour = "";
        minute = "";
        people = "";
        word = "";
        join = 1;
    }

    public Item(String title, String location, String hour, String minute, String people, String word) {
        this.title = title;
        this.location = location;
        this.hour = hour;
        this.minute = minute;
        this.people = people;
        this.word = word;
        join = 1;
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

    public String getWord() {
        return word;
    }

    public int getJoin() {
        return join;
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

    public void setWord(String word) {
        this.word = word;
    }

    public void setJoin(int join) {
        this.join = join;
    }

    public void joinPlus() {
        this.join++;
    }
}
