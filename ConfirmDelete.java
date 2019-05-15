package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmDelete extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView nameText;
    TextView descriptionText;
    Button deleteButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delete);
        setTitle("Confirm Deletion");
        myDb = new DatabaseHelper(this);
        nameText = (TextView) findViewById(R.id.nameText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        delete();
        cancel();
        String clubName = getIntent().getStringExtra("clubName");
        Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
        group.moveToNext();
        String name = group.getString(1);
        String description = group.getString(4);
        nameText.setText("Name: " + name);
        descriptionText.setText("Description: " + description);
    }

    public void delete(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                if(group.getCount() == 0){
                    return;
                }
                group.moveToNext();
                int groupID = group.getInt(0);
                String groupIDstr = Integer.toString(groupID);
                int res = myDb.deleteGroup(groupIDstr);
                if(res == 0)
                {
                    Toast.makeText(ConfirmDelete.this, "Failed to delete club",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ConfirmDelete.this, "Successfully deleted club",
                            Toast.LENGTH_SHORT).show();
                    int result = myDb.deleteGroupMembership(groupIDstr);
                    if(result > 0 ){
                        Intent deleteIntent = new Intent(ConfirmDelete.this, ClubMenu.class);
                        startActivity(deleteIntent);
                    } else {
                        Toast.makeText(ConfirmDelete.this, "Failed to remove membership", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent cancelIntent = new Intent(ConfirmDelete.this, EditActivity.class);
                cancelIntent.putExtra("clubName", clubName);
                startActivity(cancelIntent);
            }
        });
    }
}
