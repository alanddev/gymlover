package com.alanddev.gymlover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.model.WorkoutExerWeek;
import com.alanddev.gymlover.util.Utils;
import com.foound.widget.AmazingAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 12/01/2016.
 */
public class WorkoutExerAdapter extends AmazingAdapter {

    private List<WorkoutExerWeek> datas;
    private LayoutInflater inflate;
    private Context mContext;

    public WorkoutExerAdapter(Context context,LayoutInflater inflate,List<WorkoutExerWeek> datas){
        this.datas = datas;
        this.inflate = inflate;
        this.mContext = context;
    }
    @Override
    protected void onNextPageRequested(int page) {

    }

    @Override
    protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
        View header = view.findViewById(R.id.header);
        if (displaySectionHeader) {
            header.setVisibility(View.VISIBLE);
            WorkoutExerWeek exerWeek = datas.get(getSectionForPosition(position));
            int week = exerWeek.getWeek();
            TextView txtDate = (TextView) header.findViewById(R.id.txtdate);
        } else {
            header.setVisibility(View.GONE);
        }
    }

    @Override
    public View getAmazingView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {

    }

    @Override
    public int getPositionForSection(int section) {
        if (section < 0)
            section = 0;
        if (section >= datas.size())
            section = datas.size() - 1;
        int c = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (section == i) {
                return c;
            }
            c += datas.get(i).getItems().size();
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        int c = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (position >= c && position < c + datas.get(i).getItems().size()) {
                return i;
            }
            c += datas.get(i).getItems().size();
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getCount() {
        int res = 0;
        for (int i = 0; i < datas.size(); i++) {
            res += datas.get(i).getItems().size();
        }
        return res;
    }

    @Override
    public Object getItem(int position) {
        int c = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (position >= c && position < c + datas.get(i).getItems().size()) {
                return datas.get(i).getItems().get(position - c);
            }
            c += datas.get(i).getItems().size();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
