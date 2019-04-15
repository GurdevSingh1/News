package com.example.news;

import android.database.sqlite.SQLiteOpenHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


//SqliteHandlder class for database operations
public class MySqliteHanlder extends SQLiteOpenHelper {
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "news.db";
    static final String TABLE_NEWS = "tbnews";
    static final String COLUMN_ID = "id";
    static final String COLUMN_HEADING= "heading";
    static final String COLUMN_URL= "url";
    static final String COLUMN_AUTHOR="author";
    static final String COLUMN_PUBDATE = "pubdate";
    static final String COLUMN_SNIPPET= "snippet";

    String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY, " + COLUMN_HEADING + " TEXT, " +
            COLUMN_URL + " TEXT, "+ COLUMN_AUTHOR + " TEXT, "+
            COLUMN_PUBDATE  + " TEXT, "+ COLUMN_SNIPPET + " TEXT) ";

    public MySqliteHanlder(Context context) {
        //CursorFactory --> null
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    //called When database is created for the first time
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS_TABLE);
    }

    @Override
    //Called when the database needs to be upgraded.
    //When we update our app or change our database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);
    }
    // All Database Operations: create, read, delete
    // create
    public void addNews(News n) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_HEADING,n.getHeading());
        values.put(COLUMN_URL,n.getUrl());
        values.put(COLUMN_AUTHOR,n.getAuthor());
        values.put(COLUMN_PUBDATE ,n.getPubDate());
        values.put(COLUMN_SNIPPET,n.getSnippet());

        //Name of table, nullColumnHack, ContentValues
        database.insert(TABLE_NEWS, null, values);
        database.close();
    }

    public News getNews(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NEWS, new String[]{COLUMN_ID , COLUMN_HEADING,COLUMN_URL,COLUMN_AUTHOR,COLUMN_PUBDATE,COLUMN_SNIPPET},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);
        if (cursor != null)  {
            cursor.moveToFirst();
        }
        News n = new News(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5)
        );
        database.close();
        return n;
    }
    // Getting all Objects
    public ArrayList<News> getAllNews() {
        ArrayList<News> newslist = new ArrayList<News>();

        String selectAllQuery = "SELECT * FROM " + TABLE_NEWS;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(selectAllQuery, null);
        if (cursor.moveToFirst()) {
            do {
                News n = new News(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),
                        cursor.getString(5)
                );
                newslist.add(n);
            } while (cursor.moveToNext());
        }
        database.close();
        return newslist;
    }

    // Deleteing a single News
    public void deleteNews(News n) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NEWS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(n.getId())});
        database.close();
    }

}

