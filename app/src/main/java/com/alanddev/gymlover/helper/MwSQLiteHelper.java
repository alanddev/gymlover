package com.alanddev.gymlover.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MwSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_EXCERCISE = "excercise";
    public static final String TABLE_EXCERCISE_GROUP = "excercise_group";
    public static final String TABLE_USER = "user";
    public static final String TABLE_HISTORY = "history";
    public static final String TABLE_WORKOUT = "workout";
    public static final String TABLE_WORKOUT_EXER = "workout_exer";
    public static final String TABLE_TRANSACTION ="transactions";

    // table columns
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

    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_BIRTHDAY = "birthday";
    public static final String COLUMN_USER_HEIGHT = "height";
    public static final String COLUMN_USER_WEIGHT = "weight";
    public static final String COLUMN_USER_FAT = "fat";
    public static final String COLUMN_USER_IMG = "img";

    public static final String COLUMN_HISTORY_ID = "id";
    public static final String COLUMN_HISTORY_USER = "user_id";
    public static final String COLUMN_HISTORY_HEIGHT = "height";
    public static final String COLUMN_HISTORY_WEIGHT = "weight";
    public static final String COLUMN_HISTORY_FAT = "fat";
    public static final String COLUMN_HISTORY_DATE = "date";



    public static final String COLUMN_WORKOUT_ID = "id";
    public static final String COLUMN_WORKOUT_NAME = "name";
    public static final String COLUMN_WORKOUT_DESC = "desc";
    public static final String COLUMN_WORKOUT_IMAGE = "image";
    public static final String COLUMN_WORKOUT_USES = "uses";
    public static final String COLUMN_WORKOUT_WEEK = "weeks";

    public static final String COLUMN_WORKOUT_EXER_ID = "id";
    public static final String COLUMN_WORKOUT_EXER_WORK_ID = "workout_id";
    public static final String COLUMN_WORKOUT_EXER_EXER_ID = "exer_id";
    public static final String COLUMN_WORKOUT_EXER_DAY = "day";
    public static final String COLUMN_WORKOUT_EXER_DESC = "desc";
    public static final String COLUMN_WORKOUT_EXER_SET = "num_set";
    public static final String COLUMN_WORKOUT_EXER_REPEAT = "num_repeat";
    public static final String COLUMN_WORKOUT_EXER_WEIGHT = "weight";
    public static final String COLUMN_WORKOUT_EXER_TIME = "time";


    // table columns
    public static final String COLUMN_TRANS_ID = "id";
    public static final String COLUMN_TRANS_CALO= "calors";
    public static final String COLUMN_TRANS_EXERCISE = "exercise_id";
    public static final String COLUMN_TRANS_REPEAT= "repeat";
    public static final String COLUMN_TRANS_DATE = "date";
    public static final String COLUMN_TRANS_NOTE = "note";
    public static final String COLUMN_TRANS_TIME = "time";
    public static final String COLUMN_TRANS_WEIGHT = "weight";

    public static final String DATABASE_NAME = "gymlover.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDatabase sqLiteDatabase;
    // 20 fields
    // Database creation sql statement


    private static final String USER_CREATE = "CREATE TABLE "
            + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_USER_NAME + " text not null, "
            + COLUMN_USER_GENDER + " integer not null, "
            + COLUMN_USER_HEIGHT + " float not null, "
            + COLUMN_USER_WEIGHT + " float not null, "
            + COLUMN_USER_FAT + " float not null, "
            + COLUMN_USER_IMG + " text not null, "
            + COLUMN_USER_BIRTHDAY + " DATETIME not null "
            + ");";


    private static final String HISTORY_CREATE = "CREATE TABLE "
            + TABLE_HISTORY + "("
            + COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_HISTORY_USER + " integer not null, "
            + COLUMN_HISTORY_HEIGHT + " float not null, "
            + COLUMN_HISTORY_WEIGHT + " float not null, "
            + COLUMN_HISTORY_FAT + " float not null, "
            + COLUMN_HISTORY_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ");";


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
            + COLUMN_EX_GROUP_DESC + " text, "
            + COLUMN_EX_GROUP_IMAGE + " text "
            + ");";

    private static final String WORKOUT_CREATE = "CREATE TABLE "
            + TABLE_WORKOUT + "("
            + COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_WORKOUT_NAME + " text not null, "
            + COLUMN_WORKOUT_DESC + " text, "
            + COLUMN_WORKOUT_IMAGE + " text, "
            + COLUMN_WORKOUT_USES + " INTEGER DEFAULT 0, "
            + COLUMN_WORKOUT_WEEK + " INTEGER not null "
            + ");";

    private static final String WORKOUT_EXER_CREATE = "CREATE TABLE "
            + TABLE_WORKOUT_EXER + "("
            + COLUMN_WORKOUT_EXER_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_WORKOUT_EXER_WORK_ID + " integer not null, "
            + COLUMN_WORKOUT_EXER_EXER_ID + " integer not null, "
            + COLUMN_WORKOUT_EXER_DAY + " integer not null, "
            + COLUMN_WORKOUT_EXER_DESC + " text, "
            + COLUMN_WORKOUT_EXER_SET + " integer, "
            + COLUMN_WORKOUT_EXER_REPEAT + " integer, "
            + COLUMN_WORKOUT_EXER_WEIGHT + " float not null,  "
            + COLUMN_WORKOUT_EXER_TIME + " float not null "
            + ");";

    private static final String TRANS_CREATE = "CREATE TABLE "
            + TABLE_TRANSACTION + "("
            + COLUMN_TRANS_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_TRANS_EXERCISE + " integer not null, "
            + COLUMN_TRANS_DATE + " datetime not null, "
            + COLUMN_TRANS_REPEAT + " integer not null, "
            + COLUMN_TRANS_WEIGHT + " float not null, "
            + COLUMN_TRANS_TIME + " float not null, "
            + COLUMN_TRANS_CALO + " float not null, "
            + COLUMN_TRANS_NOTE + " text "
            + ");";

    public MwSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        sqLiteDatabase = database;
        database.execSQL(EXCERCISE_CREATE);
        database.execSQL(EX_GROUP_CREATE);
        database.execSQL(USER_CREATE);
        database.execSQL(WORKOUT_CREATE);
        database.execSQL(WORKOUT_EXER_CREATE);
        database.execSQL(HISTORY_CREATE);
        database.execSQL(TRANS_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");*/
        //sqLiteDatabase = db;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXCERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXCERCISE_GROUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_EXER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }

}

