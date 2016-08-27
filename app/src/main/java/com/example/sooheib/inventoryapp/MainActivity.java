package com.example.sooheib.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sooheib on 8/27/16.
 */
public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        dbOpenHelper dbOpenHelper = new dbOpenHelper(this);

        ListView listView = (ListView) findViewById(R.id.inventory_listView);
        // Inserting
        TextView empty = (TextView) findViewById(R.id.empty);

        ArrayList<Product> listArray = dbOpenHelper.readInventory();
        if (listArray.size() == 0) {
            empty.setText("Empty Inventory");
        } else {
            empty.setText("");
        }
        ListViewAdapter customAdapter = new ListViewAdapter(listArray);
        customAdapter.notifyDataSetChanged();
        listView.setAdapter(customAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbOpenHelper dbOpenHelper = new dbOpenHelper(this);

        ListView listView = (ListView) findViewById(R.id.inventory_listView);
        // Inserting
        TextView empty = (TextView) findViewById(R.id.empty);

        ArrayList<Product> listArray = dbOpenHelper.readInventory();
        if (listArray.size() == 0) {
            empty.setText("Empty Inventory");
        } else {
            empty.setText("");
        }
        ListViewAdapter customAdapter = new ListViewAdapter(listArray);
        customAdapter.notifyDataSetChanged();
        listView.setAdapter(customAdapter);
    }

    public void addNewItem(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtra("HEADER", "Add a New Product");
        startActivity(intent);
    }
}
