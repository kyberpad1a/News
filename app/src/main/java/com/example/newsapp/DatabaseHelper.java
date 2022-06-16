package com.example.newsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public  DatabaseHelper(Context context){
        super(context,"NewsData.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table Role(ID_Role INTEGER PRIMARY KEY AUTOINCREMENT, NameRole TEXT);");
        sqLiteDatabase.execSQL("create Table Users(ID_User INTEGER PRIMARY KEY AUTOINCREMENT not null, FIO TEXT not null, Email TEXT not null, Password TEXT not null, Role_ID INTEGER NOT NULL, FOREIGN KEY (Role_ID) REFERENCES Role(ID_Role));");
        sqLiteDatabase.execSQL("create Table News(ID_News INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, DateNews TEXT, Description TEXT);");

        sqLiteDatabase.execSQL("INSERT INTO Role values(1,'Администратор');");
        sqLiteDatabase.execSQL("INSERT INTO Role values(2,'Пользователь');");
        sqLiteDatabase.execSQL("INSERT INTO Users values(1,'Веприцкий Артём Николаевич', 'isip_a.n.veprickiy@mpt.ru','123',1);");
        sqLiteDatabase.execSQL("INSERT INTO News values(1,'Первая новость', '2021.11.29','новостьновостьновостьновостьновостьновостьновость');");
        sqLiteDatabase.execSQL("INSERT INTO News values(2,'Вторая новость', '2021.11.30','новость2новость2новость2новость2новость2новость2новость2новость2');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists Role");
        sqLiteDatabase.execSQL("drop Table if exists Users");
        sqLiteDatabase.execSQL("drop Table if exists News");
    }

    public Boolean insert(String FIO, String Email, String Password,int Role_ID)
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users where Email=?",new String[]{Email});
        if(cursor.getCount()>0) return false;
        else {
            DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("FIO", FIO);
            contentValues.put("Email", Email);
            contentValues.put("Password", Password);
            contentValues.put("Role_ID", Role_ID);
            long result = DB.insert("Users", null, contentValues);
            return result != -1;
        }
    }

    public Cursor getdata()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News",null);
    }

    public Cursor getNews(String Title,String DateNews)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News where Title=? and DateNews=?",new String[]{Title,DateNews});
    }

    public Boolean insertNews(String Title, String DateNews, String Description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Title", Title);
            contentValues.put("DateNews", DateNews);
            contentValues.put("Description", Description);
            long result = DB.insert("News", null, contentValues);
            return result != -1;
    }

    public Boolean UpdateNews(String id, String Title, String DateNews, String Description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", Title);
        contentValues.put("DateNews", DateNews);
        contentValues.put("Description", Description);
        long result = DB.update("News",contentValues,"ID_News = ?",new String[] {id});
        return result != -1;
    }

    public Boolean DeleteNews(String id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("News","ID_News = ?", new String[] {id});
        return result != -1;
    }


    public Boolean Update(String id, String name, String phone, String date_of_birth)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("phine", phone);
        contentValues.put("date_of_birth", date_of_birth);
        long result = DB.update("UserInfo",contentValues,"id = ?",new String[] {id});
        return result != -1;
    }

    public Boolean Delete(String id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("UserInfo","id = ?", new String[] {id});
        return result != -1;
    }

    public Cursor checkLoginPassword(String Login, String Password){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users where Email=? and Password=?",new String[]{Login,Password});
        DB = this.getWritableDatabase();
        if(cursor.getCount()>0) return DB.rawQuery("Select Role_ID from Users where Email=? and Password=?",new String[]{Login,Password});
        else return null;
    }
}
