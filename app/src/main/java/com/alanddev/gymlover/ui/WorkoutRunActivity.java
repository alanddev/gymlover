package com.alanddev.gymlover.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.util.Utils;

import java.util.ArrayList;

public class WorkoutRunActivity extends AppCompatActivity {

    Button btnstart, btnreset, btnext;
    TextView time;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int t = 1;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    Handler handler = new Handler();
    Handler handlerNextImg = new Handler();
    Handler handlerPrevImg = new Handler();


    private ImageView imgEx;
    private String[] imageArray;
    private int currentIndex;
    private int startIndex;
    private int endIndex;
    private ArrayList<Integer> listExercise;
    private int currentExercise;

    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);
//            time.setText("" + mins + ":" + String.format("%02d", secs) + ":"
//                    + String.format("%03d", milliseconds));
            time.setText("" + mins + ":" + String.format("%02d", secs));
            time.setTextColor(Color.RED);
            handler.postDelayed(this, 0);
        }};

    public Runnable nextImgTimer = new Runnable() {
        @Override
        public void run() {
            if (currentIndex > endIndex) {
                currentIndex--;
                previousImage();
            } else {
                nextImage();
            }
        }
    };


    public Runnable prevImgTimer = new Runnable() {
        @Override
        public void run() {
            if (currentIndex < startIndex) {
                currentIndex++;
                nextImage();
            } else {
                previousImage();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_run);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_workout_run));

        btnstart = (Button) findViewById(R.id.start);
        btnreset = (Button) findViewById(R.id.reset);
        btnext = (Button)findViewById(R.id.next);
        time = (TextView) findViewById(R.id.timer);

        currentExercise = 0;

        listExercise = new ArrayList<>();
        listExercise.add(1);


        final Exercise exercise = getData(2000);
        getSupportActionBar().setTitle(exercise.getName());
        String strImgs = exercise.getImage();
        imageArray = strImgs.split(",");
        imgEx = (ImageView)findViewById(R.id.imgex);
        startIndex = 0;
        endIndex = imageArray.length-1;
        nextImage();

    }

    private void initData(){

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



    public void nextImage() {
        imgEx.setImageResource(getResources().getIdentifier("ic_ex_" + imageArray[currentIndex], "mipmap", getPackageName()));
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgEx.startAnimation(rotateimage);
        currentIndex++;
        handlerNextImg.postDelayed(nextImgTimer, 1000);
    }

    public void previousImage() {
        imgEx.setImageResource(getResources().getIdentifier("ic_ex_"+imageArray[currentIndex],"mipmap",getPackageName()));
        Animation rotateimage = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imgEx.startAnimation(rotateimage);
        currentIndex--;
        handlerPrevImg.postDelayed(prevImgTimer, 1000);
    }


    private Exercise getData(int exId){
        ExcerciseController controller = new ExcerciseController(getApplicationContext());
        controller.open();
        Exercise excer = controller.getById(exId);
        controller.close();
        return excer;
    }


    public void onClickStart(View v){
        if (t == 1) {
            btnstart.setText("Pause");
            starttime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
            t = 0;
        } else {
            btnstart.setText("Start");
            time.setTextColor(Color.BLUE);
            timeSwapBuff += timeInMilliseconds;
            handler.removeCallbacks(updateTimer);
            t = 1;
        }
    }

    public void onClickReset(View v){
        starttime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedtime = 0L;
        t = 1;
        secs = 0;
        mins = 0;
        milliseconds = 0;
        btnstart.setText("Start");
        handler.removeCallbacks(updateTimer);
        time.setText("00:00");
        currentExercise = 0;
        btnext.setEnabled(true);
        reloadImage();

    }


    private void reloadImage(){
        final Exercise exercise = getData(listExercise.get(currentExercise));
        String strImgs = exercise.getImage();
        imageArray = strImgs.split(",");
        imgEx = (ImageView) findViewById(R.id.imgex);
        startIndex = 0;
        currentIndex = 0;
        endIndex = imageArray.length - 1;
        handlerNextImg.removeCallbacks(nextImgTimer);
        handlerPrevImg.removeCallbacks(prevImgTimer);
        nextImage();
        getSupportActionBar().setTitle(exercise.getName());
    }

    public void onClickNext(View v){
        if (currentExercise < listExercise.size()-1) {
            currentExercise++;
            reloadImage();
            if (currentExercise == listExercise.size() - 1){
                btnext.setEnabled(false);
            }
        }
    }


}
