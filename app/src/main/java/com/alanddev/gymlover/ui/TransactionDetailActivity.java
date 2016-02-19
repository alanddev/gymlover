package com.alanddev.gymlover.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.controller.TransactionController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class TransactionDetailActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private TransactionController controller;
    private Transaction transaction;
    private TextView edtDate;
    private TextView edtCate;
    private ImageView imgminus;
    private ImageView imgadd;
    private EditText edtrepeat;
    private EditText edtweight;
    private EditText edttime;
    private Exercise exercise;
    private Float weight_base = 2.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_transaction_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_transaction_add);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_transaction_add));
        long transId = getIntent().getExtras().getLong(MwSQLiteHelper.COLUMN_TRANS_ID, 0);
        transaction = getTransaction(transId);
        edtDate = (TextView) findViewById(R.id.edtdate);
        edtDate.setOnClickListener(this);
        edtDate.setText(Utils.changeDateStr2Str2(transaction.getDate()));

        edtCate = (TextView)findViewById(R.id.edtcate);
        edtCate.setOnClickListener(this);
        edtCate.setText(transaction.getExer_name());

        imgminus = (ImageView)findViewById(R.id.imgminus);
        imgminus.setOnClickListener(this);
        imgadd = (ImageView)findViewById(R.id.imgadd);
        imgadd.setOnClickListener(this);
        edtrepeat = (EditText)findViewById(R.id.edtrepeat);
        edtrepeat.setText(transaction.getRepeat());
        edtweight = (EditText)findViewById(R.id.edtweight);
        edtweight.setText(transaction.getWeight()+"");
        edttime = (EditText)findViewById(R.id.edttime);
        edttime.setText(transaction.getTime()+"");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String dateStr = day + "/" + (month + 1) + "/" + year;
        edtDate.setText(Utils.getDayView(this, Utils.changeStr2Date(dateStr, Constant.DATE_FORMAT_PICKER)));
    }

    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                TransactionDetailActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trans_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        if (id == R.id.action_delete) {
            openConfirmDialog();
        }

        if (id == R.id.action_edit) {
            if(changeData()) {
                saveTransaction();
                setResult(Constant.TRANS_DETAIL_UPDATE);
                finish();
            }else{
                Toast.makeText(this, getResources().getText(R.string.check_change_data), Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public Transaction getTransaction(long transId){
        controller.open();
        Transaction transactionDetail = controller.getTransbyId(transId);
        controller.close();
        return transactionDetail;
    }

    public void openConfirmDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.dialog_confirm_delete_trans));

        alertDialogBuilder.setPositiveButton(getString(R.string.dialog_yes_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                delete(transaction.getId());
                setResult(Constant.TRANS_DETAIL_UPDATE);
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.dialog_no_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //don't do anything
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private Boolean changeData(){

        return true;
    }

    private void saveTransaction(){

    }

    private void delete(long transId){
        controller.open();
        controller.delete(transId);
        controller.close();
    }
}
