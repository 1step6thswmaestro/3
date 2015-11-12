package com.soma.second.matnam.ui.advrecyclerview.data;

import android.support.v4.util.Pair;
import android.util.Log;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.MatnamApi;
import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.soma.second.matnam.listdubbies.provider.DataProvider;
import com.soma.second.matnam.ui.models.LikeRoom;
import com.soma.second.matnam.ui.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by youngjoosuh on 2015. 11. 12..
 */


public class MyRoomDataProvider{
    private List<Pair<LikeRoomRecord, ArrayList<String> >> mData; //ROOM_INFO and joinRequestUserId in a Pair
    private final ArrayList<LikeRoomRecord> mMyRoom = new ArrayList<LikeRoomRecord>();
    private final ArrayList<String> mJoinRequestUsersId = new ArrayList<String>();
    //mJoinRequestUsersId의 한개의 아이템에는 한개의 조인요청에 포함된 모든 유저들의 아이디를 String 하나로 묶은 형태로 저장하는 것을 가정함(members_Id 처럼)

    public MyRoomDataProvider() {

        mData = new LinkedList<>();

        List<LikeRoomRecord> likeRoomRecordList = DataProvider.likeRoomRecordList;
        Log.v("likeRoomRecordList", likeRoomRecordList.toString());

        for (int i = 0; i < likeRoomRecordList.size(); i++) {

            LikeRoomRecord likeRoomRecord = likeRoomRecordList.get(i);
            Log.v("List.get(i)", likeRoomRecord.toString());
            String membersId = likeRoomRecord.getMembersId();
            Log.v("membersId", membersId);
            String[] memberIdArr = membersId.split(",");
            Log.v("memberIdArr[]", memberIdArr.toString());
            String roomId = ""+likeRoomRecord.getId();

            if (memberIdArr[0].equals(User.getId())) {
                Log.v("memberIdArr[0]", memberIdArr[0]);
                Log.v("User.getId()", User.getId());
                mMyRoom.add(likeRoomRecord);
            }

            mData.add(new Pair<LikeRoomRecord, ArrayList<String>>(likeRoomRecord, mJoinRequestUsersId));

        }
    }

    public ArrayList<LikeRoomRecord> getmMyRoom() {
        return mMyRoom;
    }

    public List<Pair<LikeRoomRecord, ArrayList<String>>> getmData() {
        return mData;
    }

    /*public ArrayList<LikeRoomRecord> getmMyRoom() {
        return mMyRoom;
    }

    public void setmJoinRequestUserId(String request_user_id) {
        mJoinRequestUserId.add(request_user_id);
    }

    public ArrayList<String> getmJoinRequestUserId() {
        return mJoinRequestUserId;
    }*/



}
