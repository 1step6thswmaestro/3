package com.example.kimyoungjoon.myapplication.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by kimyoungjoon on 2015. 10. 31..
 */
@Entity
public class MeetingRecord {
    @Id
    private Long id;
    private Long place;
    private int time;
    private String male;
    private String femail;

    public MeetingRecord(Long id, Long place, int time, String male, String femail) {
        this.id = id;
        this.place = place;
        this.time = time;
        this.male = male;
        this.femail = femail;
    }

    public MeetingRecord(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlace() {
        return place;
    }

    public void setPlace(Long place) {
        this.place = place;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getFemail() {
        return femail;
    }

    public void setFemail(String femail) {
        this.femail = femail;
    }
}
