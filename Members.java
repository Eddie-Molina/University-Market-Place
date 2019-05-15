package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.lang.reflect.Member;

public class Members extends AppCompatActivity {

    Button bCreate;
    EditText search;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        setTitle("Members");
        myDb = new DatabaseHelper(this);
        search = (EditText) findViewById(R.id.etSearch);
        bCreate = (Button) findViewById(R.id.bCreate);
        Intent i = new Intent();
        final String clubName = i.getStringExtra("name again");
        // Get database stuff from Connor to perform member search
        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store Club names (ArrayList) into database
                String name = getIntent().getStringExtra("result");
                String description = getIntent().getStringExtra("description");
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int adminID = user.getInt(0);
                if(myDb.insertGroup(name, adminID, description))
                {
                    Toast.makeText(Members.this, "Successfully Added Group",
                                Toast.LENGTH_SHORT).show();
                    Cursor group = myDb.generalSearch("groups", "Name = ?", name);
                    group.moveToNext();
                    int groupID = group.getInt(0);
                    if(!myDb.addUserToGroup(adminID, groupID, "Admin")){
                        Toast.makeText(Members.this, "Failed to Add Admin", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Members.this, "Failed to Add Group",
                                Toast.LENGTH_SHORT).show();
                }

                Intent returnIntent = new Intent(Members.this, ClubInterface.class);
                startActivity(returnIntent);
                finish();
            }
        });
    }
}
