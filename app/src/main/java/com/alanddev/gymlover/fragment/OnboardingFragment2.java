package com.alanddev.gymlover.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.util.Constant;


public  class OnboardingFragment2 extends Fragment{
    View view;
    TextView tvGuide;
    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle s) {

        view =  inflater.inflate(
                R.layout.on_boarding_screen2,
                container,
                false
        );
        Bundle bundle = getArguments();
        int position = 1;
        if (bundle !=null) {
            position = bundle.getInt(Constant.KEY_GUIDE_POSITION);
        }
        init (position,view);
        return view;

    }


    public void init(int position, View view){
        tvGuide =(TextView) view.findViewById(R.id.guide);
        imageView =(ImageView) view.findViewById(R.id.image);
        String srcGuide = "guide_" + position;
        int idGuide = getResources().getIdentifier(srcGuide, "string", getContext().getPackageName());
        tvGuide.setText(getResources().getString(idGuide));

        String srcImg = "guide_" + position;
        int id = getResources().getIdentifier(srcImg, "mipmap", getContext().getPackageName());
        Drawable image = getResources().getDrawable(id);
        imageView.setImageDrawable(image);

    }
}



