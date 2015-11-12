package com.soma.second.matnam.ui.models;

import android.util.Log;

/**
 * Created by youngjoosuh on 2015. 11. 10..
 */
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;

import java.util.ArrayList;

public class MyRoom {

    public static ArrayList<String> mRoom_Id_List = new ArrayList<String>();
    //public static ArrayList<LikeRoomRecord> mMyRoom_List = new ArrayList<LikeRoomRecord>();

    private String mRoom_Owner;
    private long mRoom_Id;
    private long mPlace_Id;
    private String mTitle;
    private String mDate;
    private String mMembers_Id;
    private int mMember_Count;
    private String[] mMembers_Id_List;

    public MyRoom(long mRoom_Id, String mMembers_Id) {
        this.mRoom_Id = mRoom_Id;
        mRoom_Id_List.add(""+mRoom_Id);
        this.mMembers_Id = mMembers_Id;
        mMembers_Id_List = this.mMembers_Id.split(",");
        this.mRoom_Owner = mMembers_Id_List[0];
    }

    public MyRoom(LikeRoomRecord likeRoomRecord) {
        this.mRoom_Id = likeRoomRecord.getId();
        this.mPlace_Id = likeRoomRecord.getPlaceId();
        this.mTitle = likeRoomRecord.getTitle();
        this.mDate = likeRoomRecord.getDate();
        this.mMembers_Id = likeRoomRecord.getMembersId();
        this.mMember_Count = likeRoomRecord.getMemberCount();

        mMembers_Id_List = this.mMembers_Id.split(",");
        this.mRoom_Owner = mMembers_Id_List[0];
        //mMyRoom_List.add(likeRoomRecord);
    }

    public long getmRoom_Id() {
        return  mRoom_Id;
    }
    public void setmRoom_Id(long mRoom_Id) {
        this.mRoom_Id = mRoom_Id;
    }
    public String getmRoom_Owner() {
        return mRoom_Owner;
    }
    public void setmRoom_Owner(String mRoom_Owner) { this.mRoom_Owner = mRoom_Owner; }
    public long getmPlace_Id() {
        return mPlace_Id;
    }
    public void setmPlace_Id(long mPlace_Id) { this.mPlace_Id = mPlace_Id; }
    public String getmTitle() {
        return mTitle;
    }
    public void setmTitle(String mTitle) { this.mTitle = mTitle; }
    public String getmDate() {
        return mDate;
    }
    public void setmDate(String mDate) { this.mDate = mDate; }
    public int getmMember_Count() {
        return mMember_Count;
    }
    public void setmMember_Count(int mMember_Count) {
        this.mMember_Count = mMember_Count;
    }
    public String getmMembers_Id() {
        return mMembers_Id;
    }
    public void setmMembers_Id(String mMembers_Id) {
        this.mMembers_Id = mMembers_Id;
    }


}
