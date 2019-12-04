package com.peekay.aamirice.bean;

public class SettingBean {
    String name,intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public SettingBean(String name, String intro) {
        this.name = name;
        this.intro = intro;
    }
}
