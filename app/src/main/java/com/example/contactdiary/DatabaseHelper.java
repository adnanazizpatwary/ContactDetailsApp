package com.example.contactdiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DatabaseName ="PersonsDatabase";
    public static final int DatabaseVersion = 1;
    public static final String TableName="ContactTable";
    public static final String col_Id="person_id";
    public static final String col_name="person_name";
    public static final String col_phone="pNumber";
    public static final String col_email="p_email";
    public static final String Create_table="create table "+TableName+"("+
            col_Id+" Integer primary key, "+
            col_name+" text, "+
            col_phone+" text, "+
            col_email+" text);";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TableName);
        onCreate(db);

    }

}
