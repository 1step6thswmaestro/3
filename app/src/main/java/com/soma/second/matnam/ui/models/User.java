package com.soma.second.matnam.ui.models;

import android.graphics.Bitmap;

/**
 * Created by Dongjun on 15. 11. 6..
 */
public class User {

    private static String ID;
    private static String FULL_NAME;
    private static String USER_NAME;
    private static Bitmap PROFILE_IMG;

    public static String getId() { return ID; }
    public static void setId(String _id) { ID = _id; }

    public static String getFullName() {
        return FULL_NAME;
    }

    public static void setFullName(String fullName) {
        FULL_NAME = fullName;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public static void setUserName(String userName) {
        USER_NAME = userName;
    }

    public static Bitmap getProfileImg() {
        return PROFILE_IMG;
    }

    public static void setProfileImg(Bitmap profileImg) {
        PROFILE_IMG = profileImg;
    }
}
