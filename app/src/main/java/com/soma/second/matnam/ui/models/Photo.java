package com.soma.second.matnam.ui.models;

import android.graphics.Bitmap;

/**
 * Created by youngjoosuh on 2015. 11. 9..
 */
public class Photo {
    private Bitmap bitmap;

    public Photo(Bitmap _bitmap) {
        this.bitmap = _bitmap;
    }

    public Bitmap getBitmap() { return this.bitmap; }
    public void setBitmap(Bitmap _bitmap) { this.bitmap = _bitmap;  }
}
