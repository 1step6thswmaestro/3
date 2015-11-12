package com.soma.second.matnam.ui.models;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by Dongjun on 15. 11. 2..
 */
public class InstagramFollwer {

    private String id;
    private String full_name;
    private String user_name;
    private String profile_img_url;

    public InstagramFollwer(String _id, String _full_name, String _user_name, String _profile_img_url) {
        this.id = _id;
        this.full_name = _full_name;
        this.user_name = _user_name;
        this.profile_img_url = _profile_img_url;
    }

    public String getId() { return this.id; }
    public void setId(String _id) { this.id = _id;  }

    public String getFullName() { return this.full_name; }
    public void setFullName(String _name) { this.full_name = _name;  }

    public String getUserName() { return this.user_name; }
    public void setUserName(String _name) { this.user_name = _name;  }


    public String getProfile_img_url() {
        return profile_img_url;
    }

    public void setProfile_img_url(String profile_img_url) {
        this.profile_img_url = profile_img_url;
    }

}
