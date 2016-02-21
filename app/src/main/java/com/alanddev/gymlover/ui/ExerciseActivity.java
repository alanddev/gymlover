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
import com.alanddev.gymlover.adapter.ExerciseAdapter;
import com.alanddev.gymlover.adapter.ExerciseGrpAdapter;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.controller.ExcerciseGroupController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;

import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private int isWorkout=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView lvSetting = (ListView)findViewById(R.id.lstExercise);
        int grpId = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_EXCERCISE_GRP_ID,0);
        final String grpName = getIntent().getExtras().getString(MwSQLiteHelper.COLUMN_EX_GROUP_NAME, "");
        isWorkout=getIntent().getExtras().getInt(Constant.TAKE_EXERCISE,0);
        getSupportActionBar().setTitle(grpName);
        ExerciseAdapter adapter = new ExerciseAdapter(this,getData(grpId));
        lvSetting.setAdapter(adapter);
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = (Exercise) parent.getAdapter().getItem(position);
                if(isWorkout==1){
                    Intent intent=new Intent();
                    intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_ID, exercise.getId());
                    intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_NAME, exercise.getName());
                    intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_CALO, exercise.getCalo());
                    intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_IMAGE, exercise.getImage());
                    setResult(Constant.PICK_EXERCISE, intent);
                    finish();//finishing activity
                }else {
                    Intent intent = new Intent(getApplicationContext(), ExerciseDetailActivity.class);
                    intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_ID, exercise.getId());
                    intent.putExtra(MwSQLiteHelper.COLUMN_EX_GROUP_NAME, grpName);
                    startActivity(intent);
                }
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

    private List<Model> getData(int grpId){
        ExcerciseController controller = new ExcerciseController(getApplicationContext());
        controller.open();
        List<Model> lstExer = controller.getByGroupId(grpId);
        controller.close();
        return lstExer;
    }

}
