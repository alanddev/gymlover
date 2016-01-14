package com.alanddev.gymlover.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.controller.WorkoutExerController;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.model.WorkoutExerDetail;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class WorkoutSettingAdapter extends ArrayAdapter<WorkoutExerDetail> {

    private ExcerciseController exerciseController;
    private WorkoutExerController workoutExerController;
    private TextView tvTime;

    public WorkoutSettingAdapter(Context context, List<WorkoutExerDetail> workouts) {
        super(context, 0, workouts);
        exerciseController = new ExcerciseController(context);
        exerciseController.open();
        workoutExerController = new WorkoutExerController(context);
        workoutExerController.open();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position


        final WorkoutExerDetail workout = (WorkoutExerDetail)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_workout_setting, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        tvTime = (TextView) convertView.findViewById(R.id.time);
        ImageView imgIcon = (ImageView)convertView.findViewById(R.id.icon);
        ImageView imgAdd = (ImageView)convertView.findViewById(R.id.add);
        ImageView imgMinus = (ImageView)convertView.findViewById(R.id.minus);
        // Populate the data into the template view using the data object
        int exerId = workout.getExerid();
        Exercise exercise = exerciseController.getById(exerId);
        tvName.setText(exercise.getName());
        tvTime.setText(workout.getTime() + " " + getContext().getString(R.string.second));

        Resources res = convertView.getResources();

        String sImage = exercise.getImage().split(",")[0];
        String srcImg = "ic_ex_" + sImage.toLowerCase();
        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
        Drawable image = convertView.getResources().getDrawable(id);
        imgIcon.setImageDrawable(image);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add:
                        String sTime = tvTime.getText().toString();
                        sTime = sTime.substring(0, sTime.length() - 2);
                        float fTime = Float.valueOf(sTime) + 1;
                        workoutExerController.updateTime(fTime,workout.getWorkid(),workout.getExerid());
                        tvTime.setText(fTime + " " + getContext().getString(R.string.second));
                        notifyDataSetChanged();
                        v.invalidate();
                        break;
                    default:
                        break;
                }

            }
        });

        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.minus:
                        String sTime = tvTime.getText().toString();
                        sTime = sTime.substring(0,sTime.length()-2);
                        float fTime =  Float.valueOf(sTime) - 1;
                        workoutExerController.updateTime(fTime,workout.getWorkid(),workout.getExerid());
                        tvTime.setText(fTime + " " + getContext().getString(R.string.second));
                        notifyDataSetChanged();
                        v.invalidate();
                        break;
                    default:
                        break;
                }

            }
        });

        // Return the completed view to render on screen
//        exerciseController.close();
//        workoutExerController.close();
        return convertView;
    }


}
