package com.alanddev.gymlover.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.alanddev.gymlover.R;
import com.alanddev.gymlover.adapter.SellectThemeAdapter;
import com.alanddev.gymlover.controller.ExcerciseController;
import com.alanddev.gymlover.controller.ExcerciseGroupController;
import com.alanddev.gymlover.model.Theme;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        Utils.setLanguage(this);
        setContentView(R.layout.activity_select_theme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView lvTheme = (ListView)findViewById(R.id.lstthemes);
        List<Theme> datas;
        int type = getIntent().getExtras().getInt("SETTING_EXTRA",0);
        if(type==Constant.CHANGE_THEME_ID){
            datas=createThemeData();
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_select_theme));
        }else if(type==Constant.CHANGE_NAV_ID) {
            datas = createHeaderData();
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_select_menu));
        }else if(type == Constant.CHANGE_LANGUAGE_ID){
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_select_language));
            datas = createLanguageData();
        }else{
            datas= new ArrayList<>();
        }
        final SellectThemeAdapter adapter = new SellectThemeAdapter(this,datas);
        lvTheme.setAdapter(adapter);
        lvTheme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Theme theme = (Theme)parent.getAdapter().getItem(position);
                if(theme.getHeader()!=null&&!theme.getHeader().equals("")){
                    Utils.setSharedPreferencesValue(getApplicationContext(), Constant.NAV_HEADER_CURRENT, theme.getHeader());
                }else if(theme.getColor()!=null&&!theme.getColor().equals("")) {
                    Utils.setSharedPreferencesValue(getApplicationContext(), Constant.THEME_CURRENT, theme.getTheme());
                    Utils.changeToTheme(theme.getTheme());
                }else if(theme.getLanguage()!=null&&!theme.getLanguage().equals("")) {
                    Utils.setSharedPreferencesValue(getApplicationContext(), Constant.LANGUAGE_CURRENT, theme.getLanguage());
                    Utils.setLanguage(getApplicationContext(),theme.getLanguage());
                    int isSetup = getIntent().getExtras().getInt("SETTING_FIRST",0);
                    ExcerciseGroupController groupController = new ExcerciseGroupController(getApplicationContext());
                    ExcerciseController exController = new ExcerciseController(getApplicationContext());
                    groupController.open();
                    exController.open();
                    if (isSetup > 0){
                        groupController.init();
                        exController.init();
                        Intent intent = new Intent(SelectThemeActivity.this, UserActivity.class);
                        startActivity(intent);
                    }else{
                        groupController.delete();
                        groupController.init();
                        exController.delete();
                        exController.init();
                    }
                    groupController.close();
                    exController.close();
                    //Utils.changeToLanguage(theme.getLanguage());
                }
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
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
        return super.onOptionsItemSelected(item);
    }

    private List<Theme> createThemeData(){
        List<Theme> lstTheme = new ArrayList<Theme>();
        String[] arrThemes = getResources().getStringArray(R.array.theme_array);
        String[] arrColors = getResources().getStringArray(R.array.color_array);
        if(arrThemes!=null&&arrThemes.length>0) {
            for (int i = 0; i < arrThemes.length;i++) {
                Theme theme = new Theme();
                theme.setTheme(arrThemes[i]);
                theme.setColor(arrColors[i]);
                lstTheme.add(theme);
            }
        }
        return lstTheme;
    }

    private List<Theme> createHeaderData(){
        List<Theme> lstTheme = new ArrayList<Theme>();
        String[] arrHeaders = getResources().getStringArray(R.array.header_images);
        if(arrHeaders!=null&&arrHeaders.length>0) {
            for (int i = 0; i < arrHeaders.length;i++) {
                Theme theme = new Theme();
                theme.setHeader(arrHeaders[i]);
                lstTheme.add(theme);
            }
        }
        return lstTheme;
    }

    private List<Theme> createLanguageData(){
        List<Theme> lstTheme = new ArrayList<Theme>();
        String[] arrLanguage = getResources().getStringArray(R.array.language);
        String[] arrLanguageName = getResources().getStringArray(R.array.language_name);
        if(arrLanguage!=null&&arrLanguage.length>0) {
            for (int i = 0; i < arrLanguage.length;i++) {
                Theme theme = new Theme();
                theme.setLanguage(arrLanguage[i]);
                theme.setContent(arrLanguageName[i]);
                lstTheme.add(theme);
            }
        }
        return lstTheme;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
    }
}
