package com.alanddev.gymlover.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ANLD on 24/11/2015.
 */
public class Utils {

    private static int wallet_id;

    private static Locale locale;
    private static Locale localDefault = new Locale("vi","VI");
    private static String sTheme;
    private static ArrayList<Transaction> listResult = new ArrayList<>();

    public static Locale getLocale() {
        if(locale==null){
            return localDefault;
        }
        return locale;
    }

    public static void setLocale(Locale locale) {
        Utils.locale = locale;
    }

    public static String getFormatCurrency(String sAmount){
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        String separator = String.valueOf(format.getDecimalFormatSymbols().getDecimalSeparator());
        DecimalFormat formatter;
        String value;
        if (separator.equals(",")){
            value = sAmount.replaceAll("[.]", "");
        }else{
            value = sAmount.replaceAll(",", "");
        }
        return value;
    }


    public static void setLanguage(Context context){
        Configuration configuration;
        Resources res = context.getResources();
        configuration = new Configuration(res.getConfiguration());
        //configuration.locale=getLocale();
        String lang = getCurrentLanguage(context);
        if (lang ==""){lang="en";}
        Locale locale = new Locale(lang);
        configuration.locale = locale;
        setLocale(locale);
        res.updateConfiguration(configuration,res.getDisplayMetrics());
    }

    public static void setLanguage(Context context, String language){
        Configuration configuration;
        Resources res = context.getResources();
        configuration = new Configuration(res.getConfiguration());

        Locale locale = new Locale(language);
        configuration.locale=locale;
        setLocale(locale);
        res.updateConfiguration(configuration,res.getDisplayMetrics());
    }

    public static ArrayList<Transaction> getListResult(){
        return listResult;
    }



    public static void setListResult(ArrayList<Transaction> list){
        listResult = list;
    }

    public static void addListResult(Transaction tran){
        listResult.add(tran);
    }

    public static void addListResult(ArrayList<Transaction> trans){
        for (int i = 0 ; i <trans.size(); i++){
         listResult.add(trans.get(i));
        }
    }


    public static void emptyListResult(){
        listResult = new ArrayList<>();
    }

    public static String changeDateStr2Str(String dateStr){
        DateFormat fromFormat = new SimpleDateFormat(Constant.DATE_FORMAT_PICKER);
        fromFormat.setLenient(false);
        DateFormat toFormat = new SimpleDateFormat(Constant.DATE_FORMAT_DB);
        toFormat.setLenient(false);
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return toFormat.format(date);
    }

