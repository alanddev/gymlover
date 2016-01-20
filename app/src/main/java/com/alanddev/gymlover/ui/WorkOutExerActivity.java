package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.ExerciseGrpAdapter;
import com.alanddev.gymlover.adapter.WorkoutExerAdapter;
import com.alanddev.gymlover.controller.WorkoutExerController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.ExcerciseGroup;
import com.alanddev.gymlover.model.WorkoutExerDay;
import com.alanddev.gymlover.model.WorkoutExerDetail;
import com.alanddev.gymlover.model.WorkoutExerWeek;
import com.alanddev.gymlover.util.Utils;
import com.foound.widget.AmazingAdapter;
import com.foound.widget.AmazingListView;

import java.util.ArrayList;
import java.util.List;

public class WorkOutExerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_work_out_exer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final int workoutId = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_WORKOUT_ID);
        final String workoutName = getIntent().getExtras().getString(MwSQLiteHelper.COLUMN_WORKOUT_NAME);
        int workoutWeek = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_WORKOUT_WEEK);
        getSupportActionBar().setTitle(workoutName);
        AmazingListView lvSetting = (AmazingListView)findViewById(R.id.lstworkoutexer);
        final List<WorkoutExerWeek> exerWeeks = getData(workoutId,workoutWeek);
        final WorkoutExerAdapter adapter = new WorkoutExerAdapter(this,getLayoutInflater(),exerWeeks);
        lvSetting.setAdapter(adapter);
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ExerciseWoActivity.class);
                AmazingAdapter adapter1 = (AmazingAdapter)parent.getAdapter();
                WorkoutExerDay exerDay = (WorkoutExerDay) adapter1.getItem(position);
                int section = adapter1.getSectionForPosition(position);
                WorkoutExerWeek exerWeek = exerWeeks.get(section);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID, workoutId);
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY, exerDay.getDay());
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK, exerWeek.getWeek());
                intent.putExtra(MwSQLiteHelper.COLUMN_WORKOUT_NAME, workoutName);
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

    private List<WorkoutExerWeek> getData(int workoutId,int workoutWeek){
        List<WorkoutExerWeek> weeks = new ArrayList<WorkoutExerWeek>();
        WorkoutExerController controller = new WorkoutExerController(this);
        controller.open();
        weeks = controller.getWorkoutExer(workoutId, workoutWeek);
        controller.close();
        return weeks;
    }

}
