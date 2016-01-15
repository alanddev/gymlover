package com.alanddev.gymlover.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.WorkoutExerAdapter;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.WorkoutExerWeek;
import com.alanddev.gymlover.util.Utils;
import com.foound.widget.AmazingListView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutExerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_work_out_exer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int woId = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_WORKOUT_ID,0);
        final String woName = getIntent().getExtras().getString(MwSQLiteHelper.COLUMN_WORKOUT_NAME, "");
        getSupportActionBar().setTitle(woName);
        AmazingListView lstworkoutexer = (AmazingListView)findViewById(R.id.lstworkoutexer);
        WorkoutExerAdapter adapter = new WorkoutExerAdapter(getApplicationContext(),getLayoutInflater(),getData(woId));
        lstworkoutexer.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout, menu);
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

    List<WorkoutExerWeek> getData(int workoutId){
        List<WorkoutExerWeek> lstwes = new ArrayList<WorkoutExerWeek>();
        return lstwes;
    }

}
