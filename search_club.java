package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class search_club extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText searchText;
    Button searchButton;
    Button cancelButton;
    Spinner searchSpinner;
    ListView resultsView;
    ArrayList<String> list;
    String clubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_club);
        setTitle("Search Members");
        myDb = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchSpinner = (Spinner) findViewById(R.id.searchSpinner);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        resultsView = (ListView) findViewById(R.id.resultsView);
        list = new ArrayList<String>();
        clubName = getIntent().getStringExtra("clubName");
        cancel();
        search();
        Cursor user = myDb.getCurrentUser();
        user.moveToNext();
        int userID = user.getInt(0);
        String clubName = getIntent().getStringExtra("clubName");
        Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
        group.moveToNext();
        int groupID = group.getInt(0);
        Cursor membership = myDb.getGroupMemberShip(groupID, userID);
        if(membership.getCount() == 0){
            return;
        }
        while(membership.moveToNext()){
            String fname = membership.getString(0);
            String lname = membership.getString(1);
            String username = membership.getString(2);
            String name = fname + " " + lname + ", " + username;
            list.add(name);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(search_club.this, android.R.layout.simple_list_item_1, list);
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
                        Intent groupIntent = new Intent(search_club.this, MemberResults.class);
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

    public void search(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                ArrayList<String> initial = new ArrayList<String>();
                ArrayAdapter<String> initialAdapter = new ArrayAdapter<String>(search_club.this, android.R.layout.simple_list_item_1, initial);
                resultsView.setAdapter(initialAdapter);
                String searchPhrase = searchText.getText().toString();
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int userID = user.getInt(0);
                String clubName = getIntent().getStringExtra("clubName");
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                Cursor members = myDb.searchMembership(searchPhrase, groupID, userID);
                if(members.getCount() == 0){
                    myDb.showMessage("No Results Found", "Could not find any results that matched the search term.", search_club.this);
                    return;
                }
                while(members.moveToNext()){
                    String fname = members.getString(0);
                    String lname = members.getString(1);
                    String username = members.getString(2);
                    String name = fname + " " + lname + ", " + username;
                    list.add(name);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(search_club.this, android.R.layout.simple_list_item_1, list);
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
                                Intent groupIntent = new Intent(search_club.this, MemberResults.class);
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
    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clubName = getIntent().getStringExtra("clubName");
                Intent mainIntent = new Intent(search_club.this, ClubActivity.class);
                mainIntent.putExtra("clubName", clubName);
                startActivity(mainIntent);
            }
        });
    }
}
