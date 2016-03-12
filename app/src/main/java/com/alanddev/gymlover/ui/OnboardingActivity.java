package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.fragment.OnboardingFragment1;
import com.alanddev.gymlover.fragment.OnboardingFragment2;
import com.alanddev.gymlover.fragment.OnboardingFragment3;
import com.alanddev.gymlover.util.Constant;
import com.gc.materialdesign.views.ButtonFlat;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class OnboardingActivity extends FragmentActivity {

    private ViewPager pager;
    private SmartTabLayout indicator;
    private ButtonFlat skip;
    private ButtonFlat next;
    private int length = Constant.LENGTH_GUIDE;
    private int first_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        pager = (ViewPager)findViewById(R.id.pager);
        indicator = (SmartTabLayout)findViewById(R.id.indicator);
        skip = (ButtonFlat)findViewById(R.id.skip);
        next = (ButtonFlat)findViewById(R.id.next);

        Bundle bundle = getIntent().getExtras();;
        first_guide = 0;
        if (bundle !=null){
            first_guide = bundle.getInt(Constant.KEY_FIRST_GUIDE);
        }

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {return new OnboardingFragment1();}
                else if (position == length-1) {return new OnboardingFragment3();}
                else {
                    OnboardingFragment2 fragment =  new OnboardingFragment2();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.KEY_GUIDE_POSITION,position);
                    fragment.setArguments(bundle);
                    return fragment;
                }

            }

            @Override
            public int getCount() {
                return length;
            }
        };

        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishOnboarding();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pager.getCurrentItem() == length -1 ) { // The last screen
                    finishOnboarding();
                } else {
                    pager.setCurrentItem(
                            pager.getCurrentItem() + 1,
                            true
                    );
                }
            }
        });

        indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == length - 1){
                    skip.setVisibility(View.GONE);
                    next.setText("Done");
                } else {
                    skip.setVisibility(View.VISIBLE);
                    next.setText("Next");
                }
            }
        });

    }


    private void finishOnboarding() {
        // Get the shared preferences
        // Launch the main Activity, called MainActivity
        if (first_guide == 1) {
            Intent intent = new Intent(OnboardingActivity.this, SelectThemeActivity.class);
            intent.putExtra("SETTING_EXTRA",Constant.CHANGE_LANGUAGE_ID);
            intent.putExtra("SETTING_FIRST",Constant.CHANGE_LANGUAGE_ID);
            startActivity(intent);
        }else {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
        // Close the OnboardingActivity
        finish();
    }

}
