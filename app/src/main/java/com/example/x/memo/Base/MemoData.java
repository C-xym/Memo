package com.example.x.memo.Base;

import android.content.SharedPreferences;

import com.example.x.memo.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class MemoData {

    private static int ID=0;

    //写入数据库
    private int mId;
    private String mDate;//2019-01-01 00:00
    private String mFilePath;
    private boolean mHide;

    private ArrayList<String> mContext;
    private ArrayList<String> mPicPath;
    private ArrayList<String> mVicPath;

    private boolean PIC;
    private boolean VIC;

    public MemoData(){

        this.mId=ID;
        ID++;

        this.mDate=new Date().get();

        this.mHide=false;
        this.PIC=false;
        this.VIC=false;

    }
    public MemoData(int id,String date,String filepath,int hide){
        this.mId=id;
        this.mDate=date;
        this.mFilePath=filepath;
        if(hide==0){
            mHide=false;
        }else {
            mHide=true;
        }

    }

    public String getSortContext(){
        String sContext="";
       /*

        if(mContext.length()>20)
        {
            sContext=mContext.substring(0,20);
            sContext+="...";
        }
        else {
            sContext=mContext;
        }
        */
        return sContext;
    }

    public int getmId() {
        return mId;
    }
    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmDate() {
        return mDate;
    }
    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmFilePath() {
        return mFilePath;
    }
    public void setmFilePath(String mFilePath) {
        this.mFilePath = mFilePath;
    }

    public int getmHide() {
        if(mHide){
            return 1;
        }
        return 0;
    }
    public void setmHide(boolean mHide) {
        this.mHide = mHide;
    }

    public static void setID(int id){
        MemoData.ID=id;
    }//应用开始前初始化
    public static int getID(){
        return ID;
    }//应用结束前保存

    public String getDateF(){
        String s=mDate.substring(0,10);
        return s;
    }
    public String getDateT(){
        String s=mDate.substring(11,16);
        return s;
    }

    public boolean isPIC(){
        return PIC;
    }
    public boolean isVIC() {
        return VIC;
    }

    class Date {
        int Year;
        int Mon;
        int Day;
        int Hour;
        int Min;
        public Date()
        {
            Calendar calendar=Calendar.getInstance();
            Year=calendar.get(Calendar.YEAR);
            Mon=calendar.get(Calendar.MONTH)+1;
            Day=calendar.get(Calendar.DAY_OF_MONTH);
            Hour=calendar.get(Calendar.HOUR_OF_DAY);
            Min=calendar.get(Calendar.MINUTE);
        }

        public String get() {
            String S=Year+"-";
            if(Mon<10)
            {
                S+="0";
            }
            S=S+Mon+"-";
            if(Day<10)
            {
                S+="0";
            }
            S=S+Day+" ";
            if(Hour<10)
            {
                S+="0";
            }
            S=S+Hour+":";
            if(Min<10)
            {
                S+="0";
            }
            S=S+Min;
            return S;
        }
    }
}

