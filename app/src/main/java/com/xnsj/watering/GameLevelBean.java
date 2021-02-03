package com.xnsj.watering;
/**
 * 用来保存游戏花费时间的类
 * */
public class GameLevelBean {
    private int level;
    private String passTime;//花费的时间

    public GameLevelBean(int level, String passTime) {
        this.level = level;
        this.passTime = passTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }
}
