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
    Button buyButton;
    Button message_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Search");
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
        buyButton = (Button) findViewById(R.id.buyButton);
        String name = getIntent().getStringExtra("name");
        name = "Name: " + name;
        String description = getIntent().getStringExtra("description");
        description = "Description: " + description;
        String listDate = getIntent().getStringExtra("listDate");
        listDate = "Created Date: " + listDate;
        int quantity = getIntent().getIntExtra("quantity", -1);
        String quantityStr = Integer.toString(quantity);
        quantityStr = "Quantity: " + quantityStr;
        double price = getIntent().getDoubleExtra("price", -1);
        String priceStr = Double.toString(price);
        priceStr = "Price: " + priceStr;
        String sellerName = getIntent().getStringExtra("sellerName");
        sellerName = "Seller: " + sellerName;
        nameText.setText(name);
        descriptionText.setText(description);
        listDateText.setText(listDate);
        quantityText.setText(quantityStr);
        priceText.setText(priceStr);
        sellerText.setText(sellerName);
        message_button = (Button) findViewById(R.id.message_button_);///jake
        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomessage = new Intent(ItemResults.this, sms_mess.class);
                gotomessage.putExtra("sellerName",gotomessage);
                startActivity(gotomessage);
            }
        });///jake
        back();
        buy();
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

    public void buy(){
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemID = getIntent().getIntExtra("id", -1);
                Intent buyIntent = new Intent(ItemResults.this, Item_Activity.class);
                buyIntent.putExtra("itemID", itemID);
                startActivity(buyIntent);
            }
        });
    }
}
