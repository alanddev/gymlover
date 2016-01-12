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

import java.util.ArrayList;
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
        ArrayList<Model> transactions = new ArrayList<Model>();
        Cursor cursor = database.query(MwSQLiteHelper.TABLE_TRANSACTION,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Transaction transaction = (Transaction)cursorTo(cursor);
            transactions.add(transaction);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return transactions;
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
        }catch (Exception ex){
            //don't do anything
        }
        return transaction;
    }

    @Override
    public void delete() {
        database.delete(MwSQLiteHelper.TABLE_TRANSACTION, null, null);
    }

}
