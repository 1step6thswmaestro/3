package com.soma.second.matnam.ui.models;

import android.graphics.Bitmap;

/**
 * Created by Dongjun on 15. 11. 2..
 */
public class Food {

    private Bitmap bitmap;

    public Food(Bitmap _bitmap) {
        this.bitmap = _bitmap;
    }

    public Bitmap getBitmap() { return this.bitmap; }
    public void setBitmap(Bitmap _bitmap) { this.bitmap = _bitmap;  }
}
