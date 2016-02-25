package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;
import com.google.android.gms.nearby.bootstrap.request.StopScanRequest;

public class BodyFatActivity extends AppCompatActivity {

    private TextView tvBodyFat;
    private ImageView imgGender;
    private int gender;
    private TextView tvWeight;
    private TextView tvWaist;
    private TextView tvWeightChoice;
    private TextView tvHeightChoice;
    private String weightChoice;
    private String heightChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_body_fat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_body_fat));

        tvBodyFat = (TextView)findViewById(R.id.txtBodyFat);
        tvWeight = (TextView)findViewById(R.id.txtWeight);
        tvWaist = (TextView)findViewById(R.id.txtWaist);
        tvWeightChoice = (TextView)findViewById(R.id.txtWeightChoice);
        tvHeightChoice = (TextView)findViewById(R.id.txtHeightChoice);
        setData();
        addListener();
    }

    public void setData(){
        imgGender = (ImageView)findViewById(R.id.imageGender);
        Bundle b = getIntent().getExtras();
        if (b!=null) {
            gender = b.getInt("gender");
            if (gender == Constant.GENDER_GIRL){
                imgGender.setImageResource(R.mipmap.ic_bodyfat_girl);
            }else{
                imgGender.setImageResource(R.mipmap.ic_bodyfat_boy);
            }
            String weight = b.getString("weight");
            weightChoice = b.getString(Constant.KEY_WEIGHT_CHOICE);
            heightChoice = b.getString(Constant.KEY_HEIGHT_CHOICE);
            tvWeightChoice.setText(weightChoice);
            tvHeightChoice.setText(heightChoice);
            tvWeight.setText(weight);

        }
    }

    public void addListener(){
        tvWeight.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float weight = 0f;
                float bodyFat = 0f;
                float waist = 0f;
                if (!tvWeight.getText().toString().equals("")) {
                    weight = Float.parseFloat(tvWeight.getText().toString());
                    if (weightChoice.equals(getResources().getString(R.string.lb))){
                        weight = Math.round(weight / Constant.KG_TO_LB);
                    }
                }
                if (!tvWaist.getText().toString().equals("")) {
                    waist = Float.parseFloat(tvWaist.getText().toString());
                    if (heightChoice.equals(getResources().getString(R.string.inch))){
                        waist = Math.round(waist * Constant.INCH_TO_CM);
                    }
                }

                bodyFat = Utils.calculatorBodyFat(weight, waist, gender);
                tvBodyFat.setText(String.valueOf(bodyFat));
            }
        });
        tvWaist.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float weight = 0f;
                float bodyFat = 0f;
                float waist = 0f;
                if (!tvWeight.getText().toString().equals("")) {
                    weight = Float.parseFloat(tvWeight.getText().toString());
                    if (weightChoice.equals(getResources().getString(R.string.lb))){
                        weight = Math.round(weight / Constant.KG_TO_LB);
                    }
                }
                if (!tvWaist.getText().toString().equals("")) {
                    waist = Float.parseFloat(tvWaist.getText().toString());
                    if (heightChoice.equals(getResources().getString(R.string.inch))){
                        waist = Math.round(waist * Constant.INCH_TO_CM);
                    }

                }

                bodyFat = Utils.calculatorBodyFat(weight, waist, gender);
                if (weight == 0f || waist == 0f){
                    bodyFat = 0f;
                }
                tvBodyFat.setText(String.valueOf(bodyFat));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bodyfat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        if (id == R.id.save){
            Intent intentUser = new Intent(this,UserActivity.class);
            intentUser.putExtra(Constant.KEY_BODY_FAT, tvBodyFat.getText().toString());
            setResult(Constant.BODYFAT_USER_REQUEST, intentUser);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



}

