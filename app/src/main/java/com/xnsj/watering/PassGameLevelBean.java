package com.xnsj.watering;

/**
 * 游戏关卡信息
 * */
public class PassGameLevelBean {
    private int currentLevel;//当前等级
    private boolean isPass;//是否通关
    private long passTime;//通关时间
    private int useNum;//通关所用的水龙头个数
    private double passScore;//通关分数

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public long getPassTime() {
        return passTime;
    }

    public void setPassTime(long passTime) {
        this.passTime = passTime;
    }

    public int getUseNum() {
        return useNum;
    }

    public void setUseNum(int useNum) {
        this.useNum = useNum;
    }

    public double getPassScore() {
        return passScore;
    }

    public void setPassScore(double passScore) {
        this.passScore = passScore;
    }
}
