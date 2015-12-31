package com.alanddev.gymlover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.model.ExcerciseGroup;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Setting;
import com.alanddev.gymlover.model.Theme;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class ExerciseGrpAdapter extends BaseAdapter {
    private List<Model> exerGrps;
    private Context mContext;

    public ExerciseGrpAdapter(Context context, List<Model> datas){
        exerGrps=datas;
        mContext=context;
    }

    @Override
    public int getCount() {
        if(exerGrps!=null){
            return exerGrps.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(exerGrps!=null){
            return exerGrps.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExcerciseGroup exGrp = (ExcerciseGroup)this.getItem(position);
        Viewholder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_exercisegrp,null);
            viewHolder = new Viewholder();
            viewHolder.imggrp = ((ImageView) convertView
                    .findViewById(R.id.img_exgrp));
            viewHolder.txtgrp = ((TextView)convertView.findViewById(R.id.txt_title));

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Viewholder) convertView.getTag();
        }
        viewHolder.imggrp.setImageResource(mContext.getResources().getIdentifier("ic_exgrp_"+exGrp.getImage(),"mipmap",mContext.getPackageName()));
        viewHolder.txtgrp.setText(exGrp.getName());
        return convertView;
    }

    class Viewholder {
        public ImageView imggrp;
        public TextView txtgrp;

        Viewholder() {
        }
    }
}
