package com.alanddev.gymlover.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MwSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXCERCISE = "excercise";
    public static final String TABLE_EXCERCISE_GROUP = "excercise_group";
    // table Wallet
    public static final String COLUMN_EXCERCISE_ID = "id";
    public static final String COLUMN_EXCERCISE_NAME = "name";
    public static final String COLUMN_EXCERCISE_DESC = "desc";
    public static final String COLUMN_EXCERCISE_IMAGE = "image";
    public static final String COLUMN_EXCERCISE_VIDEOLINK = "videolink";
    public static final String COLUMN_EXCERCISE_GRP_ID = "group_id";

    public static final String COLUMN_EX_GROUP_ID = "id";
    public static final String COLUMN_EX_GROUP_NAME = "name";
    public static final String COLUMN_EX_GROUP_DESC = "desc";
    public static final String COLUMN_EX_GROUP_IMAGE = "image";


    public static final String DATABASE_NAME = "gymlover.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase sqLiteDatabase;
    // 20 fields
    // Database creation sql statement
    private static final String EXCERCISE_CREATE = "CREATE TABLE "
            + TABLE_EXCERCISE + "("
            + COLUMN_EXCERCISE_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_EXCERCISE_NAME + " text not null, "
            + COLUMN_EXCERCISE_DESC + " text not null, "
            + COLUMN_EXCERCISE_IMAGE + " text, "
            + COLUMN_EXCERCISE_VIDEOLINK + " text, "
            + COLUMN_EXCERCISE_GRP_ID + " integer not null "
            + ");";

    private static final String EX_GROUP_CREATE = "CREATE TABLE "
            + TABLE_EXCERCISE_GROUP + "("
            + COLUMN_EX_GROUP_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_EX_GROUP_NAME + " text not null, "
            + COLUMN_EX_GROUP_DESC + " text not null, "
            + COLUMN_EX_GROUP_IMAGE + " text "
            + ");";


    public MwSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        sqLiteDatabase = database;
        database.execSQL(EXCERCISE_CREATE);
        database.execSQL(EX_GROUP_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");*/
        //sqLiteDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS " + EXCERCISE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXCERCISE_GROUP);
        onCreate(db);
    }

}

