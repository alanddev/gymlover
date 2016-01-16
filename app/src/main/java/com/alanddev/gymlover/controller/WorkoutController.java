package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.ExcerciseGroup;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.model.WorkoutExerDetail;

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


        String[] arrayWOName = mContext.getResources().getStringArray(R.array.wo_names);
        String[] arrayWOImg = mContext.getResources().getStringArray(R.array.wo_images);
        String[] arrayWODesc = mContext.getResources().getStringArray(R.array.wo_descs);
        String[] arrayWOWeek = mContext.getResources().getStringArray(R.array.wo_weeks);

//        WorkoutExerController workoutExerController = new WorkoutExerController(mContext);
//        TypedArray ta = mContext.getResources().obtainTypedArray(R.array.wo_exer);
//        int length = ta.length();
//        String[][] arrayWOExericise = new String[length][];
//        for (int i = 0; i < length; ++i) {
//            int id = ta.getResourceId(i, 0);
//            if (id > 0) {
//                arrayWOExericise[i] =  mContext.getResources().getStringArray(id);
//            } else {
//                // something wrong with the XML
//            }
//        }


        for(int j=0;j<arrayWOName.length;j++){
            Workout workout = new Workout();
            workout.setId(j + 1);
            workout.setName(arrayWOName[j]);
            workout.setImage(arrayWOImg[j]);
            workout.setDesc(arrayWODesc[j]);
            workout.setWeek(Integer.valueOf(arrayWOWeek[j]));
            workout.setUses(0);
            create(workout);
            //test(workout);

        }
    }

    /*private void test(Workout workout){
        WorkoutExerController workoutExerController = new WorkoutExerController(mContext);
        workoutExerController.open();
        WorkoutExerDetail workoutExerDetail = new WorkoutExerDetail(workout.getId(),7,"Stronglift 5*5",1,5,5,"10",20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),6,"Stronglift 5*5",1,5,5,10,20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),8,"Stronglift 5*5",1,5,5,10,20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),4,"Stronglift 5*5",1,5,5,10,20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),3,"Stronglift 5*5",1,5,5,10,20);
        workoutExerController.create(workoutExerDetail);

        workoutExerDetail = new WorkoutExerDetail(workout.getId(),7,"Stronglift 5*5",2,5,5,12,20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),6,"Stronglift 5*5",2,5,5,12,20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),8,"Stronglift 5*5",2,5,5,12,20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),4,"Stronglift 5*5",2,5,5,12,20);
        workoutExerController.create(workoutExerDetail);
        workoutExerDetail = new WorkoutExerDetail(workout.getId(),3,"Stronglift 5*5",2,5,5,12,20);
        workoutExerController.create(workoutExerDetail);


    }
*/

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
