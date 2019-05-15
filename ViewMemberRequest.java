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

public class ViewMemberRequest extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView invitesView;
    Button backButton;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member_request);
        setTitle("Membership Requests");
        myDb = new DatabaseHelper(this);
        backButton = (Button) findViewById(R.id.backButton);
        list = new ArrayList<String>();
        invitesView = (ListView) findViewById(R.id.invitesView);
        back();
        String clubName = getIntent().getStringExtra("clubName");
        Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
        group.moveToNext();
        int groupID = group.getInt(0);
        Cursor users = myDb.getJoinRequests(groupID);
        if(users.getCount() == 0){
            return;
        }
        while(users.moveToNext()){
            String fname = users.getString(0);
            String lname = users.getString(1);
            String username = users.getString(2);
            String name = fname + " " + lname + ", " + username;
            list.add(name);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ViewMemberRequest.this, android.R.layout.simple_list_item_1, list);
        invitesView.setAdapter(dataAdapter);
        invitesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choice = list.get(position);
                String clubName = getIntent().getStringExtra("clubName");
                Cursor users = myDb.getAllUserData();
                while(users.moveToNext()){
                    String fname = users.getString(1);
                    String lname = users.getString(2);
                    String username = users.getString(6);
                    String name = fname + " " + lname + ", " + username;
                    if(choice.equals(name)){
                        int userID = users.getInt(0);
                        Intent groupIntent = new Intent(ViewMemberRequest.this, HandleRequest.class);
                        groupIntent.putExtra("userID", userID);
                        groupIntent.putExtra("clubName", clubName);
                        startActivity(groupIntent);
                    }
                }
            }
        });
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent backIntent = new Intent(ViewMemberRequest.this, EditActivity.class);
                backIntent.putExtra("clubName", clubName);
                startActivity(backIntent);
            }
        });
    }
}
