package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.History;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.TransactionSumGroup;
import com.alanddev.gymlover.model.User;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        values.put(MwSQLiteHelper.COLUMN_HISTORY_DATE,Utils.changeDate2Str(new Date()));
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
    public List<Model>getAll(){
        return null;
    }


    public ArrayList<History> getAllHist() {
        StringBuffer sql = new StringBuffer("SELECT h." +dbHelper.COLUMN_HISTORY_FAT +",h."+ dbHelper.COLUMN_HISTORY_WEIGHT +
                ", h."+ dbHelper.COLUMN_HISTORY_HEIGHT + ",h."+dbHelper.COLUMN_HISTORY_DATE+
                " FROM " + dbHelper.TABLE_HISTORY  + " as h");

        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        ArrayList<History> histories = new ArrayList<History>();
        while (!cursor.isAfterLast()) {
            History history = new History();
            history.setFat(cursor.getFloat(0));
            history.setWeight(cursor.getFloat(1));
            history.setHeight(cursor.getFloat(2));
            history.setDate(cursor.getString(3));
            histories.add(history);
            cursor.moveToNext();
        }

        return histories;
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


    public boolean checkDate(Date date){
        String sql = "SELECT  * FROM " + dbHelper.TABLE_HISTORY + " where date = '" + Utils.changeDate2Str(new Date()) +"'";
        Cursor cursor = database.rawQuery(sql, null);
        int cnt = cursor.getCount();
        cursor.close();

        if(cursor.getCount() <=0){
            return false;
        }
        else{
            //cursor.close();
            return true;
        }
    }

    public ArrayList<History> getBodyIndexByDate(Date date){
        String sDateStart =  Utils.changeDate2Str(date);


        StringBuffer sql = new StringBuffer("SELECT h." +dbHelper.COLUMN_HISTORY_FAT +",h."+ dbHelper.COLUMN_HISTORY_WEIGHT +
                ", h."+ dbHelper.COLUMN_HISTORY_HEIGHT + ",h."+dbHelper.COLUMN_HISTORY_DATE+
                " FROM " + dbHelper.TABLE_HISTORY  + " as h where h."
                + dbHelper.COLUMN_HISTORY_DATE + " = '"+ sDateStart +"'");

        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        ArrayList<History> histories = new ArrayList<History>();

        while (!cursor.isAfterLast()) {
            History history = new History();
            history.setFat(cursor.getFloat(0));
            history.setWeight(cursor.getFloat(1));
            history.setHeight(cursor.getFloat(2));
            history.setDate(cursor.getString(3));
            histories.add(history);
            cursor.moveToNext();
        }

        return histories;
    }





    public ArrayList<History> getBodyIndexByDate(ArrayList<Date> dates){
        Date dateStart = dates.get(0);
        Date dateEnd = dates.get(1);
        String sDateStart =  new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
        String sDateEnd =  new SimpleDateFormat("yyyy-MM-dd").format(dateEnd);


        StringBuffer sql = new StringBuffer("SELECT h." +dbHelper.COLUMN_HISTORY_FAT +",h."+ dbHelper.COLUMN_HISTORY_WEIGHT +
                ", h."+ dbHelper.COLUMN_HISTORY_HEIGHT + ",h."+dbHelper.COLUMN_HISTORY_DATE+
                " FROM " + dbHelper.TABLE_HISTORY  + " as h where h."
                + dbHelper.COLUMN_HISTORY_DATE + " >= '"+ sDateStart +"' and h." + dbHelper.COLUMN_HISTORY_DATE +"<='"+sDateEnd +"'");

        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        ArrayList<History> histories = new ArrayList<History>();
        while (!cursor.isAfterLast()) {
            History history = new History();
            history.setFat(cursor.getFloat(0));
            history.setWeight(cursor.getFloat(1));
            history.setHeight(cursor.getFloat(2));
            history.setDate(cursor.getString(3));
            histories.add(history);
            cursor.moveToNext();
        }

        return histories;
    }

    public ArrayList<History>getBodyIndex(Date date, int type){
        ArrayList<History> trans = new ArrayList<History>();
        switch (type){
            case Constant.VIEW_TYPE_DAY:
                trans = getBodyIndexByWeek(date);
                break;
            case Constant.VIEW_TYPE_WEEK:
                trans =getBodyIndexByWeek(date);
                break;
            case Constant.VIEW_TYPE_MONTH:
                trans=getBodyIndexByMonth(date);
                break;
            case Constant.VIEW_TYPE_YEAR:
                trans=getBodyIndexByYear(date);
                break;

        }
        return trans;
    }

    public ArrayList<History> getBodyIndexByWeek(Date date){
        ArrayList<History> histories = new ArrayList<History>();
        ArrayList<Date> dates = Utils.getDateOfWeek(date);
        histories = getBodyIndexByDate(dates);
        return histories;
    }


    public ArrayList<History> getBodyIndexByMonth(Date date){
        ArrayList<History> histories = new ArrayList<History>();
        ArrayList<Date> dates = Utils.getDateOfMonth(date);
        histories = getBodyIndexByDate(dates);
        return histories;
    }

    public ArrayList<History> getBodyIndexByYear(Date date){
        ArrayList<History> histories = new ArrayList<History>();
        String year = Utils.getYear(date);
        ArrayList<Date> dates = Utils.getDateOfMonths(1, 12, year);
        histories = getBodyIndexByDate(dates);
        return histories;
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
