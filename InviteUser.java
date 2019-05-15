package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class InviteUser extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText searchText;
    Button cancelButton;
    Button searchButton;
    ListView resultsView;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);
        setTitle("Invite User");
        myDb = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        resultsView = (ListView) findViewById(R.id.resultsView);
        list = new ArrayList<String>();
        cancel();
        search();
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent cancelIntent = new Intent(InviteUser.this, EditActivity.class);
                cancelIntent.putExtra("clubName", clubName);
                startActivity(cancelIntent);
            }
        });
    }

    public void search(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchPhrase = searchText.getText().toString();
                ArrayList<String> initial = new ArrayList<String>();
                ArrayAdapter<String> initialAdapter = new ArrayAdapter<String>(InviteUser.this, android.R.layout.simple_list_item_1, initial);
                resultsView.setAdapter(initialAdapter);
                list.clear();
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                Cursor users = myDb.getInvitableUsers(searchPhrase, groupID);
                if(users.getCount() == 0){
                    myDb.showMessage("No Results Found", "Could not find any users that matched the search term.", InviteUser.this);
                    return;
                }
                while(users.moveToNext()){
                    String fname = users.getString(0);
                    String lname = users.getString(1);
                    String username = users.getString(2);
                    String name = fname + " " + lname + ", " + username;
                    list.add(name);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(InviteUser.this, android.R.layout.simple_list_item_1, list);
                resultsView.setAdapter(dataAdapter);
                resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String choice = list.get(position);
                        Cursor allUsers = myDb.getAllUserData();
                        while(allUsers.moveToNext()){
                            String fname = allUsers.getString(1);
                            String lname = allUsers.getString(2);
                            String username = allUsers.getString(6);
                            String name = fname + " " + lname + ", " + username;
                            if(choice.equals(name)){
                                int userID = allUsers.getInt(0);
                                Intent groupIntent = new Intent(InviteUser.this, SendInvite.class);
                                String clubName = getIntent().getStringExtra("clubName");
                                groupIntent.putExtra("clubName", clubName);
                                groupIntent.putExtra("userID", userID);
                                startActivity(groupIntent);
                                return;
                            }
                        }

                    }
                });
            }
        });
    }
}
