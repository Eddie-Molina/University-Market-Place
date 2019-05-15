package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Item_Activity extends AppCompatActivity {

    private TextView tvName, tvdescription, tvcategory;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_);

        tvName = (TextView) findViewById(R.id.txtName);
        tvdescription = (TextView) findViewById(R.id.txtDesc);
        tvcategory = (TextView) findViewById(R.id.txtCat);
        img = (ImageView)  findViewById(R.id.itemthumbnail);




        // Receive Data
        Intent intent = getIntent();
        String Name = intent.getExtras().getString("Name");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");

        // Setting Values
        tvName.setText(Name);
        tvdescription.setText(Description);
        img.setImageResource(image);


    }
}
