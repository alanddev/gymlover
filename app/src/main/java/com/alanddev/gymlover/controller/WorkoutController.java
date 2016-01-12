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

import java.util.ArrayList;
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
            MwSQLiteHelper.COLUMN_WORKOUT_USES,
            MwSQLiteHelper.COLUMN_WORKOUT_WEEK
    };

    public WorkoutController(Context context){
        dbHelper = new MwSQLiteHelper(context);
        this.mContext = context;
    }


    public  void init(){
        Workout workout = new Workout("StrongLift 5*5","1", "StrongLift for beginner",12);
        create(workout);
        Workout workout2 = new Workout("StrongLift 5*5 2 ","1", "StrongLift for beginner",4);
        create(workout2);
        Workout workout3 = new Workout("StrongLift 5*5 3 ","1", "StrongLift for beginner",8);
        create(workout3);
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
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_NAME, workout.getName());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_DESC, workout.getDesc());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_IMAGE, workout.getImage());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_USES, workout.getUses());
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_WEEK, workout.getWeek());
        database.insert(MwSQLiteHelper.TABLE_WORKOUT, null,
                values);
        return workout;
    }

    @Override
    public void update(Model data) {

    }



    public ArrayList<Workout> getWorkoutStatus(int status){
        StringBuffer sql = new StringBuffer("SELECT * from " + dbHelper.TABLE_WORKOUT + " where " + dbHelper.COLUMN_WORKOUT_USES +" = " + status);
        Cursor cursor = database.rawQuery(sql.toString(), null);

        cursor.moveToFirst();
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        while (!cursor.isAfterLast()) {
            Workout workout = new Workout();
            workout.setId(cursor.getInt(0));
            workout.setName(cursor.getString(1));
            workout.setImage(cursor.getString(3));
            workout.setWeek(cursor.getInt(5));
            workouts.add(workout);
            cursor.moveToNext();
        }


        return workouts;
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
            workout.setUses(cursor.getInt(4));
            workout.setWeek(cursor.getInt(5));
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
