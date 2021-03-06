package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.TransactionWoAdapter;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.controller.TransactionController;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;
import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;

public class ResultWorkoutActivity extends AppCompatActivity {

    TransactionController transactionController;
    ListView listWorkout;
    ArrayList<Transaction>transactions;
    private TextView tvTime;
    ExcerciseController exerciseController;
    private float userWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_result_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_result_workout));

        transactions = Utils.getListResult();
        new ParticleSystem(this, 50, R.mipmap.star, 10000)
                .setSpeedRange(0.2f, 0.5f)
                .oneShot(findViewById(R.id.done), 50);


        exerciseController = new ExcerciseController(this);
        exerciseController.open();
        userWeight = Utils.getUserWeight(this);
        listWorkout = (ListView)findViewById(R.id.list_transaction);
        TransactionWoAdapter transactionWoAdapter = new  TransactionWoAdapter(this, transactions);
        transactionWoAdapter.setIsResult(true);
        listWorkout.setAdapter(transactionWoAdapter);
        Utils.ListUtils.setDynamicHeight(listWorkout);
        transactionController = new TransactionController(this);
        transactionController.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionController.create(transactions);
                Utils.emptyListResult();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tvTime = (TextView)findViewById(R.id.totalTime);
        getData();
    }

    private void getData(){
        Bundle b = getIntent().getExtras();
        if (b !=null){
            String time = b.getString(Constant.KEY_TIME, getResources().getString(R.string.start_time));
            tvTime.setText(time);
        }
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

    public void reloadData(){
        TransactionWoAdapter transactionWoAdapter = new  TransactionWoAdapter(this, transactions);
        transactionWoAdapter.setIsResult(true);
        listWorkout.setAdapter(transactionWoAdapter);
    }

    public void updateTime(float fTime, int position){
        Transaction transaction = transactions.get(position);
        transaction.setTime(fTime);
        float baseCalo = exerciseController.getById(transaction.getExericise()).getCalo();
        float calo = Utils.calculatorCalo(userWeight,fTime,baseCalo);
        transaction.setCalo(calo);
        listWorkout.setAdapter(new TransactionWoAdapter(this, transactions));
    }

    public void updateWeight(float fWeight, int position){
        transactions.get(position).setWeight(fWeight);
        listWorkout.setAdapter(new TransactionWoAdapter(this, transactions));
    }

    public void updateReps(int repeat, int position){
        transactions.get(position).setRepeat(repeat);
        listWorkout.setAdapter(new TransactionWoAdapter(this, transactions));
    }

}
