package com.alanddev.gymlover.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alanddev.gymlover.fragment.TransactionFragment;
import com.alanddev.gymlover.model.Transactions;

import java.util.List;


/**
 * Created by ANLD on 18/11/2015.
 */
public class TransSectionPagerAdapter extends FragmentStatePagerAdapter {
    private List<Transactions> datas;
    public TransSectionPagerAdapter(FragmentManager fm, List<Transactions> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = TransactionFragment.newInstance(position+1,datas);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }




    @Override
    public int getCount() {
        // Show 3 total pages.
        if(datas!=null&&datas.size()>0) {
            return 3;
        }else{
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getTitle();
    }

    public void setData(List<Transactions> datas){
        this.datas = datas;
    }
}
