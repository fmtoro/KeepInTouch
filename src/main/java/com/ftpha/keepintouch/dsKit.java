package com.ftpha.keepintouch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by Fernando on 2015-05-04.
 * Originally created as part of: SQLTake2
 * You will love this code and be awed by it's magnificence
 *
 */
public class dsKit {

    private SQLiteOpenHelper dbH;
    private SQLiteDatabase db;

    private static final String LOGTAG = "ftpha - sca c r ";

    private static final String[] allUTCs = {
            ftDB.U_C_ID,
            ftDB.U_C_NAME,
            ftDB.U_C_PHONE,
            ftDB.U_C_EMAIL,
            ftDB.U_C_IMAGE,
            ftDB.U_C_ACTIVE,

            ftDB.U_C_FROM,
            ftDB.U_C_TO,
            ftDB.U_C_UNIT,
            ftDB.U_C_USE_SMS,
            ftDB.U_C_USE_EMAIL,
            ftDB.U_C_JUST_MF
    };


    public dsKit(Context context){

        dbH = new ftDB(context);
    }

    public void Open(){


        db = dbH.getWritableDatabase();
        Log.i(LOGTAG," The db has opened");

    }


    public void Close(){

        dbH.close();
        Log.i(LOGTAG, " The db has closed");

    }

    public User createUser(User user){

        Open();

        ContentValues values = new ContentValues();

        values.put(ftDB.U_C_NAME, user.getUName());
        values.put(ftDB.U_C_PHONE, user.getUPhone());
        values.put(ftDB.U_C_EMAIL, user.getUEmail());
        values.put(ftDB.U_C_IMAGE, user.getUImge());

        values.put(ftDB.U_C_FROM,       user.getUFrom());
        values.put(ftDB.U_C_TO,         user.getCTo());
        values.put(ftDB.U_C_UNIT,       user.getCUnit());
        values.put(ftDB.U_C_USE_SMS,    user.getCSMS());
        values.put(ftDB.U_C_USE_EMAIL,  user.getCEmail());
        values.put(ftDB.U_C_JUST_MF,    user.getCJustMF());
        values.put(ftDB.U_C_ACTIVE,     user.getCActive());


        long insertUId = db.insert(ftDB.T_USERS, null,values );
        user.setUId(insertUId);
        return user;
    }


    public User createUser(String nom,
                           String phone,
                           String email,
                           String image){
        User user = new User();
        user.setUName(nom);
        user.setUPhone(phone);
        user.setUEmail(email);
        user.setUImge(image);
        user = createUser(user);
        Log.i(LOGTAG, "User created with Id: " + user.getUId());

        return user;
    }
    public boolean updateUser(User u){

        return  updateUser(u.getUId(),
                u.getUName(),
                u.getUPhone(),
                u.getUEmail(),
                u.getUImge());

    }

    public boolean updateUser(
            long uID,
            String nuName,
            String nuPhone,
            String nuEmail,
            String nuImage){

        Open();
        ContentValues vals = new ContentValues();

        vals.put(ftDB.U_C_NAME, nuName);
        vals.put(ftDB.U_C_PHONE, nuPhone);
        vals.put(ftDB.U_C_EMAIL, nuEmail);
        vals.put(ftDB.U_C_IMAGE, nuImage);


        boolean rslt;

        rslt = db.update(ftDB.T_USERS, vals, ftDB.U_C_ID + " = " + uID,null ) == 1;
        //myDB.update(TableName, cv, "_id " + "="+1, null);
        //db.rawQuery(sqlStat, null);

            return rslt;
    }

