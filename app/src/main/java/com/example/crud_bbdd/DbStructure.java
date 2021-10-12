package com.example.crud_bbdd;

import android.provider.BaseColumns;

public class DbStructure {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbStructure() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME = "id";
        public static final String COLUMN_NAME2 = "name";
        public static final String COLUMN_NAME3 = "surname";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME2 + " TEXT," +
                    FeedEntry.COLUMN_NAME3 + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

}
