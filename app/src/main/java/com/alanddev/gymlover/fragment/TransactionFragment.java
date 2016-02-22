package com.alanddev.gymlover.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.TransactionAdapter;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.TransactionDay;
import com.alanddev.gymlover.model.Transactions;
import com.alanddev.gymlover.ui.ReportActivity;
import com.alanddev.gymlover.ui.TransactionDetailActivity;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;
import com.foound.widget.AmazingListView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by ANLD on 18/11/2015.
 */
public class TransactionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static  List<Transactions> transactionses;
    public TransactionFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TransactionFragment newInstance(int sectionNumber,List<Transactions> datas) {
        TransactionFragment fragment = new TransactionFragment();
        transactionses = datas;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trans_fragment_tabbed, container, false);
        Integer sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        final Transactions transactions;
        if(transactionses!=null&&transactionses.size()>=sectionNumber) {
            transactions = transactionses.get(sectionNumber - 1);
        }else{
            transactions = new Transactions();
        }
        AmazingListView lsComposer = (AmazingListView) rootView.findViewById(R.id.lsttransaction);
        final TransactionAdapter adapter = new TransactionAdapter(getActivity().getApplicationContext(), inflater, transactions.getItems());
        lsComposer.setAdapter(adapter);

        View header = inflater.inflate(R.layout.trans_header_list, null, false);
        if(transactions.getItems()!=null&&transactions.getItems().size()>0){
            TextView txtInflowAmt = (TextView)header.findViewById(R.id.txtInflowAmt);
            Float fInAmount = transactions.getCalo();

            NumberFormat formatter = new DecimalFormat("###,###,###,###.##");
            String sInAmount =  formatter.format(fInAmount);

            txtInflowAmt.setText(sInAmount + " calo");

            lsComposer.addHeaderView(header);
        }

        lsComposer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                Transaction transaction= (Transaction)adapter.getItem(position-1);
                Intent intent = new Intent(getContext(), TransactionDetailActivity.class);
                intent.putExtra(MwSQLiteHelper.COLUMN_TRANS_ID,transaction.getId());
                startActivityForResult(intent, Constant.TRANS_DETAIL_REQUEST);
            }
        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (transactions.getItems() != null && transactions.getItems().size() > 0) {
                    TransactionDay transactionDay = transactions.getItems().get(0);
                    if (transactionDay.getItems() != null && transactionDay.getItems().size() > 0) {
                        Transaction transactionDetail = transactionDay.getItems().get(0);
                        SharedPreferences mShaPref = Utils.getSharedPreferences(getContext());
                        int viewtype = mShaPref.getInt(Constant.VIEW_TYPE, 0);
                        Intent intent = new Intent(getContext(), ReportActivity.class);
                        intent.putExtra(Constant.VIEW_TYPE, viewtype);
                        intent.putExtra(Constant.PUT_EXTRA_DATE, transactionDetail.getDate());
                        startActivity(intent);
                    }
                }
            }
        });
        return rootView;
    }
}




