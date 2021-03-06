package com.alanddev.gymlover.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.TransSectionPagerAdapter;
import com.alanddev.gymlover.controller.TransactionController;
import com.alanddev.gymlover.controller.UserController;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Transactions;
import com.alanddev.gymlover.model.User;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TransSectionPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private final int REQUEST_SETTING = 100;
    private final int REQUEST_USER_EDIT = 101;
    private SharedPreferences mShaPref;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_main));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TransactionAddActivity.class);
                startActivityForResult(intent, Constant.ADD_TRANSACTION_REQUEST);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setNavHeader(navigationView);

        mShaPref = Utils.getSharedPreferences(this);
        int viewType = mShaPref.getInt(Constant.VIEW_TYPE, 0);
        List<Transactions> transactionses = getData(viewType);
        mSectionsPagerAdapter = new TransSectionPagerAdapter(getSupportFragmentManager(),transactionses);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        if(transactionses.size()>0) {
            mViewPager.setCurrentItem(transactionses.size() - 2);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(mShaPref!=null){
            mShaPref = Utils.getSharedPreferences(this);
        }
        int viewtype=mShaPref.getInt(Constant.VIEW_TYPE, 0);
        Boolean isState = true;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_day) {
            if(viewtype!=Constant.VIEW_TYPE_DAY) {
                viewtype = Constant.VIEW_TYPE_DAY;
                isState=false;
            }
        }else if (id == R.id.action_view_week) {
            if(viewtype!=Constant.VIEW_TYPE_WEEK) {
                viewtype = Constant.VIEW_TYPE_WEEK;
                isState=false;
            }
        }else if(id == R.id.action_view_month) {
            if(viewtype!=Constant.VIEW_TYPE_MONTH) {
                viewtype = Constant.VIEW_TYPE_MONTH;
                isState=false;
            }
        }else if (id == R.id.action_view_year) {
            if(viewtype!=Constant.VIEW_TYPE_YEAR) {
                viewtype = Constant.VIEW_TYPE_YEAR;
                isState=false;
            }
        }

        Utils.setSharedPreferencesValue(this, Constant.VIEW_TYPE, viewtype);
        if(!isState){
            notifyDataSetChanged(true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            // Handle the camera action
            Intent intent = new Intent(this,SettingActivity.class);
            startActivityForResult(intent,REQUEST_SETTING);
        }else if(id == R.id.nav_exercise){
            Intent intent = new Intent(this,ExerciseGrpActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_workout){
            Intent intent = new Intent(this,WorkoutActivity.class);
            startActivity(intent);
        }else if(id == R.id.nav_user){
            Intent intent = new Intent(this,UserActivity.class);
            startActivityForResult(intent,REQUEST_USER_EDIT);
        }else if(id == R.id.nav_run_report){
            Intent intent = new Intent(this,ReportActivity.class);
            intent.putExtra(Constant.KEY_REPORT_TYPE, Constant.REPORT_TYPE_WORKOUT);
            startActivity(intent);
        }else if(id == R.id.nav_helper){
            Intent intent = new Intent(this,OnboardingActivity.class);
            //intent.putExtra(Constant.KEY_REPORT_TYPE, Constant.REPORT_TYPE_WORKOUT);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_SETTING){
            Utils.onActivityCreateSetTheme(this);
            Utils.refresh(this);
        }
        if(requestCode==Constant.ADD_TRANSACTION_REQUEST&&resultCode==Constant.ADD_TRANSACTION_SUCCESS) {
            notifyDataSetChanged(false);
        }
        if(resultCode==Constant.TRANS_DETAIL_UPDATE) {
            notifyDataSetChanged(false);
        }

        if(requestCode==Constant.GALLERY_USER_REQUEST){
            if (data !=null){
                Utils utils = new Utils();
                Uri selectedImageUri = data.getData();
                String imagePath = utils.getRealPathFromURI(this, selectedImageUri);
                File image = new File(imagePath);
                String imageFileName = image.getName();
                try {
                    utils.copyFile(imagePath, Constant.PATH_IMG + "/" + imageFileName);
                    UserController controller = new UserController(this);
                    controller.open();
                    User user = controller.getId(1);
                    user.setImg(imageFileName);
                    controller.update(user);
                    controller.close();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(Constant.PATH_IMG + "/" + imageFileName));
                }
                catch (IOException e){
                };
            }
        }

        if(requestCode==REQUEST_USER_EDIT&&resultCode==Constant.EDIT_USER_RESULT){
            setNavHeader(navigationView);
        }

    }


    private void notifyDataSetChanged(Boolean isChangeType){

        List<Transactions> transactionses = getData(mShaPref.getInt(Constant.VIEW_TYPE, 0));

        mSectionsPagerAdapter.setData(transactionses);
        mSectionsPagerAdapter.notifyDataSetChanged();
        //updateNaviHeader(navigationView);


        if(isChangeType) {
            tabLayout.setupWithViewPager(mViewPager);
        }
        if(transactionses.size() > 0) {
            mViewPager.setCurrentItem(transactionses.size() - 2);
        }

    }

    private List<Transactions> getData(int viewType){
        TransactionController controller = new TransactionController(this);
        controller.open();
        List<Transactions> lstTrans = controller.getAll(viewType);
        controller.close();
        return lstTrans;
    }

    private void setNavHeader(NavigationView navigationView){
        View header = navigationView.getHeaderView(0);
        UserController controller = new UserController(this);
        controller.open();
        User user = controller.getId(1);
        controller.close();

        TextView txtWallet = (TextView) header.findViewById(R.id.txtWallet);
        imageView = (ImageView)header.findViewById(R.id.imageView);
        txtWallet.setText(user.getName());

        if (!user.getImg().equals("")){
            imageView.setImageBitmap(BitmapFactory.decodeFile(Constant.PATH_IMG + "/" + user.getImg()));
        }else {
            imageView.setImageResource(R.mipmap.avatar);
        }

        String naviheader = Utils.getCurrentNavHeader(this);

        header.setBackgroundResource(getResources().getIdentifier(naviheader, "mipmap", getPackageName()));

        /*TextView textAmt = (TextView)header.findViewById(R.id.textAmt);
        TransactionController transactionController = new TransactionController(this);
        transactionController.open();
        float fAmount = transactionController.getAmountByWallet(wallet.getId());
        transactionController.close();
        NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
        String sAmount =  formatter.format(fAmount) + "  " + wallet.getCurrency();
        textAmt.setText(sAmount);*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, Constant.GALLERY_USER_REQUEST);
            }
        } );
    }

}
