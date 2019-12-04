package com.peekay.aamirice.bean;

public class LeftList {
    int pic;
    String name;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LeftList(int pic, String name) {
        this.pic = pic;
        this.name = name;
    }
}
