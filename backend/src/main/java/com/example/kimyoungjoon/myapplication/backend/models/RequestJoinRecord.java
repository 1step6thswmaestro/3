package com.example.kimyoungjoon.myapplication.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by kimyoungjoon on 2015. 10. 31..
 */
@Entity
public class RequestJoinRecord {
    @Id
    private Long id;
    private Long room_id;
    private String receive_members_id;
    private String requst_members_id;

    public RequestJoinRecord(Long id, Long room_id, String receive_members_id, String requst_members_id) {
        this.id = id;
        this.room_id = room_id;
        this.receive_members_id = receive_members_id;
        this.requst_members_id = requst_members_id;
    }

    public RequestJoinRecord(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public String getReceive_members_id() {
        return receive_members_id;
    }

    public void setReceive_members_id(String receive_members_id) {
        this.receive_members_id = receive_members_id;
    }

    public String getRequst_members_id() {
        return requst_members_id;
    }

    public void setRequst_members_id(String requst_members_id) {
        this.requst_members_id = requst_members_id;
    }
}
