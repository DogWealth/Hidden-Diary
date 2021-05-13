package com.arcsoft.arcfacedemo.NoteBook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDatabase extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String ID = "_id";//没加下划线，闪退
    public static final String TIME = "time";
    public static final String CREATE_TIME = "create_time";
    public static final String MODE = "mode";
    public NoteDatabase(Context context){
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建时要有空格
        db.execSQL("CREATE TABLE " + TABLE_NAME +"("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTENT +
                " TEXT NOT NULL," + TIME+" TEXT NOT NULL," +CREATE_TIME+" TEXT NOT NULL," + MODE + " INTEFER DEFAULT 1)");//id自增长的
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
