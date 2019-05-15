package com.example.morri.messingaround;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

import java.util.ArrayList;
import java.util.List;

public class trade_interface extends AppCompatActivity {
    DatabaseHelper myDb;
    List<Item> firstItem;
    ArrayList<String> itemNames;
    ArrayList<String> itemDescriptions;
    ArrayList<String> itemPrices;
    ArrayList<Bitmap> itemImages;
    ArrayList<Integer> itemIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Trade");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_interface);
        myDb = new DatabaseHelper(this);
        itemNames = new ArrayList<String>();
        itemDescriptions = new ArrayList<String>();
        itemPrices = new ArrayList<String>();
        itemImages = new ArrayList<Bitmap>();
        itemIDs = new ArrayList<Integer>();
        Cursor items = myDb.getAllItemData();
        if(items.getCount() == 0){
            return;
        }
        while(items.moveToNext()){
            int id = items.getInt(0);
            String name = items.getString(1);
            String description = items.getString(2);
            Bitmap image = myDb.getItemImage(id);
            double price = items.getDouble(5);
            String priceStr = Double.toString(price);
            itemIDs.add(id);
            itemNames.add(name);
            itemDescriptions.add(description);
            itemPrices.add(priceStr);
            itemImages.add(image);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(itemIDs, itemNames, itemDescriptions, itemPrices, itemImages, trade_interface.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(trade_interface.this));

    }
}
