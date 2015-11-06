package com.example.kimyoungjoon.myapplication.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by kimyoungjoon on 2015. 10. 31..
 */
@Entity
public class LikeRoomRecord {
    @Id
    private Long id;
    private Long place_id;
    private String title;
    private String date;
    private String members_id;
    private int member_count;

    public LikeRoomRecord(Long id, Long place, String title, String date, String members_id, int member_count) {
        this.id = id;
        this.place_id = place;
        this.title = title;
        this.date = date;
        this.members_id = members_id;
        this.member_count = member_count;
    }

    public LikeRoomRecord(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Long place_id) {
        this.place_id = place_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMembers_id() {
        return members_id;
    }

    public void setMembers_id(String members_id) {
        this.members_id = members_id;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }
}
