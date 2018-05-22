package com.example.a16022916.p06taskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    //DATABASE_NAME
    private static final String TABLE_NAME = "Items";
    private static final String DATABASE_NAME = "toDoListManager.db";
    private static final String COL0ID = "ID";
    private static final String COL1Name = "itemName";
    private static final String COL2Desc = "itemDesc";
    private static final String COL3Time = "itemTime";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

//    public DatabaseHelper(CustomAdapter customAdapter) {
//        super(customAdapter, DATABASE_NAME, null, 1);
//    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("+COL0ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1Name + " TEXT,"
                + COL2Desc + " TEXT," + COL3Time + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldDb, int newDb) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String strId = String.valueOf(item.getId());
        String strGetName = item.getName().toString();
        String strGetDesc = item.getDescription();
        String strTime = String.valueOf(item.getTime());


        contentValues.put(COL0ID,strId);
        contentValues.put(COL1Name, strGetName);
        contentValues.put(COL2Desc,strGetDesc);
        contentValues.put(COL3Time, strTime);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if(result == -1){
            Log.v(TAG,"AddData Error: -1");

        } else {
            Log.v(TAG,"Data Added Successfully");
        }

    }

    public Item getDataById(int id){

        SQLiteDatabase db = this.getReadableDatabase();

//        String query  = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.query(TABLE_NAME, new String[] { COL0ID,
                        COL1Name, COL2Desc, COL3Time }, COL0ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Item toDoList = new Item(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));

        return toDoList;
    }
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> toDo = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                int time = Integer.parseInt(cursor.getString(3));

                toDo.add(new Item(id,name,desc,time));

            } while (cursor.moveToNext());
        }

        // return contact list
        return toDo;
    }

}
