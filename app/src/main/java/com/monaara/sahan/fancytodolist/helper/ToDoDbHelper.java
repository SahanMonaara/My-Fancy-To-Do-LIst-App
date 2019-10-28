package com.monaara.sahan.fancytodolist.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.monaara.sahan.fancytodolist.model.ToDoItem;

import java.util.LinkedList;
import java.util.List;


public class ToDoDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "todo";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMPLETE_STATUS = "completed";
    public static final String COLUMN_FINISH_STATUS = "finshed";
    public String query;
    public ToDoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " ("
                +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_DATE + " TEXT NOT NULL, "  +
                COLUMN_COMPLETE_STATUS + " TEXT," +
                COLUMN_FINISH_STATUS + " TEXT );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    //insert a toDoItem
    public void saveItem(ToDoItem toDoItem) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, toDoItem.getTitle());
        values.put(COLUMN_DATE, toDoItem.getDate());
        values.put(COLUMN_COMPLETE_STATUS, toDoItem.getCompleteStatus());
        values.put(COLUMN_FINISH_STATUS,toDoItem.getFinishStatus());

        // insert
        db.insertOrThrow(TABLE_NAME, null, values);
        db.close();
    }
        //take all items
        public List<ToDoItem> toDoItemList(String selectedDate,String endDate ) {
        if (selectedDate == null || endDate==null) {
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        } else {
            //filter results by filter option provided
            query = "SELECT * FROM " + TABLE_NAME + " WHERE date BETWEEN " + selectedDate + " AND " + endDate;
        }

        List<ToDoItem> toDoItemLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ToDoItem toDoItem;

        if (cursor.moveToFirst()) {
            do {
                toDoItem = new ToDoItem();

                toDoItem.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                toDoItem.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                toDoItem.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                toDoItem.setCompleteStatus(cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETE_STATUS)));
                toDoItem.setFinishStatus(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STATUS)));
                toDoItemLinkedList.add(toDoItem);
            } while (cursor.moveToNext());
        }


        return toDoItemLinkedList;
    }

    //delete item
    public void deleteItem(int id) {
        String deleteQuery = "DELETE FROM "+TABLE_NAME+" WHERE id= '" + id + "'";
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }
    //update complete status
    public void updateCompletedOnly(int id, String completeStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  " + TABLE_NAME + " SET completed ='" + completeStatus   + "'  WHERE id='" + id + "'");
    }

    //update finish status
    public void updateFinishedOnly(int id, String finishedStatus){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  " + TABLE_NAME + " SET finshed ='" + finishedStatus  + "'  WHERE id='" + id + "'");
    }

    //filter data by status
    public List<ToDoItem> viewByStatus(String status1 ,String status2){
        String queryForStatus1 = "SELECT  * FROM " + TABLE_NAME + " WHERE  finshed ='" + status1 + "'";
        String queryForStatus2 = "SELECT  * FROM " + TABLE_NAME + " WHERE  finshed ='" + status1 + "'" +"OR finshed = '"+status2+"'";
        String finalQuery = "";


        if (status1.equals("unfinished") && status2.equals("not")){
            finalQuery = queryForStatus1;
        }
        else if (status1.equals("finished") && status2.equals("not")){
            finalQuery = queryForStatus1;
        }
        else finalQuery = queryForStatus2;


        List<ToDoItem> todoItemLinkedListByStatus = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(finalQuery, null);
        ToDoItem toDoItem;

        if (cursor.moveToFirst()) {
            do {
                toDoItem = new ToDoItem();

                toDoItem.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                toDoItem.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                toDoItem.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                toDoItem.setCompleteStatus(cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETE_STATUS)));
                toDoItem.setFinishStatus(cursor.getString(cursor.getColumnIndex(COLUMN_FINISH_STATUS)));
                todoItemLinkedListByStatus.add(toDoItem);
            } while (cursor.moveToNext());
        }


        return todoItemLinkedListByStatus;
    }


}
