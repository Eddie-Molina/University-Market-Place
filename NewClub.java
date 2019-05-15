package com.example.morri.messingaround;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import java.util.ArrayList;
import android.content.Intent;

public class NewClub extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText name;
    EditText description;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_club);

        setTitle("New Club");
        myDb = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.etName);
        description = (EditText) findViewById(R.id.etDescription);
        Button bSave = findViewById(R.id.bSave);
        backButton = (Button) findViewById(R.id.backButton);
        back();
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getName = name.getText().toString();
                String getDescription = description.getText().toString();
                Cursor groups = myDb.generalSearch("Groups", "Name = ?", getName);
                if(groups.getCount() != 0){
                    Toast.makeText(NewClub.this, "Group Already Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int adminID = user.getInt(0);
                if(myDb.insertGroup(getName, adminID, getDescription))
                {
                    Toast.makeText(NewClub.this, "Successfully Added Group",
                            Toast.LENGTH_SHORT).show();
                    Cursor group = myDb.generalSearch("groups", "Name = ?", getName);
                    group.moveToNext();
                    int groupID = group.getInt(0);
                    if(!myDb.addUserToGroup(adminID, groupID, "Admin")){
                        Toast.makeText(NewClub.this, "Failed to Add Admin", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(NewClub.this, "Failed to Add Group",
                            Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(NewClub.this, ClubMenu.class);
                startActivity(i);
            }
        });
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(NewClub.this, ClubMenu.class);
                startActivity(backIntent);
            }
        });
    }
}
