package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SendInvite extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView usernameText;
    TextView emailText;
    TextView phoneText;
    Button inviteButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invite);
        setTitle("Invite User");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        usernameText = (TextView) findViewById(R.id.usernameText);
        emailText = (TextView) findViewById(R.id.emailText);
        phoneText = (TextView) findViewById(R.id.phoneText);
        inviteButton = (Button) findViewById(R.id.inviteButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        int userID = getIntent().getIntExtra("userID", -1);
        String userIDstr = Integer.toString(userID);
        Cursor user = myDb.generalSearch("users", "ID = ?", userIDstr);
        user.moveToNext();
        String fname = user.getString(1);
        String lname = user.getString(2);
        String name = fname + " " + lname;
        String username = user.getString(6);
        String email = user.getString(3);
        String phone = user.getString(4);
        nameText.setText("Name: " + name);
        usernameText.setText("Username: " + username);
        emailText.setText("Email: " + email);
        phoneText.setText("Phone: " + phone);
        invite();
        cancel();
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(SendInvite.this, InviteUser.class);
                String clubName = getIntent().getStringExtra("clubName");
                cancelIntent.putExtra("clubName", clubName);
                startActivity(cancelIntent);
            }
        });
    }

    public void invite(){
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userID = getIntent().getIntExtra("userID", -1);
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                if(myDb.addUserToGroup(userID, groupID, "Invited")){
                    Toast.makeText(SendInvite.this, "User Invited", Toast.LENGTH_SHORT).show();
                    Intent inviteIntent = new Intent(SendInvite.this, EditActivity.class);
                    inviteIntent.putExtra("clubName", clubName);
                    startActivity(inviteIntent);
                } else {
                    Toast.makeText(SendInvite.this, "Failed to Invite User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
