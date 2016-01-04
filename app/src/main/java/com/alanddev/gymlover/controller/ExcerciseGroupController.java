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
public class ExcerciseGroupController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_EX_GROUP_ID,
            MwSQLiteHelper.COLUMN_EX_GROUP_NAME,
            MwSQLiteHelper.COLUMN_EX_GROUP_DESC,
            MwSQLiteHelper.COLUMN_EX_GROUP_IMAGE,
    };

    public ExcerciseGroupController(Context context){
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
        ExcerciseGroup grp  = (ExcerciseGroup)data;
        values.put(MwSQLiteHelper.COLUMN_EX_GROUP_ID, grp.getId());
        values.put(MwSQLiteHelper.COLUMN_EX_GROUP_NAME, grp.getName());
        values.put(MwSQLiteHelper.COLUMN_EX_GROUP_DESC, grp.getDescription());
        values.put(MwSQLiteHelper.COLUMN_EX_GROUP_IMAGE, grp.getImage());
        database.insert(MwSQLiteHelper.TABLE_EXCERCISE_GROUP, null,
                values);
        return grp;
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
        List<Model> excerciseGroups = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_EXCERCISE_GROUP,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ExcerciseGroup group = (ExcerciseGroup)cursorTo(cursor);
            excerciseGroups.add(group);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return excerciseGroups;
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
        ExcerciseGroup excerciseGroup = new ExcerciseGroup();
        try {
            excerciseGroup.setId(cursor.getInt(0));
            excerciseGroup.setName(cursor.getString(1));
            excerciseGroup.setDescription(cursor.getString(2));
            excerciseGroup.setImage(cursor.getString(3));
        }catch (Exception ex){
            //don't do anything
        }
        return excerciseGroup;
    }

    @Override
    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_EXCERCISE_GROUP, null, null);
    }

    public void init(){
        String[] arrayExCateName = mContext.getResources().getStringArray(R.array.excercise_grp_names);
        String[] arrayExCateImg = mContext.getResources().getStringArray(R.array.excercise_grp_imgs);
        int i = Math.min(arrayExCateName.length, arrayExCateImg.length);
        List<ExcerciseGroup> lstExGrp = new ArrayList<ExcerciseGroup>();
        for(int j=0;j<i;j++){
            ExcerciseGroup exgroup = new ExcerciseGroup();
            exgroup.setId(j + 1);
            exgroup.setName(arrayExCateName[j]);
            exgroup.setImage(arrayExCateImg[j]);
            lstExGrp.add(exgroup);
        }
        //add data
        for(int j=0;j<lstExGrp.size();j++){
            create(lstExGrp.get(j));
        }
    }
}
