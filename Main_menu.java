package com.example.morri.messingaround;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main_menu extends AppCompatActivity {
    DatabaseHelper myDb;
    Button searchButton;
    Button email_interface;
    Button sms_message;
    Button trade_interface_button;
    Button club_management_button;
    Button exit_button;

    @TargetApi(Build.VERSION_CODES.M)//check if neccessary
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("University Bazaar System");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        myDb = new DatabaseHelper(this);
        searchButton = (Button) findViewById(R.id.searchButton);
        email_interface = (Button) findViewById(R.id.email_button);
        sms_message = (Button) findViewById(R.id.this_button);//sms message button
        trade_interface_button = (Button) findViewById(R.id.trade_);
        club_management_button = (Button) findViewById(R.id.club_interface_);
        exit_button = (Button) findViewById(R.id.exit_button_);

        sms_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//call sms messaging
                Intent mess_ = new Intent(Main_menu.this, sms_mess.class);
                startActivity(mess_);

            }
        });

        email_interface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email_ = new Intent(Main_menu.this, email_Interface.class);
                startActivity(email_);
            }
        });
        trade_interface_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trade_activity = new Intent(Main_menu.this, TradeMenu.class);
                startActivity(trade_activity);
            }
        });


        club_management_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent club_manag_ = new Intent(Main_menu.this, ClubMenu.class);
                startActivity(club_manag_);
            }
        });
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.resetCurrentUser();
                Intent gotoLogin = new Intent(Main_menu.this, Login.class);
                startActivity(gotoLogin);
            }
        });
        search();
    }
    public void search(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(Main_menu.this, Search.class);
                startActivity(searchIntent);
            }
        });
    }
}
