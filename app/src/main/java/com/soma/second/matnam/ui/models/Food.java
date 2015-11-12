package com.soma.second.matnam.ui.models;

import android.graphics.Bitmap;

/**
 * Created by Dongjun on 15. 11. 2..
 */
public class Food {

    private String imgUrl;

    public Food(String _imgUrl) {
        this.imgUrl = _imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
