package com.soma.second.matnam.ui.models;

import android.graphics.Bitmap;

/**
 * Created by Dongjun on 15. 11. 2..
 */
public class InstagramFollwer {

    private String id;
    private String full_name;
    private String user_name;
    private Bitmap profile_img;

    public InstagramFollwer(String _id, String _full_name, String _user_name, Bitmap _img) {
        this.id = _id;
        this.full_name = _full_name;
        this.user_name = _user_name;
        this.profile_img = _img;
    }

    public String getId() { return this.id; }
    public void setId(String _id) { this.id = _id;  }

    public String getFullName() { return this.full_name; }
    public void setFullName(String _name) { this.full_name = _name;  }

    public String getUserName() { return this.user_name; }
    public void setUserName(String _name) { this.user_name = _name;  }

    public Bitmap getProfileImg() { return this.profile_img; }
    public void setProfileImg(Bitmap _img) { this.profile_img = _img;  }

}
