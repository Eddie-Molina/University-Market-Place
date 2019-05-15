package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupResults extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView createdDateText;
	TextView descriptionText;
    TextView adminNameText;
    Button backButton;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Search");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_results);
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        adminNameText = (TextView) findViewById(R.id.adminNameText);
        backButton = (Button) findViewById(R.id.backButton);
        String clubName = getIntent().getStringExtra("clubName");
        Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
        group.moveToNext();
        String name = group.getString(1);
        String description = group.getString(4);
        int adminID = group.getInt(3);
        String adminIDstr = Integer.toString(adminID);
        Cursor admin = myDb.generalSearch("users", "ID = ?", adminIDstr);
        admin.moveToNext();
        String fname = admin.getString(1);
        String lname = admin.getString(2);
        String adminName = fname + " " + lname;
        nameText.setText("Name: " + name);
        descriptionText.setText("Description: " + description);
        adminNameText.setText("Admin: " + adminName);
        back();

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
}
