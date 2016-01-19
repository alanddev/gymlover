package com.alanddev.gymlover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.WorkoutExerDetail;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class ExerciseWoAdapter extends BaseAdapter {
    private List<WorkoutExerDetail> exercises;
    private Context mContext;

    public ExerciseWoAdapter(Context context, List<WorkoutExerDetail> datas){
        exercises=datas;
        mContext=context;
    }

    @Override
    public int getCount() {
        if(exercises!=null){
            return exercises.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(exercises!=null){
            return exercises.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(exercises!=null){
            WorkoutExerDetail detail = (WorkoutExerDetail)exercises.get(position);
            return detail.getExerid();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WorkoutExerDetail detail = (WorkoutExerDetail)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_exercise_wo,null);
            viewHolder = new Viewholder();
            viewHolder.imggrp = ((ImageView) convertView
                    .findViewById(R.id.img_exgrp));
            viewHolder.txtgrp = ((TextView)convertView.findViewById(R.id.txt_exgrp));
            viewHolder.txtrepeat = ((TextView)convertView.findViewById(R.id.txt_repeat));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        String strImage = detail.getExerimg();
        String image1 = strImage.split(",")[0];
        if(image1!=null) {
            viewHolder.imggrp.setImageResource(mContext.getResources().getIdentifier("ic_ex_" + image1, "mipmap", mContext.getPackageName()));
        }
        viewHolder.txtgrp.setText(detail.getExername());
        viewHolder.txtrepeat.setText(detail.getRepeat());
        return convertView;
    }

    class Viewholder {
        public ImageView imggrp;
        public TextView txtgrp;
        public TextView txtrepeat;

        Viewholder() {
        }
    }
}
