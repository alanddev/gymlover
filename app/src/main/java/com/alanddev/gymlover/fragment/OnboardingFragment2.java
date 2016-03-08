package com.alanddev.gymlover.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alanddev.gymlover.R;


public class OnboardingFragment2 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle s) {

        return inflater.inflate(
                R.layout.on_boarding_screen2,
                container,
                false
        );

    }
}



