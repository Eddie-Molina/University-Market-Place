package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MemberResults extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView usernameText;
    TextView emailText;
    TextView phoneText;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_results);
        setTitle("Member Info");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        usernameText = (TextView) findViewById(R.id.usernameText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        backButton = (Button) findViewById(R.id.backButton);
        int userID = getIntent().getIntExtra("userID", -1);
        String userIDstr = Integer.toString(userID);
        Cursor user = myDb.generalSearch("users", "ID = ?", userIDstr);
        user.moveToNext();
        String fname = user.getString(1);
        String lname = user.getString(2);
        String username = user.getString(6);
        String email = user.getString(3);
        String phone = user.getString(4);
        String name = fname + " " + lname;
        nameText.setText("Name: " + name);
        usernameText.setText("Username: " + username);
        emailText.setText("Email: " + email);
        phoneText.setText("Phone: " + phone);
        back();
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent backIntent = new Intent(MemberResults.this, search_club.class);
                backIntent.putExtra("clubName", clubName);
                startActivity(backIntent);
            }
        });
    }
}
