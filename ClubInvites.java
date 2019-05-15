package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ClubInvites extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView invitesView;
    Button backButton;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_invites);
        setTitle("Club Invites");
        myDb = new DatabaseHelper(this);
        backButton = (Button) findViewById(R.id.backButton);
        list = new ArrayList<String>();
        invitesView = (ListView) findViewById(R.id.invitesView);
        back();
        Cursor user = myDb.getCurrentUser();
        user.moveToNext();
        int userID = user.getInt(0);
        Cursor invites = myDb.getInvites(userID);
        if(invites.getCount() == 0){
            return;
        }
        while(invites.moveToNext()){
            String name = invites.getString(0);
            list.add(name);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ClubInvites.this, android.R.layout.simple_list_item_1, list);
        invitesView.setAdapter(dataAdapter);
        invitesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choice = list.get(position);
                Intent groupIntent = new Intent(ClubInvites.this, HandleInvite.class);
                groupIntent.putExtra("clubName", choice);
                startActivity(groupIntent);
            }
        });
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ClubInvites.this, ClubMenu.class);
                startActivity(backIntent);
            }
        });
    }
}
