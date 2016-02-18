package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
import android.widget.ListView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.TransactionWoAdapter;
import com.alanddev.gymlover.adapter.WorkoutAdapter;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.controller.TransactionController;
import com.alanddev.gymlover.controller.WorkoutExerController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.model.WorkoutExerDetail;
import com.alanddev.gymlover.util.Utils;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class WorkoutRunActivity extends AppCompatActivity {

    Button btnstart, btnreset, btnext;
    TextView time;
    TextView timeTotal;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    long totalTime = 0L;
    long timeInMillisecondsTotal = 0L;
    long timeSwapBuffTotal = 0L;
    long updatedtimeTotal = 0L;

    int t = 1;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    int secsTotal = 0;
    int minsTotal = 0;
    int millisecondsTotal = 0;


    Handler handler = new Handler();
    Handler handlerTotalTime = new Handler();
    Handler handlerNextImg = new Handler();
    Handler handlerPrevImg = new Handler();



    private ImageView imgEx;
    private String[] imageArray;
    private int currentIndex;
    private int startIndex;

    private int endIndex;
    private List<WorkoutExerDetail> listExercise;
    private int currentExercise;
    private ArrayList<Transaction> transactions;
    private int day;
    private int week;
    private int exerId;
    private int workId;
    private float timeRunAuto;
    ListView listWorkout;

    private boolean autoRun = false;

    WorkoutExerController workoutExerController;
    TransactionController transactionController;


    public Runnable updateTimer = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);
            time.setText("" + String.format("%02d", mins) + ":" + String.format("%02d", secs));
            time.setTextColor(Color.RED);

            if (autoRun) {
                if (updatedtime <= (timeRunAuto + 1)* 1000) {
                    handler.postDelayed(this, 0);
                }else{
                    if (currentExercise < listExercise.size()-1) {
                        currentExercise++;
                        resetTime();
                        handler.postDelayed(updateTimer, 0);
                        reloadImage();
                    }else{
                        firework(findViewById(R.id.subTimer));
                    }
                }
            }else{
                handler.postDelayed(this, 0);
            }
        }};


    public Runnable updateTimerTotal = new Runnable() {
        public void run() {
            timeInMillisecondsTotal = SystemClock.uptimeMillis() - totalTime;
            updatedtimeTotal = timeSwapBuffTotal + timeInMillisecondsTotal;
            secsTotal = (int) (updatedtimeTotal / 1000);
            minsTotal = secsTotal / 60;
            secsTotal= secsTotal % 60;
            millisecondsTotal = (int) (updatedtimeTotal % 1000);
            timeTotal.setText("" + String.format("%02d", minsTotal) + ":" + String.format("%02d", secsTotal));
            timeTotal.setTextColor(Color.RED);

            if (autoRun){
                if (currentExercise <= listExercise.size()-1){
                    handlerTotalTime.postDelayed(this, 0);
                }
            }else{
                handlerTotalTime.postDelayed(this, 0);
            }

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
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_workout_run);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_workout_run));
        Bundle b = getIntent().getExtras();
        transactionController = new TransactionController(this);
        if (b!=null) {
            exerId = b.getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID, 0);
            workId = b.getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WORK_ID, 0);
            day = b.getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_DAY, 0);
            week = b.getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_WEEK, 0);


            btnstart = (Button) findViewById(R.id.start);
            btnreset = (Button) findViewById(R.id.reset);
            btnext = (Button) findViewById(R.id.next);
            time = (TextView) findViewById(R.id.timer);
            timeTotal = (TextView) findViewById(R.id.subTimer);

            if (b.getInt("autoRun",0) >0 ){
                autoRun = true;
            }


            if (exerId > 0) {
                currentExercise = b.getInt("position", 0);
            }else {
                currentExercise = 0;
            }


            workoutExerController = new WorkoutExerController(this);
            workoutExerController.open();
            listExercise = workoutExerController.getExercisebyWD(workId, day, week);


            final Exercise exercise = getData(listExercise.get(currentExercise).getExerid());

            getSupportActionBar().setTitle(exercise.getName());
            String strImgs = exercise.getImage();
            imageArray = strImgs.split(",");
            imgEx = (ImageView) findViewById(R.id.imgex);
            startIndex = 0;
            endIndex = imageArray.length - 1;
            nextImage();
            initData();
            if  (autoRun){
                starttime = SystemClock.uptimeMillis();
                totalTime = SystemClock.uptimeMillis();
                timeRunAuto = workoutExerController.getTime(workId,exercise.getId());
                handler.postDelayed(updateTimer,0);
                handlerTotalTime.postDelayed(updateTimerTotal,0);
            }
            workoutExerController.close();


        }

    }


    private void initData(){
        Transaction transaction1 = new Transaction(Utils.getStrToday(),exerId,5,20.0f,25,10,"5*5");
        Transaction transaction2 = new Transaction(Utils.getStrToday(),exerId,5,22.0f,25,10,"5*5");
        Transaction transaction3 = new Transaction(Utils.getStrToday(),exerId,5,24.0f,25,10,"5*5");
        Transaction transaction4 = new Transaction(Utils.getStrToday(),exerId,5,26.0f,25,10,"5*5");
        Transaction transaction5 = new Transaction(Utils.getStrToday(),exerId,5,28.0f,25,10,"5*5");
        transactions = new ArrayList<>();

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        transactions.add(transaction5);

        Utils.addListResult(transactions);

        listWorkout = (ListView)findViewById(R.id.list_transaction);
        TransactionWoAdapter transactionWoAdapter =  new TransactionWoAdapter(this, transactions);
        transactionWoAdapter.setIsResult(false);
        listWorkout.setAdapter(transactionWoAdapter);
        Utils.ListUtils.setDynamicHeight(listWorkout);
}


    public void reloadData(){
        ListView listWorkout = (ListView)findViewById(R.id.list_transaction);
        TransactionWoAdapter transactionWoAdapter = new  TransactionWoAdapter(this, transactions);
        transactionWoAdapter.setIsResult(false);
        listWorkout.setAdapter(transactionWoAdapter);
    }

    public void updateTime(float fTime, int position){
        transactions.get(position).setTime(fTime);
        listWorkout.setAdapter(new TransactionWoAdapter(this, transactions));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workout_run, menu);
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
            totalTime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
            handlerTotalTime.postDelayed(updateTimerTotal, 0);
            t = 0;
        } else {
            btnstart.setText("Start");
            time.setTextColor(Color.BLUE);
            timeSwapBuff += timeInMilliseconds;
            timeSwapBuffTotal += timeInMillisecondsTotal;
            handler.removeCallbacks(updateTimer);
            handlerTotalTime.removeCallbacks(updateTimerTotal);
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

        totalTime = 0L;
        timeInMillisecondsTotal = 0L;
        timeSwapBuffTotal = 0L;
        updatedtimeTotal = 0L;
        secsTotal = 0;
        minsTotal = 0;
        millisecondsTotal = 0;


        btnstart.setText("Start");
        handler.removeCallbacks(updateTimer);
        handlerTotalTime.removeCallbacks(updateTimerTotal);
        time.setText(getResources().getText(R.string.start_time));
        timeTotal.setText(getResources().getText(R.string.start_time));
        currentExercise = 0;
        btnext.setEnabled(true);
        reloadImage();

    }


    public void onClickSave(View v){

        Intent intent = new Intent(this, ResultWorkoutActivity.class);
        startActivity(intent);
//        new ParticleSystem(this, 80, R.drawable.confeti2, 10000)
//                .setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180)
//                .setRotationSpeed(144)
//                .setAcceleration(0.00005f, 90)
//                .emit(findViewById(R.id.emiter_top_right), 8);
    }


    private void firework(View v){
        new ParticleSystem(this, 50, R.mipmap.star, 10000)
                .setSpeedRange(0.2f, 0.5f)
                .oneShot(v, 50);
    }

    private void reloadImage(){
        exerId = listExercise.get(currentExercise).getExerid();
        final Exercise exercise = getData(exerId);
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
//            if (currentExercise ==0){
//                handlerTotalTime.postDelayed(updateTimerTotal,0);
//            }
            transactionController.open();
            transactionController.create(transactions);
            transactionController.close();
            currentExercise++;
            resetTime();
            handler.postDelayed(updateTimer, 0);
            reloadImage();
            initData();
            if (currentExercise == listExercise.size() - 1){
                btnext.setEnabled(false);
            }
        }
    }


    private void resetTime(){
        starttime = SystemClock.uptimeMillis();
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedtime = 0L;
        t = 1;
        secs = 0;
        mins = 0;
        milliseconds = 0;
        handler.removeCallbacks(updateTimer);
        time.setText(getResources().getText(R.string.start_time));
    }


}
