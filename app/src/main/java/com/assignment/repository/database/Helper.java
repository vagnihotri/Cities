package com.assignment.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by agni on 24/01/18.
 */

public class Helper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Assignment.db";
    public static final int DATABASE_VERSION = 2;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Contract.City.getCityCreateQuery());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(Contract.City.getCityDeleteQuery());
        onCreate(sqLiteDatabase);
    }
}
