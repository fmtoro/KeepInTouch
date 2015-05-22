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
            ftDB.U_C_JUST_MF,
            ftDB.U_C_JUST_OH
    };

    private static final String[] allLTCs = {

            ftDB.L_C_ID,
            ftDB.L_C_NAME,
            ftDB.L_C_TEXT,
            ftDB.L_C_FK_USER
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

        values.put(ftDB.U_C_FROM, user.getUFrom());
        values.put(ftDB.U_C_TO, user.getUTo());
        values.put(ftDB.U_C_UNIT, user.getUUnit());
        values.put(ftDB.U_C_USE_SMS, user.getUUseSMS());
        values.put(ftDB.U_C_USE_EMAIL, user.getUEmail());
        values.put(ftDB.U_C_JUST_MF, user.getUJustMF());
        values.put(ftDB.U_C_JUST_OH, user.getUJustOH());
        values.put(ftDB.U_C_ACTIVE,     user.getUActive());


        long insertUId = db.insert(ftDB.T_USERS, null,values );
        user.setUId(insertUId);
        return user;
    }

    public ftList createListItem(ftList ftL){

        Open();

        ContentValues values = new ContentValues();

        values.put(ftDB.L_C_NAME, ftL.getLName());
        values.put(ftDB.L_C_TEXT, ftL.getLText());
        values.put(ftDB.L_C_FK_USER, ftL.getUId());
        long listItemID = db.insert(ftDB.T_LISTS, null, values);
        ftL.setLId(listItemID);

        Close();

        return ftL;
    }



    public User createUser(String nom,
                           String phone,
                           String email,
                           String image,

                           String uFrom,
                           String uTo,
                           String uUnit,
                           String uUseSMS,
                           String uUseEmail,
                           String uJustMF,
                           String uActive
    ){
        User user = new User();

        user.setUName       (nom);
        user.setUPhone(phone);
        user.setUEmail(email);
        user.setUImge(image);

        user.setUFrom(uFrom);
        user.setUTo(uTo);
        user.setUUnit(uUnit);
        user.setUUseSMS(uUseSMS);
        user.setUUseEmail(uUseEmail);
        user.setUJustMF(uJustMF);
        user.setUActive(uActive);

        user = createUser(user);
        Log.i(LOGTAG, "User created with Id: " + user.getUId());

        return user;
    }

    public User createUser(String nom,
                           String phone,
                           String email,
                           String image) {
        User user = new User();
        user.setUName(nom);
        user.setUPhone(phone);
        user.setUEmail(email);
        user.setUImge(image);

        user.setUFrom("1");
        user.setUTo("5");
        user.setUUnit("Week");
        user.setUUseSMS("Yes");
        user.setUUseEmail("No");
        user.setUJustMF("Yes");
        user.setUActive("Yes");

        user = createUser(user);
        Log.i(LOGTAG, "User created with Id: " + user.getUId());

        return user;
    }
    public boolean updateUser(User u){

        return  updateUser(
                u.getUId(),
                u.getUName(),
                u.getUPhone(),
                u.getUEmail(),
                u.getUImge(),

                u.getUFrom(),
                u.getUTo(),
                u.getUUnit(),
                u.getUUseSMS(),
                u.getUUseEmail(),
                u.getUJustMF(),
                u.getUActive());

    }

    public boolean updateUser(
            long uID,
            String nuName,
            String nuPhone,
            String nuEmail,
            String nuImage,

            String uFrom        ,
            String uTo          ,
            String uUnit        ,
            String uUseSMS      ,
            String uUseEmail    ,
            String uJustMF      ,
            String uActive
    ){

        Open();
        ContentValues vals = new ContentValues();

        vals.put(ftDB.U_C_NAME, nuName);
        vals.put(ftDB.U_C_PHONE, nuPhone);
        vals.put(ftDB.U_C_EMAIL, nuEmail);
        vals.put(ftDB.U_C_IMAGE, nuImage);

        vals.put(ftDB.U_C_FROM,      uFrom      );
        vals.put(ftDB.U_C_TO,        uTo        );
        vals.put(ftDB.U_C_UNIT,      uUnit      );
        vals.put(ftDB.U_C_USE_SMS,   uUseSMS    );
        vals.put(ftDB.U_C_USE_EMAIL, uUseEmail  );
        vals.put(ftDB.U_C_JUST_MF,   uJustMF    );
        vals.put(ftDB.U_C_ACTIVE,    uActive    );


        boolean rslt;

        rslt = db.update(ftDB.T_USERS, vals, ftDB.U_C_ID + " = " + uID,null ) == 1;
        Close();

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
                user.setUFrom(cursor.getString(cursor.getColumnIndex(ftDB.U_C_FROM)));
                user.setUTo(cursor.getString(cursor.getColumnIndex(ftDB.U_C_TO)));
                user.setUUnit(cursor.getString(cursor.getColumnIndex(ftDB.U_C_UNIT)));
                user.setUUseSMS(cursor.getString(cursor.getColumnIndex(ftDB.U_C_USE_SMS)));
                user.setUUseEmail(cursor.getString(cursor.getColumnIndex(ftDB.U_C_USE_EMAIL)));
                user.setUJustMF(cursor.getString(cursor.getColumnIndex(ftDB.U_C_JUST_MF)));
                user.setUActive(cursor.getString(cursor.getColumnIndex(ftDB.U_C_ACTIVE)));

                users.add(user);
            }
        }

        return users;
    }


    public ftList createList(ftList fTList){

        ContentValues values = new ContentValues();

        values.put(ftDB.L_C_NAME, fTList.getLName());
        values.put(ftDB.L_C_TEXT, fTList.getLText());
        values.put(ftDB.L_C_FK_USER , fTList.getUId()   );

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
                list.setUId(cursor.getLong(cursor.getColumnIndex(ftDB.L_C_FK_USER)));
                ftlists.add(list);
            }
        }

        return ftlists;
    }

    public  User getUser(long uID){

        User user = new User();

        Open();


        Cursor cursor = db.query(
                ftDB.T_USERS,
                allUTCs,
                ftDB.U_C_ID + " = " + uID,
                null,
                null,
                null,
                null);


        if (cursor.getCount() > 0) {
            cursor.moveToNext();

            user.setUId(cursor.getLong(cursor.getColumnIndex(ftDB.U_C_ID)));
            user.setUName(cursor.getString(cursor.getColumnIndex(ftDB.U_C_NAME)));
            user.setUPhone(cursor.getString(cursor.getColumnIndex(ftDB.U_C_PHONE)));
            user.setUEmail(cursor.getString(cursor.getColumnIndex(ftDB.U_C_EMAIL)));
            if (cursor.getString(cursor.getColumnIndex(ftDB.U_C_IMAGE)) != null) {
                user.setUImge(cursor.getString(cursor.getColumnIndex(ftDB.U_C_IMAGE)));
            }
            user.setUFrom(cursor.getString(cursor.getColumnIndex(ftDB.U_C_FROM)));
            user.setUTo(cursor.getString(cursor.getColumnIndex(ftDB.U_C_TO)));
            user.setUUnit(cursor.getString(cursor.getColumnIndex(ftDB.U_C_UNIT)));
            user.setUUseSMS(cursor.getString(cursor.getColumnIndex(ftDB.U_C_USE_SMS)));
            user.setUUseEmail(cursor.getString(cursor.getColumnIndex(ftDB.U_C_USE_EMAIL)));
            user.setUJustMF(cursor.getString(cursor.getColumnIndex(ftDB.U_C_JUST_MF)));
            user.setUActive(cursor.getString(cursor.getColumnIndex(ftDB.U_C_ACTIVE)));


        }

        Close();
        return user;
    }

    public List<ftList> findListForUser(long userID){  //Aqui arreglar esto
        List<ftList> ftlists = new ArrayList<ftList>();

        Cursor cursor = db.query(
                ftDB.T_LISTS,
                allLTCs,
                ftDB.L_C_FK_USER + " = " + userID,
                null,
                null,
                null,
                null);

        Log.i(LOGTAG, "Found " + cursor.getCount() + " rows in " + ftDB.T_USERS);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ftList list = new ftList();
                list.setLId(cursor.getLong(cursor.getColumnIndex(ftDB.L_C_ID)));
                list.setLName(cursor.getString(cursor.getColumnIndex(ftDB.L_C_NAME)));
                list.setLText(cursor.getString(cursor.getColumnIndex(ftDB.L_C_TEXT)));
                list.setUId(cursor.getLong(cursor.getColumnIndex(ftDB.L_C_FK_USER)));
                ftlists.add(list);
            }
        }

        return ftlists;
    } //Aqui Arreglar esto

    public void createList(
            String nom,
            String lText,
            long uId){

        ftList fTList = new ftList();
        fTList.setLName(nom);
        fTList.setLText(lText);
        fTList.setUId(uId);

        fTList = createList(fTList);
        Log.i(LOGTAG, "List created with Id: " + fTList.getLId());
    }


}
