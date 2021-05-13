package com.arcsoft.arcfacedemo.NoteBook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CRUD extends com.example.notebook.BaseActivity {
    SQLiteOpenHelper dbHandler;
    SQLiteDatabase db;
    private static final String TAG = "CRUD";

    private static final String[] columns = {
            NoteDatabase.ID,
            NoteDatabase.CONTENT,
            NoteDatabase.TIME,
            NoteDatabase.CREATE_TIME,
            NoteDatabase.MODE
    };

    @Override
    protected void needRefresh() {

    }

    public CRUD(Context context) {
        dbHandler = new NoteDatabase(context);
    }

    public void open(){
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    //把note 加入到database里面
    public Note addNote(Note note){
        //add a note object to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteDatabase.CONTENT, note.getContent());
        contentValues.put(NoteDatabase.TIME, note.getTime());
        contentValues.put(NoteDatabase.CREATE_TIME, note.getCreteTime());
        contentValues.put(NoteDatabase.MODE, note.getTag());
        long insertId = db.insert(NoteDatabase.TABLE_NAME, null, contentValues);
        note.setId(insertId);
        return note;
    }
//    public Note getNote(long id){
//        //get a note from database using cursor index
//        Cursor cursor = db.query(NoteDatabase.TABLE_NAME, columns, NoteDatabase.ID + "=?",
//                new String[] {String.valueOf(id)}, null, null, null, null);
//        if (cursor != null) cursor.moveToFirst();
//        Note e = new Note(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
//        return e;
//    }
    public List<Note> getAllNotes(boolean update){

        Cursor cursor;
        if(update){
             cursor = db.query(NoteDatabase.TABLE_NAME, columns, null, null, null, null, NoteDatabase.TIME+" DESC");
        }else{
             cursor = db.query(NoteDatabase.TABLE_NAME, columns, null, null, null, null, NoteDatabase.CREATE_TIME+" DESC");
        }
//        cursor = db.query(NoteDatabase.TABLE_NAME, columns, null, null, null, null, NoteDatabase.CREATE_TIME+" DESC");


        List<Note> notes = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                Note note = new Note();
                note.setId(cursor.getLong(cursor.getColumnIndex(NoteDatabase.ID)));
                note.setContent(cursor.getString(cursor.getColumnIndex(NoteDatabase.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.TIME)));
                note.setCreateTime(cursor.getString(cursor.getColumnIndex(NoteDatabase.CREATE_TIME)));
                note.setTag(cursor.getInt(cursor.getColumnIndex(NoteDatabase.MODE)));
                notes.add(note);
            }
        }
        return notes;
    }

    public int updateNote(Note note) {
        //update the info of an existing note
        ContentValues values = new ContentValues();
        values.put(NoteDatabase.CONTENT, note.getContent());
        values.put(NoteDatabase.TIME, note.getTime());
        values.put(NoteDatabase.MODE, note.getTag());
        //updating row
        return db.update(NoteDatabase.TABLE_NAME, values,
                NoteDatabase.ID + "=?", new String[] { String.valueOf(note.getId())});
    }

    public void removeNote(Note note){
        //remove a note according to ID value
        db.delete(NoteDatabase.TABLE_NAME, NoteDatabase.ID + "=" + note.getId(), null);
    }

//    public boolean isUpdateOrder() {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        return sharedPreferences.getBoolean("updateOrder", false);
//    }

}
