package com.alanddev.gymlover.ui;

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

import com.alanddev.gymlover.R;
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


import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {


    String year;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_report));



        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


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



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        LineChart chart;
        PieChart chartPie;
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
            int tabPage = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView =  inflater.inflate(R.layout.fragment_report_line, container, false);
            switch(tabPage)
            {
                case 0:
                    rootView = inflater.inflate(R.layout.fragment_report_line, container, false);
                    setDataLine(rootView);
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.fragment_report_pie, container, false);
                    setDataPie(rootView);
                    break;
            }

            return rootView;
        }

        public void setDataLine(View rootView){
            chart = (LineChart)rootView.findViewById(R.id.chart);
            int count = 15;
            int range = 20;
            ArrayList<String> xVals = new ArrayList<String>();
            for (int i = 0; i < count; i++) {
                xVals.add((15 + i) + "/01/2016" );
            }

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            for (int i = 0; i < count; i++) {

                float mult = (range + 1);
                float val = (float) (Math.random() * mult) + 3;// + (float)
                yVals.add(new Entry(val, i));
            }

            // create a dataset and give it a type
            LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
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
            chart.setData(data);
            chart.animateY(800, Easing.EasingOption.EaseInBounce);


        }

        public void setDataPie(View rootView){
            chartPie = (PieChart)rootView.findViewById(R.id.chart);
            float[] yData = { 5, 10, 15, 30, 40 };
            String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };
            //String[] xData = { "ic_category", "Huawei", "LG", "Apple", "Samsung" };

            ArrayList<Entry> yVals = new ArrayList<Entry>();

            float total = 0.0f;


            for (int i = 0; i < yData.length; i++)
                yVals.add(new Entry(yData[i], i));

            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < xData.length; i++)
                xVals.add(xData[i]);

            PieDataSet pieDataSet = new PieDataSet(yVals, "expenses");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setDrawValues(false);


            PieData data = new PieData(xVals, pieDataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.GRAY);

            chartPie.setData(data);
            decorationChart(chartPie, pieDataSet);

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

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
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
                    return "Body Fat";
                case 1:
                    return "Workout";
            }
            return null;
        }
    }



}
