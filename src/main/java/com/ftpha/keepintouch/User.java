package com.ftpha.keepintouch;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by Fernando on 2015-05-04.
 */
public class User implements Serializable{

    private long uId;
    private String uName;
    private String uPhone;
    private String uEmail;
    private String uImge;

    private String uActive;

    private String uFrom;
    private String uTo;
    private String uUnit;
    private String uUseSMS;
    private String uUseEmail;
    private String uJustMF;

    private String uJustOH;


    public String getUJustOH() {
        return uJustOH;
    }

    public void setUJustOH(String uJustOH) {
        this.uJustOH = uJustOH;
    }

    public String getUActive() {
        return uActive;
    }

    public void setUActive(String uActive) {
        this.uActive = uActive;
    }

    public String getUFrom() {
        return uFrom;
    }

    public void setUFrom(String uFrom) {
        this.uFrom = uFrom;
    }

    public String getUTo() {
        return uTo;
    }

    public void setUTo(String uTo) {
        this.uTo = uTo;
    }

    public String getUUnit() {
        return uUnit;
    }

    public void setUUnit(String uUnit) {
        this.uUnit = uUnit;
    }

    public String getUUseSMS() {
        return uUseSMS;
    }

    public void setUUseSMS(String uUseSMS) {
        this.uUseSMS = uUseSMS;
    }

    public String getUUseEmail() {
        return uUseEmail;
    }

    public void setUUseEmail(String uUseEmail) {
        this.uUseEmail = uUseEmail;
    }

    public String getUJustMF() {
        return uJustMF;
    }

    public void setUJustMF(String uJustMF) {
        this.uJustMF = uJustMF;
    }

    public String getUImge() {
        return uImge;
    }

    public void setUImge(String uImge) {
        this.uImge = uImge;
    }

    public User() {
    }

    public long getUId() {
        return uId;
    }

    public void setUId(long uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUPhone() {
        return uPhone;
    }

    public void setUPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void getUser(long uID, Context cxt){

        dsKit dsK =  new dsKit(cxt);

        User u = dsK.getUser(uID);

        this.uId       = u.uId         ;
        this.uName     = u.uName       ;
        this.uPhone    = u.uPhone      ;
        this.uEmail    = u.uEmail      ;
        this.uImge     = u.uImge       ;

        this.uActive   = u.uActive     ;

        this.uFrom     = u.uFrom       ;
        this.uTo       = u.uTo         ;
        this.uUnit     = u.uUnit       ;
        this.uUseSMS   = u.uUseSMS     ;
        this.uUseEmail = u.uUseEmail   ;
        this.uJustMF   = u.uJustMF     ;
        this.uJustOH   = u.uJustOH     ;


    }

    @Override
    public String toString() {
        return uName;
    }


//    public boolean updateUser(User u, Context cxt){
//        return new dsKit(cxt).updateUser(u);
//    }

    public User createUser(User user, Context cxt){
        return new dsKit(cxt).createUser(user);
    }



//    public User createUser(String nom,
//                           String phone,
//                           String email,
//                           String image,
//                           Context cxt){
//        dsKit kitDB = new dsKit(cxt);
//        return kitDB.createUser(nom,
//                                phone,
//                                email,
//                                image);
//    }


    public boolean updateUser(Context cxt){
        return new dsKit(cxt).updateUser(this);
    }

}
