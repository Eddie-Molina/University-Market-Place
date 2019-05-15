package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TradeMenu extends AppCompatActivity {
    Button sellButton;
    Button removeButton;
    Button buyButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_menu);
        setTitle("Trade");
        sellButton = (Button) findViewById(R.id.sellButton);
        removeButton = (Button) findViewById(R.id.removeButton);
        buyButton = (Button) findViewById(R.id.buyButton);
        backButton = (Button) findViewById(R.id.backButton);
        sell();
        remove();
        buy();
        back();
    }

    public void sell(){
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sellIntent = new Intent(TradeMenu.this, ItemCreate.class);
                startActivity(sellIntent);
            }
        });
    }

    public void remove(){
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent removeIntent = new Intent(TradeMenu.this, ItemRemove.class);
                startActivity(removeIntent);
            }
        });
    }

    public void buy(){
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent buyIntent = new Intent(TradeMenu.this, trade_interface.class);
                startActivity(buyIntent);
            }
        });
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(TradeMenu.this, Main_menu.class);
                startActivity(backIntent);
            }
        });
    }
}
