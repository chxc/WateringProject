package com.xnsj.watering;
/**
 * 用来保存游戏花费时间的类
 * */
public class GameLevelBean {
    private int level;
    private long passTime;//花费的时间   默认值为0   当为0的时为最大时间

    public GameLevelBean(int level, long passTime) {
        this.level = level;
        this.passTime = passTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getPassTime() {
        return passTime;
    }

    public void setPassTime(long passTime) {
        this.passTime = passTime;
    }

    @Override
    public String toString() {
        return "GameLevelBean{" +
                "level=" + level +
                ", passTime=" + passTime +
                '}';
    }
}
