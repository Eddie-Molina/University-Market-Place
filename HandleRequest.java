package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HandleRequest extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView usernameText;
    TextView emailText;
    TextView phoneText;
    Button acceptButton;
    Button declineButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_request);
        setTitle("Membership Requests");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        usernameText = (TextView) findViewById(R.id.usernameText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        acceptButton = (Button) findViewById(R.id.removeButton);
        declineButton = (Button) findViewById(R.id.declineButton);
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
        accept();
        decline();
        back();
    }

    public void accept(){
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userID = getIntent().getIntExtra("userID", -1);
                String userIDstr = Integer.toString(userID);
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                String groupIDstr = Integer.toString(groupID);
                String role = "Member";
                if(myDb.updateIncludes(userIDstr, groupIDstr, role)){
                    Toast.makeText(HandleRequest.this, "Successfully Added User", Toast.LENGTH_SHORT).show();
                    Intent acceptIntent = new Intent(HandleRequest.this, EditActivity.class);
                    acceptIntent.putExtra("clubName", clubName);
                    startActivity(acceptIntent);
                } else {
                    Toast.makeText(HandleRequest.this, "Failed to Add User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void decline(){
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userID = getIntent().getIntExtra("userID", -1);
                String userIDstr = Integer.toString(userID);
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                String groupIDstr = Integer.toString(groupID);
                int res = myDb.removeUserFromGroup(userIDstr, groupIDstr);
                if(res > 0){
                    Toast.makeText(HandleRequest.this, "Request Declined", Toast.LENGTH_SHORT).show();
                    Intent declineIntent = new Intent(HandleRequest.this, EditActivity.class);
                    declineIntent.putExtra("clubName", clubName);
                    startActivity(declineIntent);
                } else {
                    Toast.makeText(HandleRequest.this, "Failed to Decline Request", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent backIntent = new Intent(HandleRequest.this, ViewMemberRequest.class);
                backIntent.putExtra("clubName", clubName);
                startActivity(backIntent);
            }
        });
    }
}
