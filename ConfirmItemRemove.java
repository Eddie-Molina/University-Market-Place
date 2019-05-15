package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmItemRemove extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView descriptionText;
    TextView priceText;
    TextView quantityText;
    Button cancelButton;
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_item_remove);
        setTitle("Remove Item");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        priceText = (TextView) findViewById(R.id.priceText);
        quantityText = (TextView) findViewById(R.id.quantityText);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        removeButton = (Button) findViewById(R.id.removeButton);
        cancel();
        remove();
        int itemID = getIntent().getIntExtra("itemID", -1);
        String itemIDstr = Integer.toString(itemID);
        Cursor item = myDb.generalSearch("items_services", "ID = ?", itemIDstr);
        item.moveToNext();
        String name = item.getString(1);
        String description = item.getString(2);
        double price = item.getDouble(5);
        String priceStr = Double.toString(price);
        int quantity = item.getInt(4);
        String quantityStr = Integer.toString(quantity);
        nameText.setText("Name: " + name);
        descriptionText.setText("Description: " + description);
        priceText.setText("Price: " + price);
        quantityText.setText("Quantity: " + quantity);
    }

    public void remove(){
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemID = getIntent().getIntExtra("itemID", -1);
                String itemIDstr = Integer.toString(itemID);
                int res = myDb.deleteItem(itemIDstr);
                if(res > 0){
                    Toast.makeText(ConfirmItemRemove.this, "Item Removed", Toast.LENGTH_SHORT).show();
                    Intent removeIntent = new Intent(ConfirmItemRemove.this, TradeMenu.class);
                    startActivity(removeIntent);
                } else {
                    Toast.makeText(ConfirmItemRemove.this, "Could Not Remove Item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(ConfirmItemRemove.this, ItemRemove.class);
                startActivity(cancelIntent);
            }
        });
    }
}
