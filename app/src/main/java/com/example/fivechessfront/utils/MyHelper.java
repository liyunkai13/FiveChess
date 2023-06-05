package com.example.fivechessfront.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.fivechessfront.Entity.Account;
import com.example.fivechessfront.Entity.GameHistory;

import java.util.ArrayList;
import java.util.List;

//数据库核心代码
//记得关注Entity/Account 以及Entity/GameHistory
public class MyHelper extends SQLiteOpenHelper {
    //数据库的名字
    private static final String DB_NAME = "mySQLite.db";
    private static final int VERSION = 2;
    //表格的名字
    private static final String TABLE_NAME_1 = "database1";//本表仅用于存储账号、密码
    private static final String TABLE_NAME_2 = "database2";//本表用于存储游戏记录（涵盖所有对局:账户 输赢情况 棋子颜色 比赛记录 回合数 比赛时间）

    private Context context;

    //创建数据库
    //数据库1：保存用户账号密码信息
    //数据库1 eg: 1234 haha
    //数据库1：name text, password text
    //数据库2：存储游戏记录（涵盖所有对局:账户 输赢情况 棋子颜色 比赛记录 回合数 比赛时间）
    //数据库2 eg: 123 WIN black 132456765373(每两个数字表示一个棋子位置，黑子先手，黑白交替) 2023-05-11
    //数据库2：name text, result CHAR(5), color CHAR(5), process String , int cnt , DATE_FORMAT String
    private static final String CREATE_TABLE1_SQL = "create table " + TABLE_NAME_1 +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, name text, password text)";
    private static final String CREATE_TABLE2_SQL = "create table " + TABLE_NAME_2 +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, name text, result CHAR(5), color CHAR(5), process String,cnt int,DATE_FORMAT String)";
    public MyHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1_SQL);
        db.execSQL(CREATE_TABLE2_SQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //注册账号后插入数据
    public long insertNewAccount(Account account){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", account.getName());
        values.put("password", account.getPassword());
        return db.insert(TABLE_NAME_1,null,values);
    }

    //根据账号密码查询
    //提醒：在后续的判断之中，返回的userList是否为空即为判断标准
    public List<Account> selectByAccountAndPass(String name, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_1, null,
                "name=? and password=?", new String[]{name, password}, null, null, null);
        List<Account> accountList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name1 = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String password1 = cursor.getString(cursor.getColumnIndex("password"));
                Account user = new Account(name1,password1);
                accountList.add(user);
            }
            cursor.close();
            return accountList;
        }


        return null;
    }

    //插入游戏对局所有信息
    public long insertGameData(String name, String result, String color, String process, int cnt,String DATE_FORMAT){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("result",result);
        values.put("color",color);
        values.put("cnt",cnt);
        values.put("process",process);
        values.put("DATE_FORMAT",DATE_FORMAT);

        return db.insert(TABLE_NAME_2,null,values);
    }
    //查询游戏对局所有信息
    public List<GameHistory> selectGameHistory(Account account) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME_2, null,
                "name = ?", new String[]{account.getName()},
                null, null, null);
        List<GameHistory> GameHistoryList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name1 = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String result1 = cursor.getString(cursor.getColumnIndex("result"));
                @SuppressLint("Range") String color1 = cursor.getString(cursor.getColumnIndex("color"));
                @SuppressLint("Range") String process1 = cursor.getString(cursor.getColumnIndex("process"));
                @SuppressLint("Range") String cnt1 = cursor.getString(cursor.getColumnIndex("cnt"));
                @SuppressLint("Range") String DATE_FORMAT1 = cursor.getString(cursor.getColumnIndex("DATE_FORMAT"));
                GameHistory gameHistory = new GameHistory(context);
                gameHistory.setName(name1);
                gameHistory.setColor(color1);
                gameHistory.setProcess(process1);
                gameHistory.setCnt(Integer.parseInt(cnt1));
                gameHistory.setResult(result1);
                gameHistory.setDATE_FORMAT(DATE_FORMAT1);
                GameHistoryList.add(gameHistory);
            }
            cursor.close();
            return GameHistoryList;
        }
        return null;
    }

}
