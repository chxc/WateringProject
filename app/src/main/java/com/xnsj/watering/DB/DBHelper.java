package com.xnsj.watering.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库的创建类
 * */
public class DBHelper extends SQLiteOpenHelper {
    private final static String dbName="wateringDb";//数据库名字

    /*
     * 2为增加zip_app_version字段
     * */
    public DBHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建游戏关卡数据库
        sqLiteDatabase.execSQL("create table " + "game_level_info ( " +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "level integer unique, " + //关卡级别
                "pass_time long" +//通过时间
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
