package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemResults extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView descriptionText;
    TextView listDateText;
    TextView quantityText;
    TextView priceText;
    TextView sellerText;
    Button backButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_results);
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        listDateText = (TextView) findViewById(R.id.listDateText);
        quantityText = (TextView) findViewById(R.id.quantityText);
        priceText = (TextView) findViewById(R.id.priceText);
        sellerText = (TextView) findViewById(R.id.sellerText);
        backButton = (Button) findViewById(R.id.backButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String listDate = getIntent().getStringExtra("listDate");
        int quantity = getIntent().getIntExtra("quantity", -1);
        String quantityStr = Integer.toString(quantity);
        double price = getIntent().getDoubleExtra("price", -1);
        String priceStr = Double.toString(price);
        String sellerName = getIntent().getStringExtra("sellerName");
        nameText.setText(name);
        descriptionText.setText(description);
        listDateText.setText(listDate);
        quantityText.setText(quantityStr);
        priceText.setText(priceStr);
        sellerText.setText(sellerName);
        back();
        next();
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(ItemResults.this, Search.class);
                startActivity(searchIntent);
            }
        });
    }

    public void next(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent nextIntent = new Intent(ItemResults.this, foo.class);
                startActivity(nextIntent);*/
            }
        });
    }
}
