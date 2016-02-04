package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.controller.ExcerciseGroupController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.ExcerciseGroup;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;

import java.util.List;

public class ExerciseGrpActivity extends AppCompatActivity {

    private int isWorkout=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_exercise_grp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_exercise_grp));
        if(getIntent().getExtras()!=null){
            isWorkout=getIntent().getExtras().getInt(Constant.TAKE_EXERCISE,0);
        }
        ListView lvSetting = (ListView)findViewById(R.id.lstExerciseGrp);
        final ExerciseGrpAdapter adapter = new ExerciseGrpAdapter(this,getData());
        lvSetting.setAdapter(adapter);
        lvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ExerciseActivity.class);
                ExcerciseGroup grp = (ExcerciseGroup)parent.getAdapter().getItem(position);
                intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_GRP_ID, grp.getId());
                intent.putExtra(MwSQLiteHelper.COLUMN_EX_GROUP_NAME, grp.getName());
                intent.putExtra(Constant.TAKE_EXERCISE, isWorkout);
                if(isWorkout==1){
                    startActivityForResult(intent, Constant.PICK_EXERCISE);
                }else{
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

    private List<Model> getData(){
        ExcerciseGroupController controller = new ExcerciseGroupController(getApplicationContext());
        controller.open();
        List<Model> lstGrp = controller.getAll();
        controller.close();
        return lstGrp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constant.PICK_EXERCISE)
        {
            if(data!=null) {
                Intent intent=new Intent();
                intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_ID, data.getIntExtra(MwSQLiteHelper.COLUMN_EXCERCISE_ID, 0));
                intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_NAME, data.getStringExtra(MwSQLiteHelper.COLUMN_EXCERCISE_NAME));
                intent.putExtra(MwSQLiteHelper.COLUMN_EXCERCISE_CALO, data.getFloatExtra(MwSQLiteHelper.COLUMN_EXCERCISE_CALO, 0.0f));
                setResult(Constant.PICK_EXERCISE, intent);
                finish();//finishing activity
            }
        }
    }

}
