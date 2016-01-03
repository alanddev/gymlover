package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
        setData();
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
            tvWeight.setText(weight);
        }
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
            intentUser.putExtra("bodyFat", tvBodyFat.getText().toString());
            setResult(Constant.BODYFAT_USER_REQUEST, intentUser);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickCalculate(View v){
        double weight = Double.parseDouble(tvWeight.getText().toString());
        double waist = Double.parseDouble(tvWaist.getText().toString());
        double bodyFat = Utils.calculatorBodyFat(weight,waist,gender);
        tvBodyFat.setText(String.valueOf(bodyFat));
        //finish();
    }


}

