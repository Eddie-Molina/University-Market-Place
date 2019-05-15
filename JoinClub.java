package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class JoinClub extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView descriptionText;
    TextView adminText;
    Button requestButton;
    Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_club);
        setTitle("Join Club");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        adminText = (TextView) findViewById(R.id.adminText);
        requestButton = (Button) findViewById(R.id.requestButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        String clubName = getIntent().getStringExtra("clubName");
        Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
        group.moveToNext();
        int adminID = group.getInt(3);
        String adminIDstr = Integer.toString(adminID);
        Cursor admin = myDb.generalSearch("users", "ID = ?", adminIDstr);
        admin.moveToNext();
        String fname = admin.getString(1);
        String lname = admin.getString(2);
        String name = fname + " " + lname;
        String description = group.getString(4);
        nameText.setText("Name: " + clubName);
        adminText.setText("Admin: " + name);
        descriptionText.setText("Description: " + description);
        request();
        cancel();
    }

    public void request(){
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int userID = user.getInt(0);
                if(myDb.addUserToGroup(userID, groupID, "Requested")){
                    Toast.makeText(JoinClub.this, "Successfully Sent Request", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent(JoinClub.this, ClubMenu.class);
                    startActivity(returnIntent);
                } else {
                    Toast.makeText(JoinClub.this, "Failed to Send Request", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(JoinClub.this, SearchJoinClub.class);
                startActivity(cancelIntent);
            }
        });
    }
}
