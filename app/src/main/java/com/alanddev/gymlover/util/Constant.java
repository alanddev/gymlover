package com.alanddev.gymlover.util;

import android.os.Environment;

/**
 * Created by ANLD on 28/12/2015.
 */
public class Constant {
    public static final Integer EXPENSE_TYPE=0;
    public static final Integer INCOME_TYPE=1;
    public static final String DATE_FORMAT_PICKER = "dd/MM/yyyy";
    public static final String DATE_FORMAT_DB = "yyyy-MM-dd";
    public static final int BODYFAT_USER_REQUEST = 2;
    public static final int GALLERY_USER_REQUEST = 3;

    public static final String PATH_IMG= Environment.getExternalStorageDirectory().getPath()+"/gymlover/image";
    public static final int VIEW_TYPE_DAY = 0;
    public static final int VIEW_TYPE_WEEK = 1;
    public static final int VIEW_TYPE_MONTH = 2;
    public static final int VIEW_TYPE_YEAR = 3;
    public static final int VIEW_TYPE_CATE = 4;
    public static final String SHAREDPREFERENCES_NAME = "My_SharedPreferences";
    public static final String VIEW_TYPE = "VIEW_TYPE";
    public static final String WALLET_ID = "WALLET_ID";
    public static final int ADD_TRANSACTION_SUCCESS = 1;
    public static final String CUR_ID = "CUR_ID";
    public static final long SPLASH_DISPLAY_LONG = 600;
    public static final long SPLASH_DISPLAY_SHORT = 300;
    public static final int CAT_WALLET_ADD_INCOME = 19;
    public static final int CAT_WALLET_ADD_EXPENSE = 12;
    public static final int CAT_TYPE_EXPENSE = 0;
    public static final int CAT_TYPE_INCOME = 1;
    public static final int TRANS_DETAIL_REQUEST = 8;
    public static final int TRANS_DETAIL_UPDATE = 9;
    public static final String PUT_EXTRA_DATE = "PUT_EXTRA_DATE";
    public static final int PICK_EXERCISE = 1;
    public static final int ADD_TRANSACTION_REQUEST = 2;
    public static final int TREND_TYPE_INCOME = 1;
    public static final int TREND_TYPE_EXPENSE = 0;
    public static final int TREND_TYPE_BALANCE = 2;
    public static final int TREND_TYPE_SUB = 3;
    public static String TREND_MONTH_TITLE = "Th";
    public static final int BUDGET_ADD_REQUEST = 1;
    public static final int BUDGET_ADD_RESULT = 2;
    public static final int BUDGET_AVAL_TYPE = 0;
    public static final int BUDGET_EX_TYPE = 1;
    public static final int ALL_CATEGORY_TYPE=2;
    public static final String PUT_EXTRA_BUDGET = "PUT_EXTRA_BUDGET";

    public static final int CHANGE_THEME_ID =0;
    public static final int CHANGE_NAV_ID =1;
    public static final int CHANGE_LANGUAGE_ID = 2;

    public static final String THEME_CURRENT = "THEME_CURRENT";
    public static final String NAV_HEADER_CURRENT = "NAV_HEADER_CURRENT";
    public static final String LANGUAGE_CURRENT = "LANGUAGE_CURRENT";
    public static int GENDER_BOY = 0;
    public static int GENDER_GIRL = 1;
    public static String KEY_GENDER="gender";
    public static String KEY_WEIGHT_CHOICE="weight_choice";
    public static String KEY_HEIGHT_CHOICE="height_choice";
    public static String KEY_BODY_FAT="bodyfat";
    public static String KEY_WEIGHT="weight";
    public static String KEY_POSITION = "position";
    public static String KEY_AUTORUN = "autorun";
    public static String KEY_TIME = "time_total";
    public static String KEY_WORKOUT_SETTING ="workout_setting";
    public static String KEY_GUIDE_POSITION ="position";
    public static String KEY_FIRST_GUIDE ="first_guide";

    public static int WORKOUT_NEW = 0;
    public static int WORKOUT_USING = 1;
    public static int WORKOUT_USED = 2;
    public static float INCH_TO_CM = 2.5f;
    public static float KG_TO_LB = 2.20462f;

    public static String TAKE_EXERCISE = "TAKE_EXERCISE";
    public static int REPORT_TYPE_BODY = 0;
    public static int REPORT_TYPE_WORKOUT = 1;
    public static String KEY_REPORT_TYPE = "KEY_REPORT_TYPE";
    public static int EDIT_USER_RESULT = 108;
    public static int LENGTH_GUIDE = 20;
}
