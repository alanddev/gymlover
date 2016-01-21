package com.alanddev.gymlover.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.helper.IDataSource;
import com.alanddev.gymlover.helper.MwSQLiteHelper;
import com.alanddev.gymlover.model.Exercise;
import com.alanddev.gymlover.model.Model;
import com.alanddev.gymlover.model.Transaction;
import com.alanddev.gymlover.model.TransactionDay;
import com.alanddev.gymlover.model.Transactions;
import com.alanddev.gymlover.util.Constant;
import com.alanddev.gymlover.util.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ANLD on 30/12/2015.
 */
public class TransactionController implements IDataSource {
    private SQLiteDatabase database;
    private MwSQLiteHelper dbHelper;
    private Context mContext;

    private String [] allColumns = {
            MwSQLiteHelper.COLUMN_TRANS_ID,
            MwSQLiteHelper.COLUMN_TRANS_EXERCISE,
            MwSQLiteHelper.COLUMN_TRANS_DATE,
            MwSQLiteHelper.COLUMN_TRANS_REPEAT,
            MwSQLiteHelper.COLUMN_TRANS_WEIGHT,
            MwSQLiteHelper.COLUMN_TRANS_TIME,
            MwSQLiteHelper.COLUMN_TRANS_CALO,
            MwSQLiteHelper.COLUMN_TRANS_NOTE
    };

    public TransactionController(Context context){
        dbHelper = new MwSQLiteHelper(context);
        this.mContext = context;
    }

    @Override
    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public Model create(Model data) {
        ContentValues values = new ContentValues();
        Transaction transaction    = (Transaction)data;
        values.put(MwSQLiteHelper.COLUMN_TRANS_EXERCISE, transaction.getExericise());
        values.put(MwSQLiteHelper.COLUMN_TRANS_DATE, transaction.getDate());
        values.put(MwSQLiteHelper.COLUMN_TRANS_REPEAT, transaction.getRepeat());
        values.put(MwSQLiteHelper.COLUMN_TRANS_WEIGHT, transaction.getWeight());
        values.put(MwSQLiteHelper.COLUMN_TRANS_TIME, transaction.getTime());
        values.put(MwSQLiteHelper.COLUMN_TRANS_CALO, transaction.getCalo());
        values.put(MwSQLiteHelper.COLUMN_TRANS_NOTE, transaction.getNote());

        database.insert(MwSQLiteHelper.TABLE_EXCERCISE, null,
                values);
        return transaction;
    }

    @Override
    public void update(Model data) {

    }

