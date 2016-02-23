package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alanddev.gymlover.R;
import com.alanddev.gymlover.controller.TransactionController;
import com.alanddev.gymlover.controller.UserController;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.User;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

public class TransactionAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private TextView edtDate;
    private TextView edtCate;
    private EditText edtrepeat;
    private EditText edtweight;
    private EditText edttime;
    private ImageView imgcate;
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
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_transaction_add));
        edtDate = (TextView) findViewById(R.id.edtdate);
        edtDate.setOnClickListener(this);
        edtCate = (TextView)findViewById(R.id.edtcate);
        edtCate.setOnClickListener(this);
        edtrepeat = (EditText)findViewById(R.id.edtrepeat);
        edtweight = (EditText)findViewById(R.id.edtweight);
        edttime = (EditText)findViewById(R.id.edttime);
        imgcate = (ImageView)findViewById(R.id.imgcate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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

        if (id == R.id.save) {
            saveTransaction();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String dateStr = day + "/" + (month + 1) + "/" + year;
        edtDate.setText(Utils.getDayView(this, Utils.changeStr2Date(dateStr, Constant.DATE_FORMAT_PICKER)));
    }

    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                TransactionAddActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.edtcate){
            Intent intent = new Intent(getApplicationContext(),ExerciseGrpActivity.class);
            intent.putExtra(Constant.TAKE_EXERCISE,1);
            startActivityForResult(intent, Constant.PICK_EXERCISE);
        }
        if(v.getId()==R.id.edtdate){
            showDatePickerDialog(v);
        }
    }

    private void saveTransaction() {
        if(edtCate.getText().toString().equals("")||exercise==null){
            Toast.makeText(this, getResources().getText(R.string.check_exercise_exist), Toast.LENGTH_LONG).show();
        }else if(edttime.getText().toString().equals("")){
            Toast.makeText(this, getResources().getText(R.string.check_time_exist), Toast.LENGTH_LONG).show();
        }else{
            Float bodyweight = 0.0f;
            UserController userController = new UserController(this);
            userController.open();
            List<Model> listUser = userController.getAll();
            if(listUser!=null&&listUser.size()>0){
                User user = (User)listUser.get(0);
                bodyweight=user.getWeight();
            }
            userController.close();
            TransactionController controller = new TransactionController(this);
            controller.open();
            Transaction transaction = new Transaction();
            transaction.setExericise(exercise.getId());
            transaction.setDate(Utils.getDatefromDayView(this, edtDate.getText().toString()));
            transaction.setWeight(Float.valueOf(edtweight.getText().toString()));
            transaction.setTime(Float.valueOf(edttime.getText().toString()));
            transaction.setRepeat(Integer.valueOf(edtrepeat.getText().toString()));
            transaction.setCalo(Utils.calculatorCalo(bodyweight, transaction.getTime(), exercise.getCalo()));
            controller.create(transaction);
            controller.close();
            setResult(Constant.ADD_TRANSACTION_SUCCESS, new Intent());
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==Constant.PICK_EXERCISE)
        {
            if(data!=null) {
                exercise = new Exercise();
                String cateName = data.getStringExtra(MwSQLiteHelper.COLUMN_EXCERCISE_NAME);
                exercise.setName(cateName);
                exercise.setCalo(data.getFloatExtra(MwSQLiteHelper.COLUMN_EXCERCISE_CALO, 0.0f));
                exercise.setId(data.getIntExtra(MwSQLiteHelper.COLUMN_EXCERCISE_ID, 0));
                edtCate.setText(cateName);
                String imgs = data.getStringExtra(MwSQLiteHelper.COLUMN_EXCERCISE_IMAGE);
                String[] lstImgs = imgs.split(",");
                if(lstImgs.length>=1){
                    imgcate.setImageResource(getResources().getIdentifier("ic_ex_" + lstImgs[0], "mipmap", getPackageName()));
                }
            }
        }
    }
}
