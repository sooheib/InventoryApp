package com.example.sooheib.inventoryapp;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sooheib on 8/27/16.
 */
public class AddProductActivity extends AppCompatActivity {
    public String price;
    public String quantity;
    public String name;
    public long nextID;

    public Product product = new Product();
    dbOpenHelper dbOpenHelper = new dbOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);///// need to create
        nextID = (dbOpenHelper.rowCount() + 1);
        Intent intent = getIntent();
        String message = intent.getStringExtra("Add New Product");
        setTitle(message);
    }

    public void onClickSubmit(View view) {
        EditText nameText = (EditText) findViewById(R.id.productName);
        EditText priceText = (EditText) findViewById(R.id.productPrice);
        EditText quantityText = (EditText) findViewById(R.id.productQuantity);
        ImageView img = (ImageView) findViewById(R.id.imageSelected);
        name = nameText.getText().toString();
        price = (priceText.getText().toString());
        quantity = (quantityText.getText().toString());

        if (nameText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Name can\'t be empty", Toast.LENGTH_LONG).show();
            nameText.setError("Name can\'t be empty");
            return;
        } else if (priceText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Price Needed", Toast.LENGTH_LONG).show();
            priceText.setError("Invalid Price");
            return;
        } else if (quantityText.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Quantity Needed", Toast.LENGTH_LONG).show();
            quantityText.setError("Invalid Input");
            return;
        } else if (img.getDrawable() == null) {
            Toast.makeText(getApplicationContext(), "Upload an image", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            dbOpenHelper.addEntry(new Product(name, Integer.parseInt(quantity), Double.parseDouble(price)));
            Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Log.v("VALUES : ", name + " " + price + " " + quantity);
        }
    }


    public void btnImageOnClick(View view) {
        Intent intent = new Intent();
        // Accept only images
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 &&
                resultCode == RESULT_OK && null != data) {
            Toast.makeText(this, "Uploading...", Toast.LENGTH_LONG).show();
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ImageView imageView = (ImageView) findViewById(R.id.imageSelected);
                imageView.setImageBitmap(bitmap);
                String filename = Long.toString(nextID);
                saveToInternalStorage(bitmap, filename);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Could not load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToInternalStorage(Bitmap bmp, String filename) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File appDirectory = contextWrapper.getFilesDir();

        File currentPath = new File(appDirectory, filename);

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(currentPath);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
