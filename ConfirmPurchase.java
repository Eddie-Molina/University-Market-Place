package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmPurchase extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView descriptionText;
    TextView priceText;
    TextView sellerText;
    Button confirmButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);
        setTitle("Confirm Purchase");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        priceText = (TextView) findViewById(R.id.priceText);
        sellerText = (TextView) findViewById(R.id.sellerText);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        confirm();
        cancel();
        int itemID = getIntent().getIntExtra("itemID", -1);
        String itemIDstr = Integer.toString(itemID);
        Cursor item = myDb.generalSearch("items_services", "ID = ?", itemIDstr);
        item.moveToNext();
        String name = item.getString(1);
        String description = item.getString(2);
        double price = item.getDouble(5);
        String priceStr = Double.toString(price);
        int sellerID = item.getInt(6);
        String sellerIDstr = Integer.toString(sellerID);
        Cursor seller = myDb.generalSearch("users", "ID = ?", sellerIDstr);
        seller.moveToNext();
        String fname = seller.getString(1);
        String lname = seller.getString(2);
        String sellerName = fname + " " + lname;
        nameText.setText("Name: " + name);
        descriptionText.setText("Description: " + description);
        priceText.setText("Price: " + priceStr);
        sellerText.setText("Seller: " + sellerName);
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(ConfirmPurchase.this, Item_Activity.class);
                int itemID = getIntent().getIntExtra("itemID", -1);
                cancelIntent.putExtra("itemID", itemID);
                startActivity(cancelIntent);
            }
        });
    }

    public void confirm(){
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemID = getIntent().getIntExtra("itemID", -1);
                String itemIDstr = Integer.toString(itemID);
                Cursor item = myDb.generalSearch("items_services", "ID = ?", itemIDstr);
                item.moveToNext();
                int quantity = item.getInt(4);
                quantity -= 1;
                if(quantity <= 0){
                    int res = myDb.deleteItem(itemIDstr);
                    if(res > 0){
                        Toast.makeText(ConfirmPurchase.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                        Intent purchaseIntent = new Intent(ConfirmPurchase.this, TradeMenu.class);
                        startActivity(purchaseIntent);
                    } else {
                        Toast.makeText(ConfirmPurchase.this, "Could Not Purchase Item", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(myDb.updateItemQuantity(itemID, quantity)){
                        Toast.makeText(ConfirmPurchase.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                        Intent purchaseIntent = new Intent(ConfirmPurchase.this, TradeMenu.class);
                        startActivity(purchaseIntent);
                    } else {
                        Toast.makeText(ConfirmPurchase.this, "Could Not Purchase Item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
