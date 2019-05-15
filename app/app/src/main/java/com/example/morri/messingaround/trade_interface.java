package com.example.morri.messingaround;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class trade_interface extends AppCompatActivity {

    List<Item> firstItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_interface);

        firstItem = new ArrayList<>();
        firstItem.add(new Item("DSP Book", "Book", R.drawable.dsp));
        firstItem.add(new Item("Don't Mess With Texas", "Hat", R.drawable.dmwt));
        firstItem.add(new Item("iPhone", "Phone", R.drawable.iphone));
        firstItem.add(new Item("Pen", "Pen", R.drawable.pen));
        firstItem.add(new Item("Software Engineering Book", "Book", R.drawable.sftwrebook));
        firstItem.add(new Item("DSP Book", "Book", R.drawable.dsp));
        firstItem.add(new Item("Don't Mess With Texas", "Hat", R.drawable.dmwt));
        firstItem.add(new Item("iPhone", "Phone", R.drawable.iphone));
        firstItem.add(new Item("Pen", "Pen", R.drawable.pen));
        firstItem.add(new Item("Software Engineering Book", "Book", R.drawable.sftwrebook));
        firstItem.add(new Item("DSP Book", "Book", R.drawable.dsp));
        firstItem.add(new Item("Don't Mess With Texas", "Hat", R.drawable.dmwt));
        firstItem.add(new Item("iPhone", "Phone", R.drawable.iphone));
        firstItem.add(new Item("Pen", "Pen", R.drawable.pen));
        firstItem.add(new Item("Software Engineering Book", "Book", R.drawable.sftwrebook));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id );
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,  firstItem );
        myrv.setLayoutManager(new GridLayoutManager(this, 3 ));
        myrv.setAdapter(myAdapter);



    }
}
