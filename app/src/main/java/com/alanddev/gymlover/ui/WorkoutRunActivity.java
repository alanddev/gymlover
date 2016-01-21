package com.alanddev.gymlover.ui;

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
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.Workout;
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
    private int exerId;
    private int endIndex;
    private ArrayList<Integer> listExercise;
    private int currentExercise;
    ArrayList<Transaction> transactions;

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
        exerId = getIntent().getExtras().getInt(MwSQLiteHelper.COLUMN_WORKOUT_EXER_EXER_ID, 0);
        btnstart = (Button) findViewById(R.id.start);
        btnreset = (Button) findViewById(R.id.reset);
        btnext = (Button)findViewById(R.id.next);
        time = (TextView) findViewById(R.id.timer);

        currentExercise = 0;

        listExercise = new ArrayList<>();
        listExercise.add(1);


        final Exercise exercise = getData(exerId);
        getSupportActionBar().setTitle(exercise.getName());
        String strImgs = exercise.getImage();
        imageArray = strImgs.split(",");
        imgEx = (ImageView)findViewById(R.id.imgex);
        startIndex = 0;
        endIndex = imageArray.length-1;
        nextImage();

        initData();

    }

//    private void drawText(String value){
//        ImageView imageClock = (ImageView)findViewById(R.id.myImageView);
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.clock_background);
//        Bitmap.Config config = bm.getConfig();
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        Bitmap newImage = Bitmap.createBitmap(width, height, config);
//        Canvas c = new Canvas(newImage);
//        c.drawBitmap(bm, 0, 0, null);
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.FILL);
////        Rect bounds = new Rect();
////        paint.getTextBounds(value, 0, value.length(), bounds);
////        int x = (imageClock.getWidth() - bounds.width())/2;
////        int y = (imageClock.getHeight() + bounds.height())/2;
//        paint.setTextSize(40);
//        c.drawText(value, imageClock.getWidth()/2, 150, paint);
//        imageClock.setImageBitmap(newImage);
//
//    }


    private void initData(){
        transactions = new ArrayList<Transaction>();
        Transaction transaction1 = new Transaction("21/01/2016",exerId,5,20.0f,25,10,"5*5");
        Transaction transaction2 = new Transaction("21/01/2016",exerId,5,22.0f,25,10,"5*5");
        Transaction transaction3 = new Transaction("21/01/2016",exerId,5,24.0f,25,10,"5*5");
        Transaction transaction4 = new Transaction("21/01/2016",exerId,5,26.0f,25,10,"5*5");
        Transaction transaction5 = new Transaction("21/01/2016",exerId,5,28.0f,25,10,"5*5");
        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);
        transactions.add(transaction4);
        transactions.add(transaction5);

        ListView listWorkout = (ListView)findViewById(R.id.list_transaction);
        listWorkout.setAdapter(new TransactionWoAdapter(this, transactions));
        Utils.ListUtils.setDynamicHeight(listWorkout);
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


    public void onClickSave(View v){
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
