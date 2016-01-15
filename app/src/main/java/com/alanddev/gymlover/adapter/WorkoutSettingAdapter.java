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
import com.alanddev.gymlover.ui.WorkoutSettingActivity;

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

        final WorkoutHolderView holder;
        final WorkoutExerDetail workout = (WorkoutExerDetail)getItem(position);
        View row = convertView;
        // Check if an existing view is being reused, otherwise inflate the view
        if (row == null) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_item_workout_setting, parent, false);
            holder = new WorkoutHolderView();
            holder.mTimeText = (TextView) row.findViewById(R.id.time);
            holder.mNameText = (TextView) row.findViewById(R.id.name);
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);

            holder.imgAdd = (ImageView)row.findViewById(R.id.add);
            holder.imgMinus = (ImageView)row.findViewById(R.id.minus);
            holder.imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.add:
                            String sTime = holder.mTimeText.getText().toString();
                            sTime = sTime.substring(0, sTime.length() - 2);
                            float fTime = Float.valueOf(sTime) + 1;
                            workoutExerController.updateTime(fTime, workout.getWorkid(), workout.getExerid());
//                            Intent intent = new Intent(getContext(),WorkoutSettingActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            WorkoutSettingActivity workoutSettingActivity = (WorkoutSettingActivity)getContext();
                            //workoutSettingActivity.startActivity(intent);
                            //workoutSettingActivity.finish();
                            workoutSettingActivity.reloadData();
                            break;
                        default:
                            break;
                    }

                }
            });

            holder.imgMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.minus:
                            String sTime = holder.mTimeText.getText().toString();
                            sTime = sTime.substring(0, sTime.length() - 2);
                            float fTime = Float.valueOf(sTime) - 1;
                            workoutExerController.updateTime(fTime, workout.getWorkid(), workout.getExerid());
                            WorkoutSettingActivity workoutSettingActivity = (WorkoutSettingActivity)getContext();
                            workoutSettingActivity.reloadData();
                            v.invalidate();
                            break;
                        default:
                            break;
                    }

                }
            });
            row.setTag(holder);
        }else{
            holder = (WorkoutHolderView)row.getTag();
        }

        // Lookup view for data population
        // Populate the data into the template view using the data object
        int exerId = workout.getExerid();
        Exercise exercise = exerciseController.getById(exerId);
        holder.mNameText.setText(exercise.getName());
        holder.mTimeText.setText(workout.getTime() + " " + getContext().getString(R.string.second));

        Resources res = row.getResources();

        String sImage = exercise.getImage().split(",")[0];
        String srcImg = "ic_ex_" + sImage.toLowerCase();
        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
        Drawable image = row.getResources().getDrawable(id);
        holder.imgIcon.setImageDrawable(image);



        // Return the completed view to render on screen
//        exerciseController.close();
//        workoutExerController.close();
        return row;
    }

    class WorkoutHolderView{
        TextView mTimeText;
        TextView mNameText;
        ImageView imgAdd;
        ImageView imgMinus;
        ImageView imgIcon;

    }


}
