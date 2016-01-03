package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by td.long on 11/19/2015.
 */
public class UserController implements IDataSource {

    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_USER_ID,
            MwSQLiteHelper.COLUMN_USER_NAME,
            MwSQLiteHelper.COLUMN_USER_GENDER,
            MwSQLiteHelper.COLUMN_USER_HEIGHT,
            MwSQLiteHelper.COLUMN_USER_WEIGHT,
            MwSQLiteHelper.COLUMN_USER_FAT,
            MwSQLiteHelper.COLUMN_USER_IMG

    };

    public UserController(Context context){
        dbHelper = new MwSQLiteHelper(context);
    }


    @Override
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public Model create(Model data) {
        ContentValues values = new ContentValues();
        //values.put(MwSQLiteHelper.COLUMN_ID, id);
        User user  = (User)data;
        values.put(MwSQLiteHelper.COLUMN_USER_NAME, user.getName());
        values.put(MwSQLiteHelper.COLUMN_USER_GENDER, user.getGender());
        values.put(MwSQLiteHelper.COLUMN_USER_HEIGHT, user.getHeight());
        values.put(MwSQLiteHelper.COLUMN_USER_WEIGHT, user.getWeight());
        values.put(MwSQLiteHelper.COLUMN_USER_FAT, user.getFat());
        values.put(MwSQLiteHelper.COLUMN_USER_IMG, user.getImg());

        String selectQuery = MwSQLiteHelper.COLUMN_USER_NAME + " = \"" + user.getName() + "\"";
        database.insert(MwSQLiteHelper.TABLE_USER, null,
                values);
        User newUser = (User)get(selectQuery);
        return newUser;
    }

    @Override
    public void update(Model data) {
        User user  = (User)data;
        ContentValues values = new ContentValues();
        values.put(MwSQLiteHelper.COLUMN_USER_NAME, user.getName());
        values.put(MwSQLiteHelper.COLUMN_USER_GENDER, user.getGender());
        values.put(MwSQLiteHelper.COLUMN_USER_HEIGHT, user.getHeight());
        values.put(MwSQLiteHelper.COLUMN_USER_WEIGHT, user.getWeight());
        values.put(MwSQLiteHelper.COLUMN_USER_FAT, user.getFat());
        values.put(MwSQLiteHelper.COLUMN_USER_IMG, user.getImg());


        try {
            // updating row
            database.update(MwSQLiteHelper.TABLE_USER, values, MwSQLiteHelper.COLUMN_USER_ID + " = ?",
                    new String[]{String.valueOf(user.getId())});
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        String countQuery = "SELECT  * FROM " + MwSQLiteHelper.TABLE_USER;
        Cursor cursor = database.rawQuery(countQuery, null);
        int count  = cursor.getCount();
        cursor.close();
        return count;

    }
    @Override
    public List<Model> getAll() {
        List<Model> users = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_USER,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = (User)cursorTo(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }

    @Override
    public Model get(String query) {
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_USER,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        User user = (User)cursorTo(cursor);
        cursor.close();
        return user;
    }

    public User getName(String name) {
        String query = MwSQLiteHelper.COLUMN_USER_NAME + " = " + name ;
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_USER,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        User user = (User)cursorTo(cursor);
        cursor.close();
        return user;
    }

    public User getId(int id) {
        String query = MwSQLiteHelper.COLUMN_USER_ID + " = " + id ;
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_USER,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        User user = (User)cursorTo(cursor);
        cursor.close();
        return user;
    }


    @Override
    public List<Model> getAll(String query) {
        return null;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setGender(cursor.getInt(2));
        user.setHeight(cursor.getFloat(3));
        user.setWeight(cursor.getFloat(4));
        user.setFat(cursor.getFloat(5));
        user.setImg(cursor.getString(6));
        return user;
    }


    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_USER, null, null);
    }

}
