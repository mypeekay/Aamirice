package com.peekay.aamirice.bean;

import android.graphics.Bitmap;

public class AmStudioBitmaps {
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public AmStudioBitmaps(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
