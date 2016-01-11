package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.History;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by td.long on 11/19/2015.
 */
public class HistoryController implements IDataSource {

    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_HISTORY_ID,
            MwSQLiteHelper.COLUMN_HISTORY_USER,
            MwSQLiteHelper.COLUMN_HISTORY_HEIGHT,
            MwSQLiteHelper.COLUMN_HISTORY_WEIGHT,
            MwSQLiteHelper.COLUMN_HISTORY_FAT,
            MwSQLiteHelper.COLUMN_HISTORY_DATE
    };

    public HistoryController(Context context){
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
        History history   = (History)data;
        values.put(MwSQLiteHelper.COLUMN_HISTORY_USER, history.getUser_id());
        values.put(MwSQLiteHelper.COLUMN_HISTORY_HEIGHT, history.getHeight());
        values.put(MwSQLiteHelper.COLUMN_HISTORY_WEIGHT, history.getWeight());
        values.put(MwSQLiteHelper.COLUMN_HISTORY_FAT, history.getFat());
        database.insert(MwSQLiteHelper.TABLE_HISTORY, null,
                values);
        return history;
    }

    @Override
    public void update(Model data) {
        History history   = (History)data;
        ContentValues values = new ContentValues();
        values.put(MwSQLiteHelper.COLUMN_HISTORY_USER, history.getUser_id());
        values.put(MwSQLiteHelper.COLUMN_HISTORY_HEIGHT, history.getHeight());
        values.put(MwSQLiteHelper.COLUMN_HISTORY_WEIGHT, history.getWeight());
        values.put(MwSQLiteHelper.COLUMN_HISTORY_FAT, history.getFat());


        try {
            // updating row
            database.update(MwSQLiteHelper.TABLE_HISTORY, values, MwSQLiteHelper.COLUMN_HISTORY_ID + " = ?",
                    new String[]{String.valueOf(history.getId())});
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        String countQuery = "SELECT  * FROM " + MwSQLiteHelper.TABLE_HISTORY;
        Cursor cursor = database.rawQuery(countQuery, null);
        int count  = cursor.getCount();
        cursor.close();
        return count;

    }
    @Override
    public List<Model> getAll() {
        List<Model> hists = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_HISTORY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            History hist = (History)cursorTo(cursor);
            hists.add(hist);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return hists;
    }

    @Override
    public Model get(String query) {
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_HISTORY,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        History hist = (History)cursorTo(cursor);
        cursor.close();
        return hist;
    }



    public History getId(int id) {
        String query = MwSQLiteHelper.COLUMN_HISTORY_ID + " = " + id ;
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_HISTORY,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        History history = (History)cursorTo(cursor);
        cursor.close();
        return history;
    }


    @Override
    public List<Model> getAll(String query) {
        return null;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        History history = new History();
        history.setId(cursor.getInt(0));
        history.setUser_id(cursor.getInt(1));
        history.setHeight(cursor.getFloat(2));
        history.setWeight(cursor.getFloat(3));
        history.setFat(cursor.getFloat(4));
        history.setDate(cursor.getString(6));
        return history;
    }


    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_HISTORY, null, null);
    }

}
