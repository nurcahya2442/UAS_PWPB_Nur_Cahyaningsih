package com.example.uas_pwpb_nur_cahyaningsih;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION=1;
    private static final String DB_NAME="NoteInfo";
    private static final String TABLE_NAME="tbl_note";
    private static final String KEY_JUDUL="judul";
    private static final String KEY_DATE="date";
    private static final String KEY_DESKRIPSI="deskripsi";


    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DB_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTable="Create Table "+TABLE_NAME+"("+KEY_JUDUL+" TEXT PRIMARY KEY,"+KEY_DESKRIPSI+" TEXT"+","+KEY_DATE+" DATE "+")";
        db.execSQL(createNoteTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql=("drop table if exists "+TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);

    }

    public void insert(DataNotes dataNotes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, dataNotes.getDate());
        values.put(KEY_JUDUL, dataNotes.getJudul());
        values.put(KEY_DESKRIPSI, dataNotes.getDeskripsi());

        db.insert(TABLE_NAME, null,values);

    }
    public List<DataNotes> selectNoteData(){
        ArrayList<DataNotes> noteList=new ArrayList<DataNotes>();

        SQLiteDatabase db =getReadableDatabase();
        String[] colums={KEY_JUDUL,KEY_DESKRIPSI,KEY_DATE};

        Cursor c = db.query(TABLE_NAME, colums, null,null, null, null, null);

        while (c.moveToNext()) {
            String date = c.getString(2);
            String judul = c.getString(0);
            String deskripsi = c.getString(1);


            DataNotes dataNotes= new DataNotes();
            dataNotes.setDate(date);
            dataNotes.setJudul(judul);
            dataNotes.setDeskripsi(deskripsi);

            noteList.add(dataNotes);
        }
        return noteList;
    }
    public void delete(String judul){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause=KEY_JUDUL+"='"+judul+"'";
        db.delete(TABLE_NAME,whereClause,null);
    }
    public void update(DataNotes dataNotes){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_JUDUL,dataNotes.getJudul());
        values.put(KEY_DESKRIPSI,dataNotes.getDeskripsi());
        String whereClause=KEY_JUDUL+"='"+dataNotes.getJudul()+"'";
        db.update(TABLE_NAME,values,whereClause, null);
    }

}
