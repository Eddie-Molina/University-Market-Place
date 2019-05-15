package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HandleInvite extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView descriptionText;
    TextView adminText;
    Button acceptButton;
    Button declineButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_invite);
        setTitle("Club Invites");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        adminText = (TextView) findViewById(R.id.adminText);
        acceptButton = (Button) findViewById(R.id.removeButton);
        declineButton = (Button) findViewById(R.id.declineButton);
        backButton = (Button) findViewById(R.id.backButton);
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
        accept();
        decline();
        back();
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(HandleInvite.this, ClubInvites.class);
                startActivity(backIntent);
            }
        });
    }

    public void accept(){
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int userID = user.getInt(0);
                String userIDstr = Integer.toString(userID);
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                String groupIDstr = Integer.toString(groupID);
                String role = "Member";
                if(myDb.updateIncludes(userIDstr, groupIDstr, role)){
                    Toast.makeText(HandleInvite.this, "Successfully Joined Club", Toast.LENGTH_SHORT).show();
                    Intent acceptIntent = new Intent(HandleInvite.this, ClubMenu.class);
                    startActivity(acceptIntent);
                } else {
                    Toast.makeText(HandleInvite.this, "Failed to Join Club", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void decline(){
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int userID = user.getInt(0);
                String userIDstr = Integer.toString(userID);
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                String groupIDstr = Integer.toString(groupID);
                int res = myDb.removeUserFromGroup(userIDstr, groupIDstr);
                if(res > 0){
                    Toast.makeText(HandleInvite.this, "Invitation Declined", Toast.LENGTH_SHORT).show();
                    Intent declineIntent = new Intent(HandleInvite.this, ClubMenu.class);
                    startActivity(declineIntent);
                } else {
                    Toast.makeText(HandleInvite.this, "Failed to Decline Invitation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
