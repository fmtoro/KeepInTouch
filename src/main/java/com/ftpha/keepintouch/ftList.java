package com.ftpha.keepintouch;

/**
 * Created by Fernando on 2015-05-04.
 * Originally created as part of: SQLTake2
 * You will love this code and be awed by it's magnificence
 */
public class ftList {



    private long         lId;
    private String      lName;
    private String      lText;
    private long         cId;

    public ftList() {
    }

    public long getLId() {
        return lId;
    }

    public void setLId(long lId) {
        this.lId = lId;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getLText() {
        return lText;
    }

    public void setLText(String lText) {
        this.lText = lText;
    }

    public long getCId() {
        return cId;
    }

    public void setCId(long cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return lName;
    }
}
