package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmRemove extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView usernameText;
    TextView emailText;
    TextView phoneText;
    Button removeButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_remove);
        setTitle("Confirm Removal");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        usernameText = (TextView) findViewById(R.id.usernameText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        removeButton = (Button) findViewById(R.id.removeButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        int userID = getIntent().getIntExtra("userID", -1);
        String userIDstr = Integer.toString(userID);
        Cursor user = myDb.generalSearch("users", "ID = ?", userIDstr);
        user.moveToNext();
        String fname = user.getString(1);
        String lname = user.getString(2);
        String username = user.getString(6);
        String name = fname + " " + lname + ", " + username;
        String email = user.getString(3);
        String phone = user.getString(4);
        nameText.setText("Name: " + name);
        usernameText.setText("Username: " + username);
        emailText.setText("Email: " + email);
        phoneText.setText("Phone: " + phone);
        remove();
        cancel();
    }

    public void remove(){
        removeButton.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(ConfirmRemove.this, "Member removed", Toast.LENGTH_SHORT).show();
                    Intent declineIntent = new Intent(ConfirmRemove.this, EditActivity.class);
                    declineIntent.putExtra("clubName", clubName);
                    startActivity(declineIntent);
                } else {
                    Toast.makeText(ConfirmRemove.this, "Failed to Remove Member", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent cancelIntent = new Intent(ConfirmRemove.this, RemoveMember.class);
                cancelIntent.putExtra("clubName", clubName);
                startActivity(cancelIntent);
            }
        });
    }
}
