package com.alanddev.gymlover.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.ui.WorkoutRunActivity;
import com.alanddev.gymlover.ui.WorkoutSettingActivity;
import com.alanddev.gymlover.util.Utils;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class TransactionWoAdapter extends ArrayAdapter<Transaction> {

    public TransactionWoAdapter(Context context, List<Transaction> workouts) {
        super(context, 0, workouts);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Transaction transaction = (Transaction)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_transaction_workout, parent, false);
        }
        // Lookup view for data population
        TextView tvReps = (TextView) convertView.findViewById(R.id.reps);
        final TextView tvWeight = (TextView) convertView.findViewById(R.id.weight);
        final TextView tvTime = (TextView) convertView.findViewById(R.id.time);
        TextView tvCalos = (TextView) convertView.findViewById(R.id.calos);
        ImageView imgMinusTime = (ImageView)convertView.findViewById(R.id.minusTime);

        //ImageView imgIcon = (ImageView)convertView.findViewById(R.id.icon);
        //ImageView imgChecked = (ImageView)convertView.findViewById(R.id.checked);
        // Populate the data into the template view using the data object
        tvReps.setText(transaction.getRepeat() + " " + getContext().getString(R.string.repeat));
        tvWeight.setText(transaction.getWeight() + " " + getContext().getString(R.string.weight_kg));
        tvTime.setText(transaction.getTime() + " " + getContext().getString(R.string.second));
        tvCalos.setText(transaction.getCalo() + " " + getContext().getString(R.string.calos));


        imgMinusTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.minusTime:
                        String sTime = tvTime.getText().toString();
                        sTime = sTime.substring(0, sTime.length() - 2);
                        float fTime = Float.valueOf(sTime) - 1;
                        WorkoutRunActivity workoutRunActivity = (WorkoutRunActivity) getContext();
                        workoutRunActivity.updateTime(fTime, position);
                        workoutRunActivity.reloadData();
                        v.invalidate();
                        break;
                    default:
                        break;
                }
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }



}
