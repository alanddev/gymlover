package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.ExerciseWoAdapter;
import com.alanddev.gymlover.controller.WorkoutExerController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.WorkoutExerDetail;
import com.alanddev.gymlover.util.Utils;

import java.util.List;

public class ExerciseWoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_exercise_wo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final int workoutId = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID, 0);
        final String workoutName = getIntent().getExtras().getString(MwSQLiteHelper.COLUMN_WORKOUT_NAME, "");
        final int day = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY, 0);
        final int week = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK, 0);
        getSupportActionBar().setTitle(workoutName + ", "+ getResources().getString(R.string.week)+" "+week + ", "+ getResources().getString(R.string.day)+" "+day);
        ListView lvSetting = (ListView)findViewById(R.id.lstExerciseWo);
        ExerciseWoAdapter adapter = new ExerciseWoAdapter(this,getData(workoutId,day,week));
        lvSetting.setAdapter(adapter);
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), WorkoutRunActivity.class);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID, (int) parent.getAdapter().getItemId(position));
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID,workoutId);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY, day);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK, week);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkoutRunActivity.class);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID,workoutId);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY, day);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK, week);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<WorkoutExerDetail> getData(int woid,int day,int week){
        WorkoutExerController controller = new WorkoutExerController(this);
        controller.open();
        List<WorkoutExerDetail> details = controller.getExercisebyWD(woid, day, week);
        controller.close();
        return details;
    }



}
