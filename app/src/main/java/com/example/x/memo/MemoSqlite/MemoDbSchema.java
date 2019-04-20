package com.example.x.memo.MemoSqlite;

public class MemoDbSchema {
    public static final class MemoTable{
        public static final String NAME="MEMO";
        public static final class Cols{
            public static final String ID="id";//int
            public static final String DATE="date";
            public static final String PATH="picturepath";
            public static final String HIDE="hide";//int 0/1
        }
    }
}
