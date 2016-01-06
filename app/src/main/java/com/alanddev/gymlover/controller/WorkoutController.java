package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.ExcerciseGroup;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Workout;

import java.util.List;

/**
 * Created by ANLD on 06/01/2016.
 */
public class WorkoutController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_WORKOUT_ID,
            MwSQLiteHelper.COLUMN_WORKOUT_NAME,
            MwSQLiteHelper.COLUMN_WORKOUT_DESC,
            MwSQLiteHelper.COLUMN_WORKOUT_IMAGE,
    };

    public WorkoutController(Context context){
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
        Workout workout  = (Workout)data;
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_ID, workout.getId());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_NAME, workout.getName());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_DESC, workout.getDesc());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_IMAGE, workout.getImage());
        database.insert(MwSQLiteHelper.TABLE_WORKOUT, null,
                values);
        return workout;
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
        return null;
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
        Workout workout = new Workout();
        try {
            workout.setId(cursor.getInt(0));
            workout.setName(cursor.getString(1));
            workout.setDesc(cursor.getString(2));
            workout.setImage(cursor.getString(3));
        }catch (Exception ex){
            //don't do anything
        }
        return workout;
    }

    @Override
    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_WORKOUT, null, null);
    }
}