    public List<User> findAllUsers(){
        List<User> users = new ArrayList<User>();

        Cursor cursor = db.query(ftDB.T_USERS, allUTCs, null, null, null, null, null);

        Log.i(LOGTAG, "Found " + cursor.getCount() + " rows in " + ftDB.T_USERS);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                User user = new User();
                user.setUId(cursor.getLong(cursor.getColumnIndex(ftDB.U_C_ID)));
                user.setUName(cursor.getString(cursor.getColumnIndex(ftDB.U_C_NAME)));
                user.setUPhone(cursor.getString(cursor.getColumnIndex(ftDB.U_C_PHONE)));
                user.setUEmail(cursor.getString(cursor.getColumnIndex(ftDB.U_C_EMAIL)));
                if (cursor.getString(cursor.getColumnIndex(ftDB.U_C_IMAGE)) != null) {
                    user.setUImge(cursor.getString(cursor.getColumnIndex(ftDB.U_C_IMAGE)));
                }
                users.add(user);
            }
        }

        return users;
    }





    public List<Cat> findAllCats(){
        List<Cat> cats = new ArrayList<Cat>();

        Cursor cursor = db.query(ftDB.T_CATS, allCTCs, null, null, null, null, null);

        Log.i(LOGTAG, "Found " + cursor.getCount() + " rows in " + ftDB.T_CATS);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Cat cat = new Cat();
                cat.setCId(cursor.getLong(cursor.getColumnIndex(ftDB.C_C_ID)));
                cat.setCName(cursor.getString(cursor.getColumnIndex(ftDB.C_C_NAME)));
                cat.setCFrom(cursor.getString(cursor.getColumnIndex(ftDB.C_C_FROM)));
                cat.setCTo(cursor.getString(cursor.getColumnIndex(ftDB.C_C_TO)));
                cat.setCUnit(cursor.getString(cursor.getColumnIndex(ftDB.C_C_UNIT)));
                cat.setCSMS(cursor.getString(cursor.getColumnIndex(ftDB.C_C_SMS)));
                cat.setCEmail(cursor.getString(cursor.getColumnIndex(ftDB.C_C_EMAIL)));
                cat.setCJustMF(cursor.getString(cursor.getColumnIndex(ftDB.C_C_JUST_MF)));
                cat.setCActive(cursor.getString(cursor.getColumnIndex(ftDB.C_C_ACTIVE)));
                cat.setUId(cursor.getLong(cursor.getColumnIndex(ftDB.C_C_FK_USER)));
                cats.add(cat);
            }
        }

        return cats;
    }

    public ftList createList(ftList fTList){

        ContentValues values = new ContentValues();

        values.put(ftDB.L_C_NAME, fTList.getLName());
        values.put(ftDB.L_C_TEXT, fTList.getLText());
        values.put(ftDB.L_C_FK_CAT , fTList.getCId()   );

        long insertLId = db.insert(ftDB.T_LISTS, null, values);
        fTList.setLId(insertLId);

        return fTList;

    }

    public List<ftList> findAllLists(){
        List<ftList> ftlists = new ArrayList<ftList>();

        Cursor cursor = db.query(ftDB.T_USERS, allUTCs, null, null, null, null, null);

        Log.i(LOGTAG, "Found " + cursor.getCount() + " rows in " + ftDB.T_USERS);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ftList list = new ftList();
                list.setLId(cursor.getLong(cursor.getColumnIndex(ftDB.L_C_ID)));
                list.setLName(cursor.getString(cursor.getColumnIndex(ftDB.L_C_NAME)));
                list.setLText(cursor.getString(cursor.getColumnIndex(ftDB.L_C_TEXT)));
                list.setCId(cursor.getLong(cursor.getColumnIndex(ftDB.L_C_FK_CAT)));
                ftlists.add(list);
            }
        }

        return ftlists;
    }





    public void createList(
            String nom,
            String lText,
            long cId){

        ftList fTList = new ftList();
        fTList.setLName(nom);
        fTList.setLText(lText);
        fTList.setCId(cId);
        fTList = createList(fTList);
        Log.i(LOGTAG, "List created with Id: " + fTList.getLId());
    }

    public void createCat(String nom,
                          String cFrom,
                          String cTo,
                          String cUnit,
                          String cSMS,
                          String cEmail,
                          String cJustMF,
                          String cActive,
                          long uId){
        Cat cat = new Cat();
        cat.setCName(nom);
        cat.setCFrom(cFrom);
        cat.setCTo(cTo);
        cat.setCUnit(cUnit);
        cat.setCSMS(cSMS);
        cat.setCEmail(cEmail);
        cat.setCJustMF(cJustMF);
        cat.setCActive(cActive);
        cat.setUId(uId);
        cat = createCat(cat);
        Log.i(LOGTAG, "Category created with Id: " + cat.getCId());
    }
}
