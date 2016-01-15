package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alanddev.gymlover.R;
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

    public List<Model> getByGroupId(int grpId){
        List<Model> excercises = new ArrayList<Model>();
        StringBuffer sql = new StringBuffer("SELECT * FROM ").
                append(MwSQLiteHelper.TABLE_EXCERCISE).append(" WHERE ").append(MwSQLiteHelper.COLUMN_EXCERCISE_GRP_ID)
                .append("= ?");
        String[] atts = new String[]{grpId+""};
        Cursor cursor = database.rawQuery(sql.toString(),atts);
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

    public Exercise getById(int exId){
        StringBuffer sql = new StringBuffer("SELECT * FROM ").
                append(MwSQLiteHelper.TABLE_EXCERCISE).append(" WHERE ").append(MwSQLiteHelper.COLUMN_EXCERCISE_ID)
                .append("= ?");
        String[] atts = new String[]{exId+""};
        Cursor cursor = database.rawQuery(sql.toString(),atts);
        cursor.moveToFirst();
        Exercise exercise = (Exercise)cursorTo(cursor);
        cursor.close();
        return exercise;
    }

    public void init(){
        String[] arrayExgrp = mContext.getResources().getStringArray(R.array.excercise_grp_ex) ;
        List<Exercise> lstExercises = new ArrayList<Exercise>();
        for(int i=0;i<arrayExgrp.length;i++){
            String temp = arrayExgrp[i];
            String[] itemTemp = temp.split(",");
            String[] arrayExName = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[0], "array", mContext.getPackageName()));
            String[] arrayExDesc = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[1],"array",mContext.getPackageName()));
            String[] arrayExImage = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[2],"array",mContext.getPackageName()));
            String[] arrayVideo = mContext.getResources().getStringArray(mContext.getResources().getIdentifier(itemTemp[3],"array",mContext.getPackageName()));
            for(int j=0;j<arrayExName.length;j++){
                Exercise exercise = new Exercise();
                exercise.setId((i+1)*1000+j);
                exercise.setName(arrayExName[j]);
                exercise.setDescription(arrayExDesc[j]);
                exercise.setImage(arrayExImage[j]);
                exercise.setVideolink(arrayVideo[j]);
                exercise.setExgroup_id(i + 1);
                lstExercises.add(exercise);
            }
        }

        //create
        for(int i=0;i<lstExercises.size();i++){
            create(lstExercises.get(i));
        }
    }
}
