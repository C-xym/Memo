package com.example.x.memo.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.x.memo.MemoSqlite.MemoBaseHelper;
import com.example.x.memo.MemoSqlite.MemoCursorWrapper;
import com.example.x.memo.MemoSqlite.MemoDbSchema;

import java.util.ArrayList;
import java.util.List;

public class MemoList {

    private static MemoList sMemoList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MemoList get(Context context){
        if(sMemoList==null){
            sMemoList=new MemoList(context);
        }
        return sMemoList;
    }

    private MemoList(Context context){
        mContext=context.getApplicationContext();
        mDatabase=new MemoBaseHelper(mContext).getWritableDatabase();
    }

    public List<MemoData> getMemos(){

        List<MemoData> memoDatas=new ArrayList<>();
        MemoCursorWrapper cursor=queryMemo(null,null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                memoDatas.add(cursor.getMemo());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return memoDatas;
    }
    public List<MemoData> getMemos(int hide){

        List<MemoData> memoDatas=new ArrayList<>();
        MemoCursorWrapper cursor=queryMemo(MemoDbSchema.MemoTable.Cols.HIDE+"=?",
                new String[]{String.valueOf(hide)});
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                memoDatas.add(cursor.getMemo());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return memoDatas;
    }

    public MemoData getMemo(int id){

        MemoData memoData=null;
        MemoCursorWrapper cursor=queryMemo(
                MemoDbSchema.MemoTable.Cols.ID+"=?",
                new String[]{String.valueOf(id)}
        );
        try {
            if(cursor.getCount()!=0){
                cursor.moveToFirst();
                memoData=cursor.getMemo();
            }
        }finally {
            cursor.close();
        }
        return memoData;
    }

    public void updateMemo(MemoData memoData){
        int id=memoData.getmId();
        ContentValues values=getContentValues(memoData);
        mDatabase.update(MemoDbSchema.MemoTable.NAME,values, MemoDbSchema.MemoTable.Cols.ID+"=?",new String[]{String.valueOf(id)});
    }

    public void deleteMemo(int id){
        mDatabase.delete(MemoDbSchema.MemoTable.NAME,MemoDbSchema.MemoTable.Cols.ID+"=?",new String[]{String.valueOf(id)});
    }

    private static ContentValues getContentValues(MemoData memoData){
        ContentValues values=new ContentValues();
        values.put(MemoDbSchema.MemoTable.Cols.ID ,memoData.getmId());
        values.put(MemoDbSchema.MemoTable.Cols.DATE ,memoData.getmDate());
        values.put(MemoDbSchema.MemoTable.Cols.PATH ,memoData.getmFilePath());
        values.put(MemoDbSchema.MemoTable.Cols.HIDE,memoData.getmHide());
        return values;
    }

    private MemoCursorWrapper queryMemo(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(
                MemoDbSchema.MemoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new MemoCursorWrapper(cursor);
    }

    public void addMemo(MemoData memoData){

        ContentValues values=getContentValues(memoData);
        mDatabase.insert(MemoDbSchema.MemoTable.NAME,null,values);
    }
}
