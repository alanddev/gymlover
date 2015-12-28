package com.alanddev.gymlover.ui;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
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
                if (checkDB()) {
                    Utils.setWallet_id(Utils.getSharedPreferencesValue(getApplicationContext(), Constant.WALLET_ID));
                    sleep(Constant.SPLASH_DISPLAY_LONG);
                } else {
                    initfor1st();
                    sleep(Constant.SPLASH_DISPLAY_SHORT);
                }
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            } catch (Exception e) {
            }
        }
    };

    private Boolean checkDB() {
        File dbtest = new File(getResources().getString(R.string.db_path) + MwSQLiteHelper.DATABASE_NAME);
        return dbtest.exists();
    }


    private void initfor1st() {
        //start notification services
        Intent intent = new Intent();
        intent.setAction("com.alanddev.manwal.CUSTOM_INTENT");
        sendBroadcast(intent);
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

        if (getIntent().getExtras() != null && getIntent().getExtras().get("NOTIFICATION").toString().equals("1")) {
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.cancel(NotifyService.GREETNG_ID);
        }
        Utils.createFolder(Constant.PATH_IMG);
    }

}
