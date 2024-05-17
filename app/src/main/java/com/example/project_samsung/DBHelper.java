package com.example.project_samsung;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WORDS.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "WORDS";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public DBHelper(StatisticsFragment context) {
        super(context.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NUMBER + " INTEGER, " + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Функция для добавления записи в базу данных
    public void addData(int number, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NUMBER + ") VALUES (" + number + ")";
        db.execSQL(insertQuery);
        db.close();
    }

    // Функция для получения последнего числа из базы данных
    public int getLastNumber() {
        int lastNumber = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_NUMBER + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            lastNumber = cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER));
        }
        cursor.close();
        db.close();
        return lastNumber;
    }

    // Функция для получения всех данных из базы данных
    public List<String> getAllData() {
        List<String> dataList = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int number = cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER));
                String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
                String dataEntry = "Number: " + number + ", Timestamp: " + timestamp+"\n";
                dataList.add(dataEntry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return dataList;
    }

    public String getMidCount() {
        double number = 0;
        double i =0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NUMBER + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                number += cursor.getInt(cursor.getColumnIndex(COLUMN_NUMBER));
                i++;
            } while (cursor.moveToNext());
        }else {
            i++;
        }
        cursor.close();
        db.close();
        return String.format("%.2f",number/i);
    }
    public int bestResult(){
        int res = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_NUMBER + ") FROM " + TABLE_NAME, null);
         if (cursor != null && cursor.moveToFirst()) {
             res = cursor.getInt(0);
         }
        cursor.close();
        db.close();
        return res;
    }

    public int countOfTests(){
        int i =0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NUMBER + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return i;
    }
}
