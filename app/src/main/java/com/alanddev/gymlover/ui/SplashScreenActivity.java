package com.alanddev.gymlover.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.controller.ExcerciseGroupController;
import com.alanddev.gymlover.controller.UserController;
import com.alanddev.gymlover.controller.WorkoutController;
import com.alanddev.gymlover.controller.WorkoutExerController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.service.NotifyService;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;

import java.io.File;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        background.start();
    }
    Thread background = new Thread() {
        public void run() {
            try {
                init();

                //userController.open();
                if (checkDB()) {
                    sleep(Constant.SPLASH_DISPLAY_SHORT);
                } else {
                    initfor1st();
                    sleep(Constant.SPLASH_DISPLAY_LONG);
                }
                UserController userController = new UserController(getApplicationContext());
                userController.open();
                if (userController.getCount() > 0) {
                    sleep(Constant.SPLASH_DISPLAY_SHORT);
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                } else if(Utils.getCurrentLanguage(getApplicationContext()).equals("")){
                    Intent intent = new Intent(SplashScreenActivity.this,OnboardingActivity.class);
                    intent.putExtra(Constant.KEY_FIRST_GUIDE,1);
                    //Intent intent = new Intent(SplashScreenActivity.this, SelectThemeActivity.class);
//                    intent.putExtra("SETTING_EXTRA",Constant.CHANGE_LANGUAGE_ID);
//                    intent.putExtra("SETTING_FIRST",Constant.CHANGE_LANGUAGE_ID);
                    startActivity(intent);
                } else{
                    sleep(Constant.SPLASH_DISPLAY_SHORT);
                    Intent intent = new Intent(getBaseContext(), UserActivity.class);
                    intent.putExtra("SETTING_FIRST",1);
                    startActivity(intent);
                }
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Boolean checkDB() {
        File dbtest = new File(getResources().getString(R.string.db_path) + MwSQLiteHelper.DATABASE_NAME);
        return dbtest.exists();
    }


    private void initfor1st() {
        ExcerciseGroupController controller = new ExcerciseGroupController(getApplicationContext());
        controller.open();
        controller.init();
        controller.close();

        ExcerciseController excerciseController = new ExcerciseController(getApplicationContext());
        excerciseController.open();
        excerciseController.init();
        excerciseController.close();
        //start notification services
        Intent intent = new Intent();
        intent.setAction("com.alanddev.manwal.CUSTOM_INTENT");
        sendBroadcast(intent);

        WorkoutController workoutController = new WorkoutController(getApplicationContext());
        workoutController.open();
        workoutController.init();
        workoutController.close();

        WorkoutExerController exerController = new WorkoutExerController(getApplicationContext());
        exerController.open();
        exerController.init();
        exerController.close();


    }

    private void init() {
        String theme = Utils.getCurrentTheme(this);
        if (!theme.equals("")) {
            Utils.changeToTheme(theme);
        } else {
            Utils.setSharedPreferencesValue(getApplicationContext(), Constant.THEME_CURRENT, "DodgerBlue_Theme");
            Utils.changeToTheme("DodgerBlue_Theme");
        }

        String naviheader = Utils.getCurrentNavHeader(this);
        if (naviheader.equals("")) {
            Utils.setSharedPreferencesValue(getApplicationContext(), Constant.NAV_HEADER_CURRENT, getString(R.string.navi_header_default));
        }

        if (getIntent().getExtras() != null && getIntent().getExtras().get("NOTIFICATION")!=null && getIntent().getExtras().get("NOTIFICATION").toString().equals("1")) {
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.cancel(NotifyService.GREETNG_ID);
        }
        Utils.createFolder(Constant.PATH_IMG);
    }

}
