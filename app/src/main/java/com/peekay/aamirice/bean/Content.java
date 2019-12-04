package com.peekay.aamirice.bean;

import android.graphics.Bitmap;

public class Content {
    String name,time,content;
    int type;
    Bitmap bitmap;

    public Content(String name, String time, String content, int type, Bitmap bitmap) {
        this.name = name;
        this.time = time;
        this.content = content;
        this.type = type;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Content(String name, String time, String content, int type) {
        this.name = name;
        this.time = time;
        this.content = content;
        this.type = type;
    }
}
