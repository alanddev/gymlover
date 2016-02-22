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
import com.alanddev.gymlover.model.TransactionSumGroup;
import com.alanddev.gymlover.model.Workout;
import com.alanddev.gymlover.model.WorkoutExerDetail;
import com.alanddev.gymlover.ui.WorkoutSettingActivity;

import java.util.List;

/**
 * Created by ANLD on 31/12/2015.
 */
public class TransSumGrpAdapter extends ArrayAdapter<TransactionSumGroup> {




    public TransSumGrpAdapter(Context context, List<TransactionSumGroup> transactionSumGroups) {
        super(context, 0, transactionSumGroups);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        TransactionSumGroup transactionSumGroup = (TransactionSumGroup) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_transaction_sum, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.txtName);
        //TextView tvTime = (TextView) convertView.findViewById(R.id.txtTime);
        TextView tvWeight = (TextView) convertView.findViewById(R.id.txtWeight);
        TextView tvCalos = (TextView) convertView.findViewById(R.id.txtCalos);
        ImageView imgGrp = (ImageView) convertView.findViewById(R.id.grpImg);

        // Populate the data into the template view using the data object
        tvName.setText(transactionSumGroup.getName());
        //tvTime.setText(transactionSumGroup.getTime() + " " + getContext().getString(R.string.time));
        tvWeight.setText(transactionSumGroup.getWeight() + " " + getContext().getString(R.string.kg));
        tvCalos.setText(transactionSumGroup.getCalo() + " " + getContext().getString(R.string.calos));

        Resources res = convertView.getResources();
        String srcImg = "ic_exgrp_" + transactionSumGroup.getImage().toLowerCase();
        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
        Drawable image = convertView.getResources().getDrawable(id);
        imgGrp.setImageDrawable(image);

        // Return the completed view to render on screen
        return convertView;
    }


}
