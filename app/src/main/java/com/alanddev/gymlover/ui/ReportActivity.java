package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.TransSumGrpAdapter;
import com.alanddev.gymlover.adapter.TransactionWoAdapter;
import com.alanddev.gymlover.controller.HistoryController;
import com.alanddev.gymlover.controller.TransactionController;
import com.alanddev.gymlover.model.History;
import com.alanddev.gymlover.model.TransactionSumGroup;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.location.places.Place;


import java.util.ArrayList;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {


    String year;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TransactionController transactionController;
    private int typeReport;
    private String dateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_report));


        transactionController = new TransactionController(this);



        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        getData();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void getData(){
        Bundle b = getIntent().getExtras();
        typeReport=Constant.REPORT_TYPE_BODY;
        if (b!=null){
            typeReport = b.getInt(Constant.REPORT_TYPE,Constant.REPORT_TYPE_BODY);
            dateStr =  b.getString(Constant.PUT_EXTRA_DATE,Utils.changeDate2Str(new Date()));
        }
        mSectionsPagerAdapter.typeReport = typeReport;
    }

    public void reloadData(int viewType){
        finish();
        Intent intent = new Intent(this,ReportActivity.class);
        intent.putExtra(Constant.REPORT_TYPE, typeReport);
        intent.putExtra(Constant.VIEW_TYPE,viewType);
        intent.putExtra(Constant.PUT_EXTRA_DATE, dateStr);
        startActivity(intent);
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

        if (id == R.id.action_view_day) {
             reloadData(Constant.VIEW_TYPE_DAY);
        }else if (id == R.id.action_view_week) {
            reloadData(Constant.VIEW_TYPE_WEEK);
        }else if(id == R.id.action_view_month) {
            reloadData(Constant.VIEW_TYPE_MONTH);
        }else if (id == R.id.action_view_year) {
            reloadData(Constant.VIEW_TYPE_YEAR);
        }


        return super.onOptionsItemSelected(item);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        LineChart chartBodyFat;
        LineChart chartBMI;
        PieChart chartPieCalo;
        PieChart chartPieWeight;
        public Date dateReport;
        public int typeView;
        public int tabPage;
        public int typeReport;

        private TransactionController transactionController;
        private HistoryController historyController;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            tabPage = getArguments().getInt(ARG_SECTION_NUMBER);

            View rootView =  inflater.inflate(R.layout.fragment_report_line, container, false);
            transactionController = new TransactionController(getContext());
            historyController = new HistoryController(getContext());

            getData();
            switch(tabPage)
            {
                case 0:
                    if (typeReport == Constant.REPORT_TYPE_WORKOUT) {
                        rootView = getFramePie(inflater, rootView, container);
                    }else{
                        rootView = getFrameLine(inflater,rootView,container);
                    }
                    break;
                case 1:
                    if (typeReport == Constant.REPORT_TYPE_WORKOUT) {
                        rootView = getFrameLine(inflater, rootView, container);
                    }else{
                        rootView = getFramePie(inflater,rootView,container);
                    }
                    break;
            }

            return rootView;
        }


        public View getFrameLine(LayoutInflater inflater,View rootView,ViewGroup container){
            rootView = inflater.inflate(R.layout.fragment_report_line, container, false);
            Button btUpdate = (Button)rootView.findViewById(R.id.update);
            btUpdate.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    onClickUpdate(v);
                }
            });
            historyController.open();
            ArrayList<History>histories = historyController.getBodyIndex(dateReport,typeView);
            setDataLineBodyFat(rootView,histories);
            setDataLineBMI(rootView, histories);
            return rootView;
        }

        public View getFramePie(LayoutInflater inflater,View rootView,ViewGroup container){
            rootView = inflater.inflate(R.layout.fragment_report_pie, container, false);
            ListView listTransaction = (ListView)rootView.findViewById(R.id.list_transaction);
            transactionController.open();
            ArrayList<TransactionSumGroup> tranSumGrps = transactionController.getCaloGroup(dateReport,typeView);
            listTransaction.setAdapter(new TransSumGrpAdapter(getContext(), tranSumGrps));
            setDataPieCalo(rootView, tranSumGrps);
            setDataPieWeight(rootView, tranSumGrps);
            Utils.ListUtils.setDynamicHeight(listTransaction);
            return rootView;
        }




        public void getData(){
            Bundle b = getActivity().getIntent().getExtras();
            if (b!=null) {
                typeView = b.getInt(Constant.VIEW_TYPE, 0);
                String dateStr = b.getString(Constant.PUT_EXTRA_DATE,Utils.changeDate2Str(new Date()));
                dateReport = Utils.changeStr2Date(dateStr, Constant.DATE_FORMAT_DB);
                typeReport = b.getInt(Constant.REPORT_TYPE,0);
            }else {
                typeView = Constant.VIEW_TYPE_DAY;
                dateReport = new Date();
                typeReport = Constant.REPORT_TYPE_WORKOUT;
            }
        }

        public void setDataLineBodyFat(View rootView,ArrayList<History> histories){
            chartBodyFat = (LineChart)rootView.findViewById(R.id.chartBodyFat);
            int count = 15;
            int range = 20;
            ArrayList<String> xVals = new ArrayList<String>();
            ArrayList<Entry> yVals = new ArrayList<Entry>();
            for (int i = 0; i < histories.size(); i++) {
                History hist = histories.get(i);
                String sDate = hist.getDate();
                xVals.add(sDate.substring(5, sDate.length()));
                yVals.add(new Entry(hist.getFat(),i));
            }
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, "Body Fat");
            // set1.setFillAlpha(110);
            // set1.setFillColor(Color.RED);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.CYAN);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            //set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.CYAN);
            set1.setDrawFilled(true);


            ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            // set data
            chartBodyFat.setData(data);
            chartBodyFat.animateY(800, Easing.EasingOption.EaseInBounce);
        }


        public void setDataLineBMI(View rootView,ArrayList<History> histories){
            chartBMI = (LineChart)rootView.findViewById(R.id.chartBMI);
            ArrayList<String> xVals = new ArrayList<String>();
            ArrayList<Entry> yVals = new ArrayList<Entry>();
            for (int i = 0; i < histories.size(); i++) {
                History hist = histories.get(i);
                String sDate = hist.getDate();
                xVals.add(sDate.substring(5,sDate.length()));
                //xVals.add(hist.getDate());
                float BMI = hist.getWeight()/((hist.getHeight()/100)*(hist.getHeight()/100));
                yVals.add(new Entry(BMI,i));
            }
            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, "BMI");
            // set1.setFillAlpha(110);
            // set1.setFillColor(Color.RED);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.CYAN);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            //set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setFillAlpha(65);
            set1.setFillColor(Color.CYAN);
            set1.setDrawFilled(true);


            ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            // set data
            chartBMI.setData(data);
            chartBMI.animateY(800, Easing.EasingOption.EaseInBounce);
        }


        public void setDataPieCalo(View rootView, ArrayList<TransactionSumGroup>trans){
            chartPieCalo = (PieChart)rootView.findViewById(R.id.chartCalo);
            //float[] yData = { 5, 10, 15, 30, 40 };
            //String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            float total = 0.0f;


            for (int i = 0; i < trans.size(); i++)
                yVals.add(new Entry(trans.get(i).getCalo(), i));

            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < trans.size(); i++)
                xVals.add(trans.get(i).getName());

            PieDataSet pieDataSet = new PieDataSet(yVals, "Calo");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setDrawValues(false);


            PieData data = new PieData(xVals, pieDataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.GRAY);

            chartPieCalo.setData(data);
            decorationChart(chartPieCalo, pieDataSet);

        }

        public void setDataPieWeight(View rootView, ArrayList<TransactionSumGroup>trans){
            chartPieWeight = (PieChart)rootView.findViewById(R.id.chartWeight);
            float[] yData = { 5, 10, 15, 30, 40 };
            String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            float total = 0.0f;


            for (int i = 0; i < trans.size(); i++)
                yVals.add(new Entry(trans.get(i).getWeight(), i));

            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < trans.size(); i++)
                xVals.add(trans.get(i).getName());

            PieDataSet pieDataSet = new PieDataSet(yVals, "Weight");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setDrawValues(false);


            PieData data = new PieData(xVals, pieDataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.GRAY);

            chartPieWeight.setData(data);
            decorationChart(chartPieWeight, pieDataSet);

        }

        private void decorationChart(PieChart chart,PieDataSet dataSet){

            chart.getLegend().setEnabled(true);
            chart.setDescription("");
            chart.setUsePercentValues(true);
            //chart.invalidate();

            chart.animateY(800, Easing.EasingOption.EaseInBounce);
            chart.setTouchEnabled(true);
            //chart.setDrawCenterText(false)
            // ;
            // set display X text or not;
//        chart.setDrawSliceText(false);

            chart.setDrawHoleEnabled(true);
            chart.setHoleColorTransparent(true);
            chart.setHoleRadius(7);
            chart.setTransparentCircleRadius(10);
            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());
            dataSet.setColors(colors);

//        for (IDataSet<?> set : chart.getData().getDataSets())
//            set.setDrawValues(!set.isDrawValuesEnabled());

            Legend l = chart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(7);
            l.setYEntrySpace(5);

            //chart.setOnChartValueSelectedListener(context);
            chart.invalidate(); // refresh

        }

        public void onClickUpdate(View v){
            Intent intent = new Intent(getActivity(),UserActivity.class);
            intent.putExtra("update", 1);
            getActivity().finish();
            startActivity(intent);
        }


    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public int typeReport;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    if (typeReport == Constant.REPORT_TYPE_WORKOUT) {
                        return "Workout";
                    }else{
                        return "Body Fat";
                    }

                case 1:
                    if (typeReport == Constant.REPORT_TYPE_WORKOUT) {
                        return "Body Fat";
                    }else{
                        return "Workout";
                    }
            }
            //fragment = PlaceholderFragment.newInstance(position);
            return null;
        }
    }



}
