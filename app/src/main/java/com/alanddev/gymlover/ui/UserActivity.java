package com.alanddev.gymlover.ui;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alanddev.gymlover.R;

import com.alanddev.gymlover.controller.HistoryController;
import com.alanddev.gymlover.controller.UserController;
import com.alanddev.gymlover.model.History;
import com.alanddev.gymlover.model.User;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


public class UserActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener{

    UserController userController;
    HistoryController historyController;
    Utils utils;
    // Full path of image
    String imagePath = "";
    private int counter = 0;
    private Spinner spinnerGender;
    private ShowcaseView showcaseView;
    private TextView txtBodyFat;
    private TextView tvWeight;
    private TextView txtBirthday;
    private TextView txtHeight;
    private TextView txtName;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_user));

        spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        setDataSpinner();
        txtBodyFat = (TextView)findViewById(R.id.txtFat);
        tvWeight = (TextView)findViewById(R.id.txtWeight);
        txtHeight = (TextView)findViewById(R.id.txtHeight);
        txtName = (TextView)findViewById(R.id.txtName);

        txtBirthday = (TextView)findViewById(R.id.txtdate);
        txtBirthday.setOnClickListener(this);


        userController = new UserController(this);
        historyController = new HistoryController(this);
        userController.open();
        if (userController.getCount() > 0) {
            User user = userController.getId(1);
            txtName.setText(user.getName());
            txtHeight.setText(String.valueOf(user.getHeight()));
            txtBodyFat.setText(String.valueOf(user.getFat()));
            tvWeight.setText(String.valueOf(user.getWeight()));
            txtBirthday.setText(user.getBirthday());
            spinnerGender.setSelection(user.getGender());
        }
        userController.close();
        //userController.open();
        //userController.close();
        utils = new Utils();

    }


    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                UserActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        now.set(2000,1,1);
        dpd.setMaxDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void setDataSpinner(){
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterType);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        if (id == R.id.save){
            saveUser();

        }

        return super.onOptionsItemSelected(item);
    }


    public void saveUser() {
        EditText nameEdit   = (EditText)findViewById(R.id.txtName);
        EditText heightEdit   = (EditText)findViewById(R.id.txtHeight);
        EditText weightEdit   = (EditText)findViewById(R.id.txtWeight);
        EditText fatEdit   = (EditText)findViewById(R.id.txtFat);
        //CheckBox chooseCB = (CheckBox)findViewById(R.id.choose);

        String imageFileName = "";
        String nameUser = nameEdit.getText().toString();


        float fHeight = 0.0f;
        String sHeight = heightEdit.getText().toString();
        if (!sHeight.equals("")|| !sHeight.equals("0")) {
            fHeight = Float.valueOf(sHeight);
        }

        float fWeight = 0.0f;
        String sWeight = weightEdit.getText().toString();
        if (!sWeight.equals("")|| !sWeight.equals("0")) {
            fWeight = Float.valueOf(sWeight);
        }


        float fFat = 0.0f;
        String sFat = fatEdit.getText().toString();
        if (!sFat.equals("")|| !sFat.equals("0")) {
            fFat = Float.valueOf(sFat);
        }


        if (!imagePath.equals("")) {
            File image = new File(imagePath);
            imageFileName = image.getName();
            try {
                utils.copyFile(imagePath, Constant.PATH_IMG + "/" + imageFileName);
            }
            catch (IOException e){};
        }

        String sGender = spinnerGender.getSelectedItem().toString();
        int gender = 1;
        if (sGender == getResources().getString(R.string.gender_boy)){
            gender = Constant.GENDER_BOY;
        }else{
            gender = Constant.GENDER_GIRL;
        }

        if (nameUser.equals("")){
            Toast.makeText(this,getResources().getString(R.string.warning_user_name), Toast.LENGTH_LONG).show();
        }else {
            User newUser = new User(nameUser,txtBirthday.getText().toString(),gender, fHeight, fWeight,fFat, imageFileName);
            //db.createWallet();
            userController.open();
            if (userController.getCount() == 0) {
                User userSaved = (User) userController.create(newUser);
                newUser.setId(userSaved.getId());
            }else{
                User userEdit = (User)userController.getId(1);
                newUser.setId(userEdit.getId());
                userController.update(newUser);
            }
            historyController.open();
            if (historyController.checkDate(new Date())) {
                History hist = new History(newUser.getId(), newUser.getHeight(), newUser.getWeight(), newUser.getFat());
                historyController.create(hist);
            }

            historyController.close();
            userController.close();

            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }



    @Override
    protected void onResume() {
        if(userController!=null) {
            userController.open();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(userController!=null) {
            userController.close();
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            //if (requestCode == Constant.CUR_WALLET_REQUEST) {
            case Constant.BODYFAT_USER_REQUEST:
                if (data != null) {
                    String bodyFat = data.getStringExtra("bodyFat");
                    txtBodyFat.setText(bodyFat);
                }
                break;
            case Constant.GALLERY_USER_REQUEST :
                if (data !=null){
                    Uri selectedImageUri = data.getData();
                    imagePath = utils.getRealPathFromURI(this,selectedImageUri);
                    //Toast.makeText(this,path,Toast.LENGTH_LONG).show();
                    File image = new File(imagePath);
                    ImageView imgUser = (ImageView)findViewById(R.id.imageUser);
                    imgUser.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                }
                break;
            default:
                break;
        }
    }


    public void onClickBodyFat(View v){
        Intent intent = new Intent(this, BodyFatActivity.class);
        int gender = Constant.GENDER_BOY;
        if (spinnerGender.getSelectedItem().toString() == getResources().getString(R.string.gender_girl)){
            gender = Constant.GENDER_GIRL;
        }
        intent.putExtra("gender", gender);
        intent.putExtra("weight", tvWeight.getText().toString());
        startActivityForResult(intent, Constant.BODYFAT_USER_REQUEST);
        //finish();
    }


    public void clickChooseImage(View v){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, Constant.GALLERY_USER_REQUEST);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.txtdate){
            showDatePickerDialog(v);
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int month, int day) {
        String dateStr = day + "/" + (month + 1) + "/" + year;
        txtBirthday.setText(Utils.getDayView(this,Utils.changeStr2Date(dateStr,Constant.DATE_FORMAT_PICKER)));
    }
}
