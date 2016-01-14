package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.User;
import com.alanddev.gymlover.model.WorkoutExerDetail;

import java.util.List;

/**
 * Created by ANLD on 06/01/2016.
 */
public class WorkoutExerController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_ID,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_DESC,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_SET,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_REPEAT,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEIGHT,
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_TIME
    };

    public WorkoutExerController(Context context){
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
        WorkoutExerDetail workout  = (WorkoutExerDetail)data;
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID, workout.getWorkid());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID, workout.getExerid());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DESC, workout.getDesc());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY, workout.getDay());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_SET, workout.getSet());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_REPEAT, workout.getRepeat());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEIGHT, workout.getWeight());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_TIME, workout.getTime());
        database.insert(MwSQLiteHelper.TABLE_WORKOUT_EXER, null,
                values);
        return workout;
    }

    @Override
    public void update(Model data) {

    }

    public void updateTime(float time,int workoutId, int exerciseId){


        String updateQuery = "UPDATE " + MwSQLiteHelper.TABLE_WORKOUT_EXER + " SET " + MwSQLiteHelper.COLUMN_WORKOUT_EXER_TIME + "=" + time +" WHERE " +
                MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID + "=" +workoutId + " AND " + MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID + "=" + exerciseId;
        try {
            database.execSQL(updateQuery);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getCount() {
        return 0;
    }


    public WorkoutExerDetail getId(int id) {
        String query = MwSQLiteHelper.COLUMN_WORKOUT_EXER_ID + " = " + id ;
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_WORKOUT_EXER,
                allColumns, query, null,
                null, null, null);
        cursor.moveToFirst();
        WorkoutExerDetail workoutExerDetail = (WorkoutExerDetail)cursorTo(cursor);
        cursor.close();
        return workoutExerDetail;
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
        WorkoutExerDetail workout = new WorkoutExerDetail();
        try {
            workout.setId(cursor.getInt(0));
            workout.setWorkid(cursor.getInt(1));
            workout.setExerid(cursor.getInt(2));
            workout.setDesc(cursor.getString(3));
            workout.setDay(cursor.getInt(4));
            workout.setSet(cursor.getInt(5));
            workout.setRepeat(cursor.getInt(6));
            workout.setWeight(cursor.getFloat(7));
            workout.setTime(cursor.getFloat(8));
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
