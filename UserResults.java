package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserResults extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView emailText;
    TextView phoneText;
    TextView usernameText;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Search");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_results);
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        usernameText = (TextView) findViewById(R.id.usernameText);
        backButton = (Button) findViewById(R.id.backButton);
        String name = getIntent().getStringExtra("name");
        name = "Name: " + name;
        String username = getIntent().getStringExtra("username");
        username = "Username: " + username;
        String email = getIntent().getStringExtra("email");
        email = "Email: " + email;
        String phone = getIntent().getStringExtra("phone");
        phone = "Phone: " + phone;
        nameText.setText(name);
        usernameText.setText(username);
        emailText.setText(email);
        phoneText.setText(phone);
        back();
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(UserResults.this, Search.class);
                startActivity(searchIntent);
            }
        });
    }
}
