package com.soma.second.matnam.ui.models;

import android.util.Log;

/**
 * Created by youngjoosuh on 2015. 11. 10..
 */
import java.util.ArrayList;

public class MyRoom {

    public static ArrayList<String> mRoom_Id_List = new ArrayList<String>();
    private long mRoom_Id;
    private String mRoom_Owner;
    private String mMembers_Id;
    private String[] mMembers_Id_List;

    public MyRoom(long mRoom_Id, String mMembers_Id) {
        this.mRoom_Id = mRoom_Id;
        mRoom_Id_List.add(""+mRoom_Id);
        this.mMembers_Id = mMembers_Id;
        mMembers_Id_List = this.mMembers_Id.split(",");
        this.mRoom_Owner = mMembers_Id_List[0];
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
    public void setmRoom_Owner(String mRoom_Owner) {
        this.mRoom_Owner = mRoom_Owner;
    }
    public String getmMembers_Id() {
        return mMembers_Id;
    }
    public void setmMembers_Id(String mMembers_Id) {
        this.mMembers_Id = mMembers_Id;
    }


}
