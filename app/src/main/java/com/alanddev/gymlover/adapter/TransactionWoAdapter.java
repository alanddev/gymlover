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
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.Workout;
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
        TextView tvWeight = (TextView) convertView.findViewById(R.id.weight);
        TextView tvTime = (TextView) convertView.findViewById(R.id.time);
        TextView tvCalos = (TextView) convertView.findViewById(R.id.calos);

        //ImageView imgIcon = (ImageView)convertView.findViewById(R.id.icon);
        //ImageView imgChecked = (ImageView)convertView.findViewById(R.id.checked);
        // Populate the data into the template view using the data object
        tvReps.setText(transaction.getRepeat() + " " + getContext().getString(R.string.repeat));
        tvWeight.setText(transaction.getWeight() + " " + getContext().getString(R.string.weight_kg));
        tvTime.setText(transaction.getTime() + " " + getContext().getString(R.string.second));
        tvCalos.setText(transaction.getCalo() + " " + getContext().getString(R.string.calos));
//        Resources res = convertView.getResources();
//
//        String srcImg = "ic_workout_" + transaction.getImage().toLowerCase();
//        int id = res.getIdentifier(srcImg, "mipmap", getContext().getPackageName());
//        Drawable image = convertView.getResources().getDrawable(id);
//        imgIcon.setImageDrawable(imag e);
//
//        imgChecked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.checked:
//                        PopupMenu popup = new PopupMenu(getContext(), v);
//                        popup.getMenuInflater().inflate(R.menu.workout_popup,
//                                popup.getMenu());
//                        popup.show();
//                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                switch (item.getItemId()) {
//                                    case R.id.install:
//                                        //Or Some other code you want to put here.. This is just an example.
//                                        Intent intentSetting = new Intent(getContext(),WorkoutSettingActivity.class);
//                                        getContext().startActivity(intentSetting);
////                                        Toast.makeText(getContext(), " Install Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
//                                        break;
//                                    case R.id.addtowishlist:
//                                        Toast.makeText(getContext(), "Add to Wish List Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();
//                                        break;
//                                    default:
//                                        break;
//                                }
//
//                                return true;
//                            }
//                        });
//
//                        break;
//
//                    default:
//                        break;
//                }
//
//            }
//        });
//        if (wallet.getId() == utils.getSharedPreferencesValue(convertView.getContext(), Constant.WALLET_ID)){
//            imgChecked.setImageResource(R.mipmap.ic_check_green);
//        }

        // Return the completed view to render on screen
        return convertView;
    }



}
