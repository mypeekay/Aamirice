package com.peekay.aamirice.bean;

public class UpdateJson {
    int v;
    String v1;
    String url;
    String content;
    String time;
    String post;

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPost() {
        return post;
    }

    public UpdateJson() {
    }

    public void setPost(String post) {
        this.post = post;
    }

    public UpdateJson(int v, String v1, String url, String content, String time, String post) {
        this.v = v;
        this.v1 = v1;
        this.url = url;
        this.content = content;
        this.time = time;
        this.post = post;
    }
}
