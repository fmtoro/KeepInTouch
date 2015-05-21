package com.ftpha.keepintouch;

/**
 *
 * Created by Fernando on 2015-05-04.
 * Originally created as part of: KeepinTouch
 * You will love this code and be awed by it's magnificence
 *
 */
public class Cat {

    private long cId;
    private String cName;
    private String cFrom;
    private String cTo;
    private String cUnit;
    private String cSMS;
    private String cEmail;
    private String cJustMF;
    private String cActive;
    private long uId;


    public Cat() {
    }

    public long getCId() {
        return cId;
    }

    public void setCId(long cId) {
        this.cId = cId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getCFrom() {
        return cFrom;
    }

    public void setCFrom(String cFrom) {
        this.cFrom = cFrom;
    }

    public String getCTo() {
        return cTo;
    }

    public void setCTo(String cTo) {
        this.cTo = cTo;
    }

    public String getCUnit() {
        return cUnit;
    }

    public void setCUnit(String cUnit) {
        this.cUnit = cUnit;
    }

    public String getCSMS() {
        return cSMS;
    }

    public void setCSMS(String cSMS) {
        this.cSMS = cSMS;
    }

    public String getCEmail() {
        return cEmail;
    }

    public void setCEmail(String cEmail) {
        this.cEmail = cEmail;
    }

    public String getCActive() {
        return cActive;
    }

    public void setCActive(String cActive) {
        this.cActive = cActive;
    }

    public String getCJustMF() {
        return cJustMF;
    }

    public void setCJustMF(String cJustMF) {
        this.cJustMF = cJustMF;
    }

    public long getUId() {
        return uId;
    }

    public void setUId(long uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return cName;
    }
}