    @Override
    public int getCount() {
        String countQuery = "SELECT  * FROM " + MwSQLiteHelper.TABLE_TRANSACTION;
        Cursor cursor = database.rawQuery(countQuery, null);
        int count  = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    public List<Model> getAll() {
       return new ArrayList<Model>();
    }

    @Override
    public Model get(String query) {
        return null;
    }

    @Override
    public List<Model> getAll(String query) {
        return null;
    }

    @Override
    public Model cursorTo(Cursor cursor) {
        Transaction transaction = new Transaction();
        try {
            transaction.setId(cursor.getInt(0));
            transaction.setExericise(cursor.getInt(1));
            transaction.setDate(cursor.getString(2));
            transaction.setRepeat(cursor.getInt(3));
            transaction.setWeight(cursor.getFloat(4));
            transaction.setTime(cursor.getFloat(5));
            transaction.setCalo(cursor.getFloat(6));
            transaction.setNote(cursor.getString(7));
            transaction.setExer_name(cursor.getString(9));
            transaction.setExer_desc(cursor.getString(10));
            transaction.setExer_img(cursor.getString(11));
        }catch (Exception ex){
            //don't do anything
        }
        return transaction;
    }




    @Override
    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_TRANSACTION, null, null);
    }

    public List<Transactions> getAll(int viewtype) {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        switch (viewtype) {
            case Constant.VIEW_TYPE_DAY:
                transactionses = getTransbyDay();
                break;
            case Constant.VIEW_TYPE_WEEK:
                transactionses = getTransbyWeek();
                break;
            case Constant.VIEW_TYPE_MONTH:
                transactionses = getTransbyMonth();
                break;
            case Constant.VIEW_TYPE_YEAR:
                transactionses = getTransbyYear();
                break;

        }
        return transactionses;
    }

    public List<Transactions> getTransbyDay() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for (int i = 6; i >= 0; i--) {
            List<TransactionDay> transactionDays = new ArrayList<TransactionDay>();
            TransactionDay transactionDay = new TransactionDay();
            Transactions transactions = new Transactions();
            Float calo_day = Float.valueOf(0);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            String strDate1 = Utils.changeDate2Str(cal.getTime());
            cal.add(Calendar.DATE, -1);
            String strDate2 = Utils.changeDate2Str(cal.getTime());
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_EXCERCISE).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_EXERCISE).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_EXCERCISE_ID)
                    .append(" AND (s.").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" BETWEEN ").append("Datetime(?) AND Datetime(?))");
            String[] atts = new String[]{strDate2, strDate1};
            Cursor cursor = database.rawQuery(sql.toString(), atts);
            cursor.moveToFirst();
            List<Transaction> trans = new ArrayList<Transaction>();
            while (!cursor.isAfterLast()) {
                Transaction tran = (Transaction) cursorTo(cursor);
                trans.add(tran);
                cursor.moveToNext();
            }
            cursor.close();
            transactionDay.setItems(trans);

            for (int j = 0; j < trans.size(); j++) {
                calo_day = calo_day+trans.get(j).getCalo();
            }

            transactionDay.setCalo(calo_day);
            cal.add(Calendar.DATE, 1);
            transactionDay.setDisplay_date(cal.getTime());
            transactionDays.add(transactionDay);

            //set transactions
            transactions.setCalo(calo_day);
            transactions.setItems(transactionDays);
            transactions.setTitle(Utils.getDayView(mContext, Utils.changeDate2Date(cal.getTime(), Constant.DATE_FORMAT_PICKER)));
            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    public List<Transactions> getTransbyWeek() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for (int i = 4; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR, -i);
            int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
            String startWeek = "";
            String endWeek = "";
            if (i == 0) {
                endWeek = Utils.changeDate2Str(calendar.getTime());
                if (dayofweek == 1) {
                    calendar.add(Calendar.DATE, -7);
                } else {
                    calendar.add(Calendar.DATE, -dayofweek + 1);
                }
                startWeek = Utils.changeDate2Str(calendar.getTime());
            } else {
                if (dayofweek == 1) {
                    endWeek = Utils.changeDate2Str(calendar.getTime());
                } else {
                    calendar.add(Calendar.DATE, -dayofweek + 8);
                    endWeek = Utils.changeDate2Str(calendar.getTime());
                }
                calendar.add(Calendar.DATE, -7);
                startWeek = Utils.changeDate2Str(calendar.getTime());
            }

            List<String> listDt = getDateTrans(startWeek, endWeek);
            Transactions transactions;
            if (listDt.size() >= 0) {
                transactions = getFutureTrans(listDt);
            } else {
                transactions = new Transactions();
            }

            if (i == 0) {
                transactions.setTitle(mContext.getString(R.string.onweek));
            } else if (i == 1) {
                transactions.setTitle(mContext.getString(R.string.preweek));
            } else {
                transactions.setTitle(Utils.changeDateStr2Str2(startWeek) + " - " + Utils.changeDateStr2Str2(endWeek));
            }

            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    public List<Transactions> getTransbyMonth() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for(int i=11;i>=0;i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -i);
            int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
            String startMonth = "";
            String endMonth = "";
            if(i==0){
                endMonth=Utils.changeDate2Str(calendar.getTime());
                calendar.add(Calendar.DATE,-dayofmonth);
                startMonth=Utils.changeDate2Str(calendar.getTime());
            }else{
                calendar.add(Calendar.DATE,-dayofmonth);
                startMonth=Utils.changeDate2Str(calendar.getTime());
                calendar.add(Calendar.MONTH,1);
                endMonth=Utils.changeDate2Str(calendar.getTime());
            }
            List<String> listDt = getDateTrans(startMonth, endMonth);
            Transactions transactions;
            if (listDt.size() >= 0) {
                transactions = getFutureTrans(listDt);
            } else {
                transactions = new Transactions();
            }
            if (i == 0) {
                transactions.setTitle(mContext.getString(R.string.onmonth));
            } else if (i == 1) {
                transactions.setTitle(mContext.getString(R.string.premonth));
            } else {
                transactions.setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Utils.getLocale())+" "+calendar.get(Calendar.YEAR));
            }
            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    public List<Transactions> getTransbyYear() {
        List<Transactions> transactionses = new ArrayList<Transactions>();
        for(int i=3;i>=0;i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR,-i);
            int year = calendar.get(Calendar.YEAR);
            List<Integer> lstMonthTrans = getMonthTrans(year);
            Transactions transactions = getTransactionaYear(lstMonthTrans, year);
            transactionses.add(transactions);
        }
        transactionses.add(getFutureTrans(getDisplayDateFuture()));
        return transactionses;
    }

    private List<String> getDateTrans(String startDt, String endDate) {
        StringBuffer sql = new StringBuffer("SELECT distinct ").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" FROM ")
                .append(MwSQLiteHelper.TABLE_TRANSACTION)
                .append(" WHERE ").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" BETWEEN ").append("Datetime(?) AND Datetime(?)")
                .append(" ORDER BY ").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" DESC");
        String[] atts = new String[]{startDt, endDate};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        List<String> lstDisplayDate = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            String strDisplayDate = cursor.getString(0);
            lstDisplayDate.add(strDisplayDate);
            cursor.moveToNext();
        }
        cursor.close();
        return lstDisplayDate;
    }

    private Transactions getFutureTrans(List<String> lstDisplayDt) {
        Transactions transactions = new Transactions();
        List<TransactionDay> transactionDays = new ArrayList<TransactionDay>();
        Float calo_total = Float.valueOf(0);
        for (int i = 0; i < lstDisplayDt.size(); i++) {
            TransactionDay transactionDay = new TransactionDay();
            Float calo_day = Float.valueOf(0);
            String strDisplayDt = lstDisplayDt.get(i);
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_EXCERCISE).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_EXERCISE).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_EXCERCISE_ID)
                    .append(" AND (s.").append(MwSQLiteHelper.COLUMN_TRANS_EXERCISE).append(" BETWEEN ").append("Datetime(?) AND (?))");
            Date dipslayDt = Utils.changeStr2Date(strDisplayDt, Constant.DATE_FORMAT_DB);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dipslayDt);
            calendar.add(Calendar.DATE, -1);
            String strDate2 = Utils.changeDate2Str(calendar.getTime());
            String[] atts = new String[]{Utils.getWallet_id() + "", strDate2, strDisplayDt};
            Cursor cursor = database.rawQuery(sql.toString(), atts);
            cursor.moveToFirst();
            List<Transaction> trans = new ArrayList<Transaction>();
            while (!cursor.isAfterLast()) {
                Transaction tran = (Transaction) cursorTo(cursor);
                trans.add(tran);
                cursor.moveToNext();
            }
            cursor.close();
            transactionDay.setItems(trans);

            for (int j = 0; j < trans.size(); j++) {
                calo_day=calo_day+trans.get(j).getCalo();
            }
            transactionDay.setCalo(calo_day);

            transactionDay.setDisplay_date(dipslayDt);
            transactionDays.add(transactionDay);
            calo_total = calo_total + calo_day;
        }
        transactions.setItems(transactionDays);
        transactions.setCalo(calo_total);
        transactions.setTitle(mContext.getResources().getString(R.string.future));
        return transactions;
    }

    private List<String> getDisplayDateFuture() {
        Calendar cal = Calendar.getInstance();
        String strDate1 = Utils.changeDate2Str(cal.getTime());
        StringBuffer sql = new StringBuffer("SELECT distinct ").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" FROM ")
                .append(MwSQLiteHelper.TABLE_TRANSACTION)
                .append(" WHERE ").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" >= ").append("Datetime(?)");
        String[] atts = new String[]{strDate1};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        List<String> lstDisplayDate = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            String strDisplayDate = cursor.getString(0);
            lstDisplayDate.add(strDisplayDate);
            cursor.moveToNext();
        }
        cursor.close();
        return lstDisplayDate;
    }

    private List<Integer> getMonthTrans(int year){
        List<Integer> lstMonth = new ArrayList<Integer>();
        Calendar calendar = Calendar.getInstance();
        String startDt="";
        String endDt="";
        int nyear = calendar.get(Calendar.YEAR);
        if(year==nyear){
            endDt = Utils.changeDate2Str(calendar.getTime());
        }else{
            endDt = year+"-12-31";
        }
        startDt = year-1 + "-12-31";

        StringBuffer sql = new StringBuffer("SELECT distinct ").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" FROM ")
                .append(MwSQLiteHelper.TABLE_TRANSACTION)
                .append(" WHERE (").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" BETWEEN ").append("Datetime(?) AND Datetime(?))")
                .append(" ORDER BY ").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" DESC");
        String[] atts = new String[]{startDt, endDt};
        Cursor cursor = database.rawQuery(sql.toString(), atts);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String strDisplayDate = cursor.getString(0);
            Date date = Utils.changeStr2Date(strDisplayDate, Constant.DATE_FORMAT_DB);
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH);
            if(!lstMonth.contains(month)){
                lstMonth.add(month);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return lstMonth;
    }

    private Transactions getTransactionaYear(List<Integer> months,int year){
        List<TransactionDay> transactionDays = new ArrayList<TransactionDay>();
        Transactions transactions = new Transactions();
        Float calo_total = Float.valueOf(0);
        for(int i=0;i<months.size();i++){
            int month = months.get(i);
            Calendar calendar = Calendar.getInstance();
            String startDt="";
            String endDt="";
            if(month==calendar.get(Calendar.MONTH)&&year==calendar.get(Calendar.YEAR)){
                endDt=Utils.changeDate2Str(calendar.getTime());
                int date = calendar.get(Calendar.DATE);
                calendar.add(Calendar.DATE,-date);
                startDt=Utils.changeDate2Str(calendar.getTime());
            }else{
                calendar.set(year,month,0);
                startDt=Utils.changeDate2Str(calendar.getTime());
                calendar.set(year, month, 1);
                int numdayofmonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.add(Calendar.DATE,numdayofmonth-1);
                endDt=Utils.changeDate2Str(calendar.getTime());
            }
            //Log.d("AAAAAAA",startDt+" "+endDt+" "+month+" "+calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            StringBuffer sql = new StringBuffer("SELECT * FROM ").append(MwSQLiteHelper.TABLE_TRANSACTION).append(" s inner join ")
                    .append(MwSQLiteHelper.TABLE_EXCERCISE).append(" c ON s.").append(MwSQLiteHelper.COLUMN_TRANS_EXERCISE).append(" = c.")
                    .append(MwSQLiteHelper.COLUMN_EXCERCISE_ID)
                    .append(" WHERE (s.").append(MwSQLiteHelper.COLUMN_TRANS_DATE).append(" BETWEEN ").append("Datetime(?) AND (?))");

            String[] atts = new String[]{Utils.getWallet_id() + "", startDt, endDt};
            Cursor cursor = database.rawQuery(sql.toString(), atts);
            cursor.moveToFirst();
            List<Transaction> trans = new ArrayList<Transaction>();
            Float calo_day = Float.valueOf(0);
            while (!cursor.isAfterLast()) {
                Transaction tran = (Transaction) cursorTo(cursor);
                calo_day=calo_day+tran.getCalo();
                trans.add(tran);
                cursor.moveToNext();
            }
            cursor.close();
            TransactionDay transactionDay = new TransactionDay();
            transactionDay.setItems(trans);
            transactionDay.setCalo(calo_day);
            calendar=Calendar.getInstance();
            calendar.set(Calendar.MONTH, month);
            transactionDay.setDisplayStr(calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Utils.getLocale()));
            calo_total = calo_total + calo_day;
            transactionDays.add(transactionDay);

        }
        transactions.setItems(transactionDays);
        transactions.setCalo(calo_total);
        transactions.setTitle(year + "");
        return transactions;
    }

}
