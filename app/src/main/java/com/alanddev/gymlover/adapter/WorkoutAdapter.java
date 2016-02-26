package com.alanddev.gymlover.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.ui.WorkoutSettingActivity;
import com.alanddev.gymlover.util.Utils;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class WorkoutAdapter extends ArrayAdapter<Workout> {

    private Context mContext;
    public WorkoutAdapter(Context context, List<Workout> workouts) {
        super(context, 0, workouts);
        mContext=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        ImageView imgChecked = (ImageView)convertView.findViewById(R.id.checked);
        // Populate the data into the template view using the data object
        tvName.setText(workout.getName());
        tvWeek.setText(workout.getWeek() + " " + getContext().getString(R.string.week));
        int temp = position%6;
        imgIcon.setImageResource(mContext.getResources().getIdentifier("workout_" + temp, "mipmap", mContext.getPackageName()));
        imgChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.checked:
                        PopupMenu popup = new PopupMenu(getContext(), v);
                        popup.getMenuInflater().inflate(R.menu.workout_popup,
                                popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.install:
                                        //Or Some other code you want to put here.. This is just an example.
                                        Intent intentSetting = new Intent(getContext(),WorkoutSettingActivity.class);
                                        getContext().startActivity(intentSetting);
//                                        Toast.makeText(getContext(), " Install Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
                                        break;
                                    case R.id.addtowishlist:
                                        Toast.makeText(getContext(), "Add to Wish List Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
                                        break;
                                    default:
                                        break;
                                }

                                return true;
                            }
                        });

                        break;

                    default:
                        break;
                }

            }
        });
//        if (wallet.getId() == utils.getSharedPreferencesValue(convertView.getContext(), Constant.WALLET_ID)){
//            imgChecked.setImageResource(R.mipmap.ic_check_green);
//        }

        // Return the completed view to render on screen
        return convertView;
    }



}
