package com.xnsj.watering;

public class TreeBean {
    enum DotType{
        TREE,//果树
        FAUCET//水龙头
    }
    private DotType dotType;//点的类型
    private boolean isExist;//是否存在  （表面树是否存活，或者水龙头是否开启）
    private long linePosition;//行位置
    private long columnPosition;//列位置

    public DotType getDotType() {
        return dotType;
    }

    public void setDotType(DotType dotType) {
        this.dotType = dotType;
    }

    public boolean getIsExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public long getLinePosition() {
        return linePosition;
    }

    public void setLinePosition(long linePosition) {
        this.linePosition = linePosition;
    }

    public long getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(long columnPosition) {
        this.columnPosition = columnPosition;
    }
}
