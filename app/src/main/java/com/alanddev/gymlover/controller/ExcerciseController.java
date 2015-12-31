package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.ExcerciseGroup;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANLD on 30/12/2015.
 */
public class ExcerciseController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_EXCERCISE_ID,
            MwSQLiteHelper.COLUMN_EXCERCISE_NAME,
            MwSQLiteHelper.COLUMN_EXCERCISE_DESC,
            MwSQLiteHelper.COLUMN_EXCERCISE_IMAGE,
            MwSQLiteHelper.COLUMN_EXCERCISE_VIDEOLINK,
            MwSQLiteHelper.COLUMN_EXCERCISE_GRP_ID
    };

    public ExcerciseController(Context context){
        dbHelper = new MwSQLiteHelper(context);
        this.mContext = context;
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
        Exercise exercise  = (Exercise)data;
        values.put(MwSQLiteHelper.COLUMN_EXCERCISE_ID, exercise.getId());
        values.put(MwSQLiteHelper.COLUMN_EXCERCISE_NAME, exercise.getName());
        values.put(MwSQLiteHelper.COLUMN_EXCERCISE_DESC, exercise.getDescription());
        values.put(MwSQLiteHelper.COLUMN_EXCERCISE_IMAGE, exercise.getImage());
        values.put(MwSQLiteHelper.COLUMN_EXCERCISE_VIDEOLINK, exercise.getVideolink());
        values.put(MwSQLiteHelper.COLUMN_EXCERCISE_GRP_ID, exercise.getExgroup_id());
        database.insert(MwSQLiteHelper.TABLE_EXCERCISE, null,
                values);
        return exercise;
    }

    @Override
    public void update(Model data) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<Model> getAll() {
        ArrayList<Model> excercises = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_EXCERCISE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Exercise exercise = (Exercise)cursorTo(cursor);
            excercises.add(exercise);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return excercises;
    }

    @Override
    public Model get(String query) {
        return null;
    }

    @Override
    public List<Model> getAll(String query) {
        return null;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        Exercise exercise = new Exercise();
        try {
            exercise.setId(cursor.getInt(0));
            exercise.setName(cursor.getString(1));
            exercise.setDescription(cursor.getString(2));
            exercise.setImage(cursor.getString(3));
            exercise.setVideolink(cursor.getString(4));
            exercise.setExgroup_id(cursor.getInt(5));
        }catch (Exception ex){
            //don't do anything
        }
        return exercise;
    }

    @Override
    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_EXCERCISE, null, null);
    }
}
