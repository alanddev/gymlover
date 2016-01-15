package com.alanddev.gymlover.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.WorkoutAdapter;
import com.alanddev.gymlover.adapter.WorkoutSettingAdapter;
import com.alanddev.gymlover.controller.WorkoutExerController;
import com.alanddev.gymlover.model.WorkoutExerDetail;

import java.util.ArrayList;

public class WorkoutSettingActivity extends AppCompatActivity {

    private WorkoutExerController workoutExerController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_workout_setting));

        workoutExerController = new WorkoutExerController(this);
        reloadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    public void reloadData(){
        workoutExerController.open();
        ArrayList<WorkoutExerDetail> listWorkout = new ArrayList<>();
        listWorkout.add(workoutExerController.getId(1));
        listWorkout.add(workoutExerController.getId(2));
        listWorkout.add(workoutExerController.getId(3));
        listWorkout.add(workoutExerController.getId(4));
        listWorkout.add(workoutExerController.getId(5));

        ListView listViewWorkout = (ListView)findViewById(R.id.list_workout_detail);
        listViewWorkout.setAdapter(new WorkoutSettingAdapter(this,listWorkout));
        workoutExerController.close();

    }

}