    public void createFile(String folder, String fileName){
        File directory = new File("/sdcard/manwal/" + folder);
        directory.mkdirs();
        File outputFile = new File(directory, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public static void createFolder(String folder){
        File directory = new File(folder);
        directory.mkdirs();
    }


    public static void copyFile(String sourcePath, String destPath)
            throws IOException {
        File source = new File(sourcePath);
        File dest = new File(destPath);
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }



    public String getRealPathFromURI(Context context,Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String changeDate2Str(Date date){
        DateFormat toFormat = new SimpleDateFormat(Constant.DATE_FORMAT_DB);
        toFormat.setLenient(false);
        return toFormat.format(date);
    }

    public static String changeDate2Str(Date date,String dateformat){
        DateFormat toFormat = new SimpleDateFormat(dateformat);
        toFormat.setLenient(false);
        return toFormat.format(date);
    }

    public static Date changeStr2Date(String dateStr,String dateformat){
        DateFormat fromFormat = new SimpleDateFormat(dateformat);
        fromFormat.setLenient(false);
        try {
            return fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date changeDate2Date(Date date,String datefomat){
        String strDate = changeDate2Str(date,datefomat);
        return changeStr2Date(strDate, datefomat);
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME, context.MODE_PRIVATE);
    }

    public static void setSharedPreferencesValue(Context context, String name, int value){
        SharedPreferences sharedPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static void setSharedPreferencesValue(Context context, String name, String value){
        SharedPreferences sharedPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static int getSharedPreferencesValue(Context context,String name) {
        SharedPreferences sharedPref = getSharedPreferences(context);
        int defaultValue = 0;
        int getValue = sharedPref.getInt(name, defaultValue);
        return getValue;
    }


    public static int getWallet_id() {
        return wallet_id;
    }

    public static void setWallet_id(int wallet_id) {
        Utils.wallet_id = wallet_id;
    }

    public static Date getYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        return changeDate2Date(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static Date getTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        return changeDate2Date(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static Date getToday(){
        Calendar calendar = Calendar.getInstance();
        return changeDate2Date(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getStrToday(){
        Calendar calendar = Calendar.getInstance();
        return changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getStrYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        return changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getStrTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        return changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getDayView(Context context, Date date){
        String[] dayview = context.getResources().getStringArray(R.array.day_view);
        if(date.compareTo(getToday())==0){
            return dayview[1];
        }else if(date.compareTo(getYesterday())==0){
            return dayview[0];
        }else if(date.compareTo(getTomorrow())==0){
            return dayview[2];
        }else{
            return changeDate2Str(date, Constant.DATE_FORMAT_PICKER);
        }
    }

    public static String getDatefromDayView(Context context,String dateStr){
        String[] dayview = context.getResources().getStringArray(R.array.day_view);
        String strRes;
        if(dateStr.equals(dayview[0])){
            // Yesterday
            strRes = getStrYesterday();
        }else if(dateStr.equals(dayview[1])){
            //Today
            strRes = getStrToday();
        }else if(dateStr.equals(dayview[2])){
            //Tomorrow
            strRes = getStrTomorrow();
        }else{
            return changeDateStr2Str(dateStr);
        }
        return strRes;
    }

    public static String changeDateStr2Str2(String dateStr){
        DateFormat fromFormat = new SimpleDateFormat(Constant.DATE_FORMAT_DB);
        fromFormat.setLenient(false);
        DateFormat toFormat = new SimpleDateFormat(Constant.DATE_FORMAT_PICKER);
        toFormat.setLenient(false);
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return toFormat.format(date);
    }

    public static void changeToTheme(String theme)
    {
        sTheme = theme;
    }


    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        if(sTheme!=null&&!sTheme.equals("AppTheme")) {
            activity.setTheme(activity.getResources().getIdentifier(sTheme, "style", activity.getPackageName()));
        }
    }

    public static String getYear(){
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        return Integer.toString(thisYear);
    }


    public static String getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return String.valueOf(year);
    }



    public static ArrayList<Date> getDateOfWeek(Date date){
        //Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        Date weekStart = c.getTime();
        // we do not need the same day a week after, that's why use 6, not 7
        c.add(Calendar.DAY_OF_MONTH, 6);
        Date weekEnd = c.getTime();
        ArrayList<Date>dates = new ArrayList<Date>();
        dates.add(weekStart);
        dates.add(weekEnd);
        return dates;
    }

    public static ArrayList<Date>getDateOfMonth(Date date){
        Calendar c = Calendar.getInstance();   // this takes current date
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = c.getTime();

        //c.set(Calendar.DATE, 1);
        //c.add(Calendar.DATE, -1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date monthEnd = c.getTime();
        ArrayList<Date>dates = new ArrayList<Date>();
        dates.add(monthStart);
        dates.add(monthEnd);
        //System.out.println(c.getTime());
        return dates;

    }


    public static ArrayList<Date>getDateOfMonths(int fromMonth, int toMonth, String year){
        String beginDateOfMonth = year + "-" + fromMonth + "-01";
        Date dateStart = Utils.changeStr2Date(beginDateOfMonth, Constant.DATE_FORMAT_DB);

        String endDateOfMonth = year + "-" + toMonth + "-01";
        Date dateTo = Utils.changeStr2Date(endDateOfMonth, Constant.DATE_FORMAT_DB);
        Calendar c = Calendar.getInstance();   // this takes current date
        c.setTime(dateTo);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
        Date dateEnd = c.getTime();

        ArrayList<Date>dates = new ArrayList<Date>();
        dates.add(dateStart);
        dates.add(dateEnd);
        //System.out.println(c.getTime());
        return dates;

    }





    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

    public static void refresh(Activity activity){
        activity.finish();
        activity.startActivity(activity.getIntent());
    }

    public static String getCurrentTheme(Context context){
        SharedPreferences mshare = Utils.getSharedPreferences(context);
        return mshare.getString(Constant.THEME_CURRENT,"");
    }

    public static String getCurrentNavHeader(Context context){
        SharedPreferences mshare = Utils.getSharedPreferences(context);
        return mshare.getString(Constant.NAV_HEADER_CURRENT,"");
    }

    public static String getCurrentLanguage(Context context){
        SharedPreferences mshare = Utils.getSharedPreferences(context);
        return mshare.getString(Constant.LANGUAGE_CURRENT,"");
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    // size waist -- > YCMA formula;
    // way 2 is Navy body fat;
    public static float calculatorBodyFat(float weight, float sizeRoundTwo, int gender){
        float result ;
        sizeRoundTwo = sizeRoundTwo/2.54f; // convert cm --> to inches; 1inches = 2.54 cm;
        weight = weight * 2.2f; // 1kg = 2.2 pounds

        if (gender == Constant.GENDER_BOY){
            result = (-98.42f + (4.15f * sizeRoundTwo) - (0.082f *weight))/weight ;
        }else{
            result = (-76.76f + (4.15f * sizeRoundTwo) - (0.082f * weight))/weight;
        }
        return Math.round(result * 100);
    }


    // base 150 lbs ( ~ 68 kg) in 60 minutes.
    public static float calculatorCalo(float weight, float time, float caloBase){
        float result;
        result = (((weight*Constant.KG_TO_LB/150f)* caloBase)/3600f)*time;
        return Math.round(result);
    }


}
