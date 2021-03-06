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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.ui.ResultWorkoutActivity;
import com.alanddev.gymlover.ui.WorkoutRunActivity;
import com.alanddev.gymlover.ui.WorkoutSettingActivity;
import com.alanddev.gymlover.util.Utils;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class TransactionWoAdapter extends ArrayAdapter<Transaction> {

    private boolean isResult;
    public TransactionWoAdapter(Context context, List<Transaction> workouts) {
        super(context, 0, workouts);

    }

    public void setIsResult(boolean isResult){
        this.isResult = isResult;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Transaction transaction = (Transaction)getItem(position);
        ExcerciseController exerciseController = new ExcerciseController(getContext());
        exerciseController.open();
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_transaction_workout, parent, false);
        }
        // Lookup view for data population
        final TextView tvReps = (TextView) convertView.findViewById(R.id.reps);
        final TextView tvWeight = (TextView) convertView.findViewById(R.id.weight);
        final TextView tvTime = (TextView) convertView.findViewById(R.id.time);
        TextView tvCalos = (TextView) convertView.findViewById(R.id.calos);
        ImageView imgMinusTime = (ImageView)convertView.findViewById(R.id.minusTime);
        ImageView imgMinusWeight = (ImageView)convertView.findViewById(R.id.minusWeight);
        ImageView imgMinusReps = (ImageView)convertView.findViewById(R.id.minusReps);
        ImageView imgAddTime = (ImageView)convertView.findViewById(R.id.addTime);
        ImageView imgAddWeight = (ImageView)convertView.findViewById(R.id.addWeight);
        ImageView imgAddReps = (ImageView)convertView.findViewById(R.id.addReps);
        ImageView imgEx = (ImageView)convertView.findViewById(R.id.exImg);
        //ImageView imgIcon = (ImageView)convertView.findViewById(R.id.icon);
        //ImageView imgChecked = (ImageView)convertView.findViewById(R.id.checked);
        // Populate the data into the template view using the data object
        tvReps.setText(transaction.getRepeat() + " " + getContext().getString(R.string.repeat));
        tvWeight.setText(transaction.getWeight() + " " + getContext().getString(R.string.kg));
        tvTime.setText(transaction.getTime() + " " + getContext().getString(R.string.second));
        tvCalos.setText(transaction.getCalo() + " " + getContext().getString(R.string.calos));



        String strImage = exerciseController.getById(transaction.getExericise()).getImage();
        exerciseController.close();
        String image1 = strImage.split(",")[0];
        if(image1!=null) {
            imgEx.setImageResource(convertView.getResources().getIdentifier("ic_ex_" + image1, "mipmap", getContext().getPackageName()));
        }

        //imgEx.setImageDrawable(image);


        imgMinusTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.minusTime:
                        String sTime = tvTime.getText().toString();
                        sTime = sTime.substring(0, sTime.length() - 2);
                        float fTime = Float.valueOf(sTime) - 5;
                        if (fTime <= 0f){
                            fTime = 0f;
                        }
                        if (!isResult) {
                            WorkoutRunActivity workoutRunActivity = (WorkoutRunActivity) getContext();
                            workoutRunActivity.updateTime(fTime, position);
                            workoutRunActivity.reloadData();
                            v.invalidate();
                        }else{
                            ResultWorkoutActivity resultWorkoutActivity = (ResultWorkoutActivity) getContext();
                            resultWorkoutActivity.updateTime(fTime, position);
                            resultWorkoutActivity.reloadData();
                            v.invalidate();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        imgAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.addTime:
                        String sTime = tvTime.getText().toString();
                        sTime = sTime.substring(0, sTime.length() - 2);
                        float fTime = Float.valueOf(sTime) + 5;
                        if (!isResult) {
                            WorkoutRunActivity workoutRunActivity = (WorkoutRunActivity) getContext();
                            workoutRunActivity.updateTime(fTime, position);
                            workoutRunActivity.reloadData();
                            v.invalidate();
                        }else{
                            ResultWorkoutActivity resultWorkoutActivity = (ResultWorkoutActivity) getContext();
                            resultWorkoutActivity.updateTime(fTime, position);
                            resultWorkoutActivity.reloadData();
                            v.invalidate();
                        }
                        break;
                    default:
                        break;
                }
            }
        });



        imgMinusWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.minusWeight:
                        String sWeight = tvWeight.getText().toString();
                        sWeight = sWeight.substring(0, sWeight.length() - 2);
                        float fWeight = Float.valueOf(sWeight) - 2.5f;
                        if (fWeight <= 0f){
                            fWeight = 0f;
                        }
                        if (!isResult) {
                            WorkoutRunActivity workoutRunActivity = (WorkoutRunActivity) getContext();
                            workoutRunActivity.updateWeight(fWeight, position);
                            workoutRunActivity.reloadData();
                            v.invalidate();
                        }else{
                            ResultWorkoutActivity resultWorkoutActivity = (ResultWorkoutActivity) getContext();
                            resultWorkoutActivity.updateWeight(fWeight, position);
                            resultWorkoutActivity.reloadData();
                            v.invalidate();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        imgAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.addWeight:
                        String sWeight = tvWeight.getText().toString();
                        sWeight = sWeight.substring(0, sWeight.length() - 2);
                        float fWeight = Float.valueOf(sWeight) + 2.5f;
                        if (!isResult) {
                            WorkoutRunActivity workoutRunActivity = (WorkoutRunActivity) getContext();
                            workoutRunActivity.updateWeight(fWeight, position);
                            workoutRunActivity.reloadData();
                            v.invalidate();
                        }else{
                            ResultWorkoutActivity resultWorkoutActivity = (ResultWorkoutActivity) getContext();
                            resultWorkoutActivity.updateWeight(fWeight, position);
                            resultWorkoutActivity.reloadData();
                            v.invalidate();
                        }
                        break;
                    default:
                        break;
                }
            }
        });



        imgMinusReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.minusReps:
                        String sReps = tvReps.getText().toString();
                        sReps = sReps.substring(0, sReps.length() - 4);
                        int fReps = Integer.valueOf(sReps) - 1;
                        if (fReps <= 0){
                            fReps = 0;
                        }
                        if (!isResult) {
                            WorkoutRunActivity workoutRunActivity = (WorkoutRunActivity) getContext();
                            workoutRunActivity.updateReps(fReps, position);
                            workoutRunActivity.reloadData();
                            v.invalidate();
                        }else{
                            ResultWorkoutActivity resultWorkoutActivity = (ResultWorkoutActivity) getContext();
                            resultWorkoutActivity.updateReps(fReps, position);
                            resultWorkoutActivity.reloadData();
                            v.invalidate();
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        imgAddReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.addReps:
                        String sReps = tvReps.getText().toString();
                        sReps = sReps.substring(0, sReps.length() - 4);
                        int fReps = Integer.valueOf(sReps) + 1;
                        if (!isResult) {
                            WorkoutRunActivity workoutRunActivity = (WorkoutRunActivity) getContext();
                            workoutRunActivity.updateReps(fReps, position);
                            workoutRunActivity.reloadData();
                            v.invalidate();
                        }else{
                            ResultWorkoutActivity resultWorkoutActivity = (ResultWorkoutActivity) getContext();
                            resultWorkoutActivity.updateReps(fReps, position);
                            resultWorkoutActivity.reloadData();
                            v.invalidate();
                        }
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
