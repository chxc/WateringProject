package com.xnsj.watering;
/**
 * 用来保存游戏花费时间的类
 * */
public class GameLevelBean {
    private int level;
    private long passTime;//花费的时间   默认值为0   当为0的时为最大时间
    private int taps_num;//水龙头数量

    public GameLevelBean(int level, long passTime,int taps_num) {
        this.level = level;
        this.passTime = passTime;
        this.taps_num = taps_num;
    }

    public int getTaps_num() {
        return taps_num;
    }

    public void setTaps_num(int taps_num) {
        this.taps_num = taps_num;
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
                ", taps_num=" + taps_num +
                '}';
    }
}
