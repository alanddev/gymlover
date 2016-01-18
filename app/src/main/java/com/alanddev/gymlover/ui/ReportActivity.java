package com.alanddev.gymlover.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.util.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    BarChart chart;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_report));
        getData();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        ArrayList<String> xAxis = new ArrayList<>();
        chart = (BarChart) findViewById(R.id.chart);

        for (int m = 1; m <=12; m++){
            xAxis.add(Integer.toString(m));
        }

        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSetY = new ArrayList<>();
//        ArrayList<Trend> trends = transactionController.getAmountTrendByMonths(option, Utils.getWallet_id(), monthBegin, monthEnd, year);
//        listView.setAdapter(new TrendAdapter(this, trends));
        for (int i = 0; i<12;i++){
            valueSetY.add(new BarEntry(i,i));
        }
        year  = Utils.getYear();
        dataSets = new ArrayList<>();
        BarDataSet barDataSet1 = new BarDataSet(valueSetY, year);
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSets.add(barDataSet1);

        BarData data = new BarData(xAxis,dataSets);
        chart.setData(data);
        chart.setDescription("");
        chart.animateXY(2000, 2000);
        chart.invalidate();


    }
}
