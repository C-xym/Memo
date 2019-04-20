package com.example.x.memo.MemoSqlite;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.x.memo.Base.MemoData;

public class MemoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MemoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public MemoData getMemo(){

        int id= getInt(getColumnIndex(MemoDbSchema.MemoTable.Cols.ID));
        String date=getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.DATE));
        String path=getString(getColumnIndex(MemoDbSchema.MemoTable.Cols.PATH));
        int hide=getInt(getColumnIndex(MemoDbSchema.MemoTable.Cols.HIDE));

        MemoData memoData=new MemoData(id,date,path,hide);
        return memoData;
    }
}
