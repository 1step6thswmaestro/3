package com.soma.second.matnam.listdubbies.provider;

import com.example.kimyoungjoon.myapplication.backend.matnamApi.model.LikeRoomRecord;
import com.soma.second.matnam.ui.models.InstagramFollwer;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static long[] foodId_left = new long[10];
    public static long[] foodId_right = new long[10];

    public static String[] foodName_left = new String[10];
    public static String[] foodName_right = new String[10];

    public static String[] foodImgUrl_left = new String[10];
    public static String[] foodImgUrl_right = new String[10];

    public static ArrayList<InstagramFollwer> instagramFollwerList = new ArrayList<>();
    public static List<LikeRoomRecord> likeRoomRecordList;

}
