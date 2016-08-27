package com.example.sooheib.inventoryapp;

import android.provider.BaseColumns;

/**
 * Created by sooheib on 8/27/16.
 */
public final class dbContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA = ",";

    private dbContract() {
        //default constructor
    }

    public static abstract class Table implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "inventory";
        // Table Columns names
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_QUANTITY = "quantity";
        public static final String KEY_PRICE = "price";
        public static final String KEY_IMAGE = "image";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY UNIQUE ," +
                        KEY_NAME + TEXT_TYPE + COMMA +
                        KEY_QUANTITY + " INTEGER" + COMMA +
                        KEY_PRICE + " REAL" + COMMA +
                        KEY_IMAGE + TEXT_TYPE + " )";

        public static final String DELETE_TABLE = "DROP TABLE " + TABLE_NAME;
    }
}
