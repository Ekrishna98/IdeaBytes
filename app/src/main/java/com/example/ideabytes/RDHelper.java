package com.example.ideabytes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RDHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "EmployeeData_11.db";
    public static final String TABLE_NAME = "empData";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRSTNAME";
    public static final String COL_3 = "LASTNAME";
    public static final String COL_4 = "PHONE";
    public static final String COL_5 = "EMAIL";
    public static final String COL_6 = "PASS";

    // Constructor
    public RDHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" Create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, PHONE TEXT, EMAIL TEXT, PASS TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEmpData(String FIRSTNAME, String LASTNAME , String PHONE , String EMAIL , String PASS){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues co= new ContentValues();

        co.put(COL_2,FIRSTNAME);
        co.put(COL_3,LASTNAME);
        co.put(COL_4,PHONE);
        co.put(COL_5,EMAIL);
        co.put(COL_6,PASS);
        db.insert(TABLE_NAME,null,co);
        db.close();
    }
}
