package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.User;
import com.alanddev.gymlover.model.WorkoutExerDay;
import com.alanddev.gymlover.model.WorkoutExerDetail;
import com.alanddev.gymlover.model.WorkoutExerWeek;

import java.util.ArrayList;
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
            MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK,
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
        values.put(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK, workout.getWeek());
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


    public float getTime(int workoutId, int exerciseId){
        StringBuffer sql = new StringBuffer("SELECT ").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_TIME).append(" FROM ").
                append(MwSQLiteHelper.TABLE_WORKOUT_EXER).append(" WHERE ").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID)
                .append("= ?").append(" AND ").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID).append("= ?");
        String[] atts = new String[]{String.valueOf(workoutId),String.valueOf(exerciseId)};
        Cursor cursor = database.rawQuery(sql.toString(),atts);
        cursor.moveToFirst();
        return cursor.getFloat(0);
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
            workout.setWeek(cursor.getInt(3));
            workout.setDay(cursor.getInt(4));
            workout.setDesc(cursor.getString(5));
            workout.setSet(cursor.getInt(6));
            workout.setRepeat(cursor.getString(7));
            workout.setWeight(cursor.getFloat(8));
            workout.setTime(cursor.getFloat(9));
        }catch (Exception ex){
            //don't do anything
            ex.printStackTrace();
        }
        return workout;
    }

    @Override
    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_WORKOUT, null, null);
    }

    public List<WorkoutExerWeek> getWorkoutExer(int workoutId,int numweek){
        List<WorkoutExerWeek> weeks = new ArrayList<WorkoutExerWeek>();
        for(int i=1;i<=numweek;i++){
            WorkoutExerWeek week = new WorkoutExerWeek();
            StringBuffer sql = new StringBuffer("SELECT * FROM ").
                    append(MwSQLiteHelper.TABLE_WORKOUT_EXER).append(" WHERE ").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID)
                    .append("= ?").append(" AND ").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK).append("= ?");
            String[] atts = new String[]{String.valueOf(workoutId),String.valueOf(i)};
            Cursor cursor = database.rawQuery(sql.toString(),atts);
            cursor.moveToFirst();
            List<WorkoutExerDetail> workoutExerDetails = new ArrayList<WorkoutExerDetail>();
            int number_day=1;
            while (!cursor.isAfterLast()) {
                WorkoutExerDetail workoutExerDetail = (WorkoutExerDetail)cursorTo(cursor);
                workoutExerDetails.add(workoutExerDetail);
                number_day=workoutExerDetail.getDay();
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();

            //add workoutday, workoutweek
            List<WorkoutExerDay> exerDays = new ArrayList<WorkoutExerDay>();
            for (int j=1;j<=number_day;j++){
                WorkoutExerDay workoutExerDay = new WorkoutExerDay();
                List<WorkoutExerDetail> details = new ArrayList<WorkoutExerDetail>();
                for(int k=0;k<workoutExerDetails.size();k++){
                    WorkoutExerDetail temp = workoutExerDetails.get(k);
                    if(temp.getDay()==j){
                        details.add(temp);
                    }
                }
                workoutExerDay.setDay(j);
                workoutExerDay.setItems(details);
                exerDays.add(workoutExerDay);
            }
            week.setWeek(i);
            week.setItems(exerDays);
            weeks.add(week);
        }
        return weeks;
    }

    public List<WorkoutExerDetail> getExercisebyWD(int workoutId,int day,int week){
        StringBuffer sql = new StringBuffer("SELECT e.").append(MwSQLiteHelper.COLUMN_EXCERCISE_ID).append(",e.").
                append(MwSQLiteHelper.COLUMN_EXCERCISE_IMAGE).append(",e.").append(MwSQLiteHelper.COLUMN_EXCERCISE_NAME).
                append(",we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_REPEAT).
                append(",we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEIGHT).
                append(",we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_TIME).
                append(",we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY).
                append(",we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_SET).
                append(" FROM ").append(MwSQLiteHelper.TABLE_WORKOUT_EXER).
                append(" we inner join ").append(MwSQLiteHelper.TABLE_EXCERCISE).append(" e ON we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID).
                append(" = e.").append(MwSQLiteHelper.COLUMN_EXCERCISE_ID).append(" WHERE we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID)
                .append("= ?").append(" AND we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY).append("= ?").
                        append(" AND we.").append(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK).append("= ?");
        String[] atts = new String[]{String.valueOf(workoutId),String.valueOf(day),String.valueOf(week)};
        Cursor cursor = database.rawQuery(sql.toString(),atts);
        cursor.moveToFirst();
        List<WorkoutExerDetail> workoutExerDetails = new ArrayList<WorkoutExerDetail>();
        while (!cursor.isAfterLast()) {
            WorkoutExerDetail detail = new WorkoutExerDetail();
            detail.setExerid(cursor.getInt(0));
            detail.setExerimg(cursor.getString(1));
            detail.setExername(cursor.getString(2));
            detail.setRepeat(cursor.getString(3));
            detail.setWeight(cursor.getFloat(4));
            detail.setTime(cursor.getFloat(5));
            detail.setDay(cursor.getInt(6));
            detail.setSet(cursor.getInt(7));
            workoutExerDetails.add(detail);
            cursor.moveToNext();
        }
        return workoutExerDetails;
    }

    public void init(){
        String[] arrayExgrp = mContext.getResources().getStringArray(R.array.wo_exers) ;
        for(int i=0;i<arrayExgrp.length;i++){
            String temp = arrayExgrp[i];
            String[] itemTemp = temp.split(",");
            String[] arrayWoExWeek = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[0], "array", mContext.getPackageName()));
            String[] arrayWoExDay = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[1],"array",mContext.getPackageName()));
            String[] arrayWoEx = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[2],"array",mContext.getPackageName()));
            String[] arrayWoSet = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[3], "array", mContext.getPackageName()));
            String[] arrayWoRepeat = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[4], "array", mContext.getPackageName()));
            String[] arrayWoExWeight = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[5], "array", mContext.getPackageName()));
            String[] arrayWoExTimes = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[6], "array", mContext.getPackageName()));
            for(int j=0;j<arrayWoExWeek.length;j++){
                WorkoutExerDetail detail = new WorkoutExerDetail();
                detail.setWorkid(i+1);
                detail.setExerid(Integer.valueOf(arrayWoEx[j]));
                detail.setWeek(Integer.valueOf(arrayWoExWeek[j]));
                detail.setDay(Integer.valueOf(arrayWoExDay[j]));
                detail.setDay(Integer.valueOf(arrayWoExDay[j]));
                detail.setSet(Integer.valueOf(arrayWoSet[j]));
                detail.setRepeat(arrayWoRepeat[j]);
                detail.setWeight(Float.valueOf(arrayWoExWeight[j]));
                detail.setTime(Float.valueOf(arrayWoExTimes[j]));
                create(detail);
            }
        }
    }
}
