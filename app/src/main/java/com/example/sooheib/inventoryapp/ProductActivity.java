package com.example.sooheib.inventoryapp;

import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


/**
 * Created by sooheib on 8/27/16.
 */
public class ProductActivity extends AppCompatActivity {
    public int id;
    public String pName;
    public Product product = new Product();
    public static double priceProduct;
    public int quantity1;
    dbOpenHelper dbOpenHelper = new dbOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        TextView product = (TextView) findViewById(R.id.productName);
        TextView price = (TextView) findViewById(R.id.productPrice1);
        TextView quantity = (TextView) findViewById(R.id.productQuantity);
        TextView orderQuantity = (TextView) findViewById(R.id.order_quantity);

        Intent details = getIntent();
        setTitle("Product Details");
        pName = details.getStringExtra("productName");
        product.setText(pName);
        id  = details.getIntExtra("id",0);
        ContextWrapper cw = new ContextWrapper(this);
        File dir = cw.getFilesDir();
        quantity1 = details.getIntExtra("productQuantity", 0);
        quantity.setText("" + quantity1);
        priceProduct = details.getDoubleExtra("productPrice", 0.0);
        price.setText("" + priceProduct);
        orderQuantity.setText("" + quantity1);

        // Load product image
        String imageLocationDir = dir.toString();
        id = details.getIntExtra("id", 0);
        String imagePath = imageLocationDir + "/" + id;
        ImageView imageView = (ImageView) findViewById(R.id.imgIcon);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);
    }

    public void incrementQuantity(View view) {
        if (quantity1 == 100) {
            return;
        }
        quantity1 += 1;
        displayQuantity(quantity1);
        //Only update db once user clicks "Update"
    }

    public void decrementQuantity(View view) {
        if (quantity1 == 0) {
            return;
        }
        quantity1 -= 1;
        displayQuantity(quantity1);
        //Only update db once user clicks "Update"
    }

    private void displayQuantity(int count) {
        TextView quantity = (TextView) findViewById(R.id.order_quantity);
        quantity.setText("" + count);
    }

    public void onClickDelete(final View view) {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItemPermanently(id);
                        Toast.makeText(view.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void deleteItemPermanently(int id) {
        dbOpenHelper.deleteProduct(id);

    }

    public void onClickReorder(View view) {
        String subject = "Reorder from AAApps";
        String message = "Product Name: " + pName +
                "\nProduct Price: " + priceProduct +
                "\nQuantity To be ordered: " + quantity1;
        String[] emails = {"reorder@outlook.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void onClickUpdate(View view){
        boolean success = dbOpenHelper.updateQuantity(id, quantity1);
        Log.e("db update success: ", Boolean.toString(success));
        dbOpenHelper.close();
        TextView quantity = (TextView) findViewById(R.id.productQuantity);
        quantity.setText("" + quantity1);
        Toast.makeText(this, "Product quantity updated", Toast.LENGTH_SHORT).show();
    }

}
