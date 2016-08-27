package com.example.sooheib.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sooheib on 8/27/16.
 */
public class dbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Inventory";

    public dbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("QUERY:", dbContract.Table.CREATE_TABLE);////////////////////////////
        db.execSQL(dbContract.Table.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dbContract.Table.DELETE_TABLE);
        onCreate(db);
    }

    public ArrayList<Product> readInventory(){
        ArrayList<Product> productList = new ArrayList<Product>();
        String query = "SELECT * FROM " + dbContract.Table.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.setmID(Integer.parseInt(cursor.getString(0)));
                product.setmProduct(cursor.getString(1));
                product.setmQuantity(cursor.getInt(2));
                product.setmPrice(cursor.getDouble(3));
                productList.add(product);
            }while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return productList;
    }
    // Add Product
    void addEntry(Product newEntry) {
        //Create a Database Connection and insert row into database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Table.KEY_NAME, newEntry.getmProduct());
        values.put(dbContract.Table.KEY_QUANTITY, newEntry.getmQuantity());
        values.put(dbContract.Table.KEY_PRICE, newEntry.getmPrice());
        //Add image here--------------------------
        db.insert(dbContract.Table.TABLE_NAME, null, values);
        db.close(); // Close database connection
    }

    // Update single entry
    public void updateProduct(double id, Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbContract.Table.KEY_NAME, product.getmProduct());
        values.put(dbContract.Table.KEY_PRICE, product.getmPrice());
        values.put(dbContract.Table.KEY_QUANTITY, product.getmQuantity());
        db.update(dbContract.Table.TABLE_NAME, values, dbContract.Table.KEY_ID + "=" + id, null);
        db.close();
    }

    // Delete single entry
    public void deleteProduct(double id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(dbContract.Table.TABLE_NAME, dbContract.Table.KEY_ID + "=" + id, null);
        sqLiteDatabase.close();
    }

    // update single entry quantity
    public boolean updateQuantity(double id, int quantity){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbContract.Table.KEY_QUANTITY, quantity);
        int rowsUpdated = sqLiteDatabase.update(dbContract.Table.TABLE_NAME, cv, dbContract.Table.KEY_ID+"="+id, null);
        boolean success = false;
        if(rowsUpdated==1)success=true;
        sqLiteDatabase.close();
        return success;
    }

    public long rowCount(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, dbContract.Table.TABLE_NAME);
        sqLiteDatabase.close();
        return count;
    }
}