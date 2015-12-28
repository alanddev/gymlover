package com.alanddev.gymlover.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MwSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_WALLET = "wallet";
    public static final String TABLE_CUR = "currency";
    public static final String TABLE_CATEGORY = "category";
    public static final String TABLE_TRANSACTION = "transactions";
    public static final String TABLE_BUDGET = "budget";


    // table Wallet
    public static final String COLUMN_WALLET_ID = "id";
    public static final String COLUMN_WALLET_NAME = "name";
    public static final String COLUMN_WALLET_AMOUNT = "amount";
    public static final String COLUMN_WALLET_CURRENCY = "currency";
    // only save image Name file.
    public static final String COLUMN_WALLET_IMG="image";
    public static final String DATABASE_NAME = "gymlover.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase sqLiteDatabase;
    // 20 fields
    // Database creation sql statement
    private static final String WALLET_CREATE = "CREATE TABLE "
            + TABLE_WALLET + "("
            + COLUMN_WALLET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_WALLET_NAME + " text not null, "
            + COLUMN_WALLET_AMOUNT + " real not null, "
            + COLUMN_WALLET_CURRENCY + " text not null, "
            + COLUMN_WALLET_IMG + " text not null "
            + ");";


    public MwSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        sqLiteDatabase = database;
        database.execSQL(WALLET_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");*/
        //sqLiteDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WALLET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        onCreate(db);
    }

}

