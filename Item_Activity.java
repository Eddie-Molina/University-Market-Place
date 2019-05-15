package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Item_Activity extends AppCompatActivity {

    private TextView tvName, tvdescription, tvcategory;
    private ImageView img;
    Button message_button;
    Button cash_button;
    Button backButton;
    DatabaseHelper myDB;
    String seller = "Greg Oldman";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTitle("Trade");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_);
        myDB = new DatabaseHelper(this);
        tvName = (TextView) findViewById(R.id.txtName);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView)  findViewById(R.id.itemthumbnail);
        message_button = (Button) findViewById(R.id.message_button_);
        cash_button = (Button) findViewById(R.id.cash_button_) ;
        backButton = (Button) findViewById(R.id.backButton);
        back();
        // Receive Data
        int id = getIntent().getIntExtra("itemID", -1);
        String idStr = Integer.toString(id);
        Cursor item = myDB.generalSearch("items_services", "ID = ?", idStr);
        item.moveToNext();
        String name = item.getString(1);
        String description = item.getString(2);
        Bitmap image = myDB.getItemImage(id);
        tvName.setText(name);
        tvdescription.setText(description);
        if(image != null){
            img.setImageBitmap(image);
        }
        Button button1 = (Button)findViewById(R.id.payCard);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(), creditcard.class);
                //startActivity(i);
                int id = getIntent().getIntExtra("itemID", -1);
                Intent i = new Intent(Item_Activity.this, creditcard.class);
                i.putExtra("itemID", id);
                startActivity(i);
            }
        });
        cash_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Item_Activity. this,"Cash Payment on Delivery", Toast.LENGTH_SHORT ).show();
                //Toast.makeText(Item_Activity.this,"A message has been sent to seller", Toast.LENGTH_SHORT ).show();
                int id = getIntent().getIntExtra("itemID", -1);
                String idStr = Integer.toString(id);
                Cursor group = myDB.generalSearch("items_services", "ID = ?", idStr);
                group.moveToNext();
                String name = group.getString(1);
                double price = group.getDouble(5);
                String priceStr = Double.toString(price);
                myDB.showMessage("Cash Payment on Delivery", "A message has been sent to the seller.\n" +
                 "Item: " + name + "\nPrice: " + priceStr, Item_Activity.this);
                //need to get deletion functions hard coded currently file: TradeInterface.java
            }
        });
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomessanger = new Intent(Item_Activity.this, sms_mess.class);
                gotomessanger.putExtra("keyname",seller);
                startActivity(gotomessanger);
            }
        });

    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(Item_Activity.this, TradeMenu.class);
                startActivity(backIntent);
            }
        });
    }



}
