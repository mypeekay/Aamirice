package com.peekay.aamirice.bean;

import android.graphics.Bitmap;

public class BitmapSave {
    private static Bitmap bitmap;

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        BitmapSave.bitmap = bitmap;
    }
}
