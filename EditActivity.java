package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class EditActivity extends AppCompatActivity {

    EditText rename;
    int clubID;
    DatabaseHelper myDb;
    Button delete;
    Button save;
	EditText descriptionText;
	Button inviteUserButton;
    Button viewRequestButton;
    Button removeUserButton;
    Button backButton;
    String strClubID;
	 String clubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        myDb = new DatabaseHelper(this);

        setTitle("Edit");

        rename = (EditText) findViewById(R.id.etRename);
        String getRename = rename.getText().toString();
        descriptionText = (EditText) findViewById(R.id.etDescription);
        clubName = getIntent().getStringExtra("clubName");
        save = (Button) findViewById(R.id.bSave);
        delete = (Button) findViewById(R.id.bDelete);
        inviteUserButton = (Button) findViewById(R.id.inviteUserButton);
        viewRequestButton = (Button) findViewById(R.id.viewRequestsButton);
        removeUserButton = (Button) findViewById(R.id.removeUserButton);
        backButton = (Button) findViewById(R.id.backButton);
        inviteUser();
        viewRequest();
        removeUser();
        back();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deleteIntent = new Intent(EditActivity.this, ConfirmDelete.class);
                deleteIntent.putExtra("clubName", clubName);
                startActivity(deleteIntent);
            }
        });
        save();

    }
    public void save(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = rename.getText().toString();
                String description = descriptionText.getText().toString();
                if(newName.equals("") && description.equals("")){
                    return;
                }
                Cursor group = myDb.generalSearch("groups","Name = ?", clubName);
                if(group.getCount()==0){
                    return;
                }
                group.moveToNext();
                int groupID = group.getInt(0);
                String groupIDstr = Integer.toString(groupID);
                int adminID = group.getInt(3);
                String strAdminID = Integer.toString(adminID);
                if(myDb.updateGroup(groupIDstr, newName, strAdminID, description)){
                    Toast.makeText(EditActivity.this, "Group Updated", Toast.LENGTH_SHORT).show();
                    Intent clubIntent = new Intent(EditActivity.this, ClubActivity.class);
                    if(newName.equals("")){
                        clubIntent.putExtra("clubName", clubName);
                    } else {
                        clubIntent.putExtra("clubName", newName);
                    }
                    startActivity(clubIntent);
                } else {
                    Toast.makeText(EditActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void inviteUser(){
        inviteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent inviteIntent = new Intent(EditActivity.this, InviteUser.class);
                inviteIntent.putExtra("clubName", clubName);
                startActivity(inviteIntent);
            }
        });
    }

    public void viewRequest(){
        viewRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent requestIntent = new Intent(EditActivity.this, ViewMemberRequest.class);
                requestIntent.putExtra("clubName", clubName);
                startActivity(requestIntent);
            }
        });
    }

    public void removeUser(){
        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent removeIntent = new Intent(EditActivity.this, RemoveMember.class);
                removeIntent.putExtra("clubName", clubName);
                startActivity(removeIntent);
            }
        });
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent backIntent = new Intent(EditActivity.this, ClubActivity.class);
                backIntent.putExtra("clubName", clubName);
                startActivity(backIntent);
            }
        });
    }
}
