package com.example.fivechessfront.Entity;

import android.content.Context;

import com.example.fivechessfront.utils.MyHelper;

//存储游戏记录（涵盖所有对局:账户 输赢情况 棋子颜色 比赛记录）
//eg: 123 WIN black 132456765373(每两个数字表示一个棋子位置，黑子先手，黑白交替)
//name text, result CHAR(5), color CHAR(5), process String
public class GameHistory {
    final MyHelper sqlHelper;
    String name;//用户名
    String result;//比赛输赢
    String color;//使用棋子颜色
    String process;//比赛过程
    int    cnt;//回合数
    String DATE_FORMAT;//比赛结束时间

    public GameHistory(Context context) {
        sqlHelper = new MyHelper(context);
    }

    public int getCnt() {return cnt;}
    public void setCnt(int cnt) {this.cnt = cnt;}

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getProcess() {
        return process;
    }
    public void setProcess(String process) {
        this.process = process;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getDATE_FORMAT() {
        return DATE_FORMAT;
    }
    public void setDATE_FORMAT(String DATE_FORMAT) {
        this.DATE_FORMAT = DATE_FORMAT;
    }

    public void WriteProcess(Position position){
        process+=Integer.toString(position.col)+ position.row;
    }

    public void SubmitToSql(){
        //TODO 完成将GameHistory数据提交到数据库操作
    }

    @Override
    public String toString() {
        return "GameHistory{" +
                "name='" + name + '\'' +
                ", result='" + result + '\'' +
                ", color='" + color + '\'' +
                ", process='" + process + '\'' +
                ", cnt=" + cnt +
                ", DATE_FORMAT='" + DATE_FORMAT + '\'' +
                '}';
    }
}
