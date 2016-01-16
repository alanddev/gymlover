package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.util.Utils;
import com.google.android.gms.games.GamesMetadata;

import java.util.List;

public class ExerciseDetailActivity extends AppCompatActivity {
    private ImageView imgEx;
    private String[] imageArray;
    private int currentIndex;
    private int startIndex;
    private int endIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_exercise_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int exId = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_EXCERCISE_ID, 0);
        final Exercise exercise = getData(exId);
        getSupportActionBar().setTitle(exercise.getName());
        String strImgs = exercise.getImage();
        imageArray = strImgs.split(",");
        imgEx = (ImageView)findViewById(R.id.imgex);
        startIndex = 0;
        endIndex = imageArray.length-1;
        nextImage();
        TextView txtDesc = (TextView)findViewById(R.id.txtDesc);
        txtDesc.setText(exercise.getDescription());
        RelativeLayout lay_video = (RelativeLayout)findViewById(R.id.lay_video);
        lay_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(exercise.getVideolink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setDataAndType(uri, "video/mp4");
                startActivity(intent);
            }
        });
    }

    public void nextImage() {
        imgEx.setImageResource(getResources().getIdentifier("ic_ex_"+imageArray[currentIndex],"mipmap",getPackageName()));
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgEx.startAnimation(rotateimage);
        currentIndex++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex > endIndex) {
                    currentIndex--;
                    previousImage();
                } else {
                    nextImage();
                }

            }
        }, 1000);
    }

    public void previousImage() {
        imgEx.setImageResource(getResources().getIdentifier("ic_ex_"+imageArray[currentIndex],"mipmap",getPackageName()));
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgEx.startAnimation(rotateimage);
        currentIndex--;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex < startIndex) {
                    currentIndex++;
                    nextImage();
                } else {
                    previousImage();
                }
            }
        }, 1000);
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

    private Exercise getData(int exId){
        ExcerciseController controller = new ExcerciseController(getApplicationContext());
        controller.open();
        Exercise excer = controller.getById(exId);
        controller.close();
        return excer;
    }

}
