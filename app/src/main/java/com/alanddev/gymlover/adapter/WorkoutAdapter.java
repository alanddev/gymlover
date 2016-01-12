package com.alanddev.gymlover.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.util.Utils;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class WorkoutAdapter extends ArrayAdapter<Workout> {


    Utils utils;
    //TransactionController transactionController;
    public WorkoutAdapter(Context context, List<Workout> currencies) {
        super(context, 0, currencies);
        utils = new Utils();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Workout workout = (Workout)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_workout, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvWeek = (TextView) convertView.findViewById(R.id.weeks);
        ImageView imgIcon = (ImageView)convertView.findViewById(R.id.icon);
//        ImageView imgChecked = (ImageView)convertView.findViewById(R.id.checked);
        // Populate the data into the template view using the data object
        tvName.setText(workout.getName());
        tvWeek.setText(workout.getWeek() + " " + getContext().getString(R.string.week));

        Resources res = convertView.getResources();
        String srcImg = "ic_workout_" + workout.getImage().toLowerCase();
        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
        Drawable image = convertView.getResources().getDrawable(id);
        imgIcon.setImageDrawable(image);


//        if (wallet.getId() == utils.getSharedPreferencesValue(convertView.getContext(), Constant.WALLET_ID)){
//            imgChecked.setImageResource(R.mipmap.ic_check_green);
//        }

        // Return the completed view to render on screen
        return convertView;
    }


}
