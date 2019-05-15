package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupResults extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView createdDateText;
    TextView adminNameText;
    Button backButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_results);
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        createdDateText = (TextView) findViewById(R.id.createdDateText);
        adminNameText = (TextView) findViewById(R.id.adminNameText);
        backButton = (Button) findViewById(R.id.backButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        String name = getIntent().getStringExtra("name");
        String createdDate = getIntent().getStringExtra("createdDate");
        String adminName = getIntent().getStringExtra("adminName");
        nameText.setText(name);
        createdDateText.setText(createdDate);
        adminNameText.setText(adminName);
        back();
        next();
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(GroupResults.this, Search.class);
                startActivity(searchIntent);
            }
        });
    }

    public void next(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent nextIntent = new Intent(GroupResults.this, foo.class);
                startActivity(nextIntent);*/
            }
        });
    }
}
