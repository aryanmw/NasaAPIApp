package com.wadhavekar.nasainfo.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Favourites";
    public static final String TABLE1_NAME = "History";
    public static final String COL1 = "Date";
    public static final String COL2 = "url";
    public static final String COL3 = "title";

    public DatabaseManager(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable1 = "CREATE TABLE " +TABLE1_NAME+ "(Date Text, "+COL2+" TEXT, "+COL3 +" TEXT)";


        sqLiteDatabase.execSQL(createTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
    }

    public boolean addData(String date, String url,String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1,date);
        contentValues.put(COL2,url);
        contentValues.put(COL3,title);

        long result = db.insert(TABLE1_NAME,null,contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor retrieveData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE1_NAME,null);
        return data;
    }

    public void deleteFav(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ TABLE1_NAME +" WHERE "+ COL1 + " = '" + date + "'";
        db.execSQL(query);
    }


}

