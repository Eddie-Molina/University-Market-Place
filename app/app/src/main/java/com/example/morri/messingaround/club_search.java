package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class club_search extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText searchText;
    Button searchButton;
    Spinner searchSpinner;
    Spinner resultsSpinner;
    Button cancelButton;
    Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myDb = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchSpinner = (Spinner) findViewById(R.id.searchSpinner);
        resultsSpinner = (Spinner) findViewById(R.id.resultsSpinner);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        selectButton = (Button) findViewById(R.id.selectButton);
        cancel();
        search();
        select();
    }
    public void search(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchPhrase = searchText.getText().toString();
                String searchCategory = searchSpinner.getSelectedItem().toString();
                List<String> initial = new ArrayList<String>();
                ArrayAdapter<String> initialAdapter = new ArrayAdapter<String>(club_search.this, android.R.layout.simple_spinner_item, initial);
                initialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                resultsSpinner.setAdapter(initialAdapter);
                if(searchCategory.equals("Members")){
                    Cursor users = myDb.searchUsers(searchPhrase);
                    if(users.getCount() == 0){
                        myDb.showMessage("No Results Found", "Could not find any users that matched the search term.", club_search.this);
                        return;
                    }
                    List<String> list = new ArrayList<String>();
                    list.add("User Results");
                    while(users.moveToNext()){
                        String fname = users.getString(1);
                        String lname = users.getString(2);
                        String username = users.getString(6);
                        String name = fname + " " + lname + ", " + username;
                        list.add(name);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(club_search.this, android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    resultsSpinner.setAdapter(dataAdapter);



                } else if(searchCategory.equals("Groups")){
                    Cursor groups = myDb.searchGroups(searchPhrase);
                    if(groups.getCount() == 0){
                        myDb.showMessage("No Results Found", "Could not find any groups that matched the search term.", club_search.this);
                        return;
                    }
                    List<String> list = new ArrayList<String>();
                    list.add("Group Results");
                    while(groups.moveToNext()) {
                        int id = groups.getInt(0);
                        String idStr = Integer.toString(id);
                        String name = groups.getString(1);
                        String entry = name + ", ID: " + idStr;
                        list.add(entry);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(club_search.this, android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    resultsSpinner.setAdapter(dataAdapter);
                } else if(searchCategory.equals("Select a Search Category")){
                    Toast.makeText(club_search.this, "Please Select a Search Category", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void select(){
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = resultsSpinner.getSelectedItem().toString();
                String category = searchSpinner.getSelectedItem().toString();
                if(category.equals("Users")){
                    Cursor users = myDb.getAllUserData();
                    while(users.moveToNext()){
                        String fname = users.getString(1);
                        String lname = users.getString(2);
                        String username = users.getString(6);
                        String match = fname + " " + lname + ", " + username;
                        if(selected.equals(match)){
                            int id = users.getInt(0);
                            String name = fname + " " + lname;
                            String email = users.getString(3);
                            String phone = users.getString(4);
                            Intent userIntent = new Intent(club_search.this, UserResults.class);//change UserActivity to whatever is needed
                            userIntent.putExtra("id", id);
                            userIntent.putExtra("name", name);
                            userIntent.putExtra("email", email);
                            userIntent.putExtra("phone", phone);
                            startActivity(userIntent);
                        }
                    }
                } else if(category.equals("Items/Services")){
                    Cursor items = myDb.getAllItemData();
                    while(items.moveToNext()){
                        int id = items.getInt(0);
                        String idStr = Integer.toString(id);
                        String name = items.getString(1);
                        String description = items.getString(2);
                        double price = items.getDouble(5);
                        String priceStr = Double.toString(price);
                        String match = name + ", " + description + ", Price: " + priceStr + ", ID: " + idStr;
                        if(selected.equals(match)){
                            String listDate = items.getString(3);
                            int quantity = items.getInt(4);
                            int sellerID = items.getInt(6);
                            String sellerIDstr = Integer.toString(sellerID);
                            Cursor seller = myDb.generalSearch("users", "ID = ?", sellerIDstr);
                            if(seller.getCount() == 0){
                                return;
                            }
                            seller.moveToNext();
                            String sellerFname = seller.getString(1);
                            String sellerLname = seller.getString(2);
                            String sellerName = sellerFname + " " + sellerLname;
                            Intent itemIntent = new Intent(club_search.this, ItemResults.class);//change ItemActivity to whatever is needed
                            itemIntent.putExtra("id", id);
                            itemIntent.putExtra("name", name);
                            itemIntent.putExtra("description", description);
                            itemIntent.putExtra("listDate", listDate);
                            itemIntent.putExtra("quantity", quantity);
                            itemIntent.putExtra("price", price);
                            itemIntent.putExtra("sellerName", sellerName);
                            startActivity(itemIntent);
                        }
                    }
                } else if(category.equals("Groups")){
                    Cursor groups = myDb.getAllGroupData();
                    while(groups.moveToNext()){
                        int id = groups.getInt(0);
                        String idStr = Integer.toString(id);
                        String name = groups.getString(1);
                        String match = name + ", ID: " + idStr;
                        if(selected.equals(match)){
                            String createdDate = groups.getString(2);
                            int adminID = groups.getInt(3);
                            String adminIDstr = Integer.toString(adminID);
                            Cursor admin = myDb.generalSearch("users", "ID = ?", adminIDstr);
                            if(admin.getCount() == 0){
                                return;
                            }
                            admin.moveToNext();
                            String adminFname = admin.getString(1);
                            String adminLname = admin.getString(2);
                            String adminName = adminFname + " " + adminLname;
                            Intent groupIntent = new Intent(club_search.this, GroupResults.class);//change GroupActivity to whatever is needed
                            groupIntent.putExtra("id", id);
                            groupIntent.putExtra("name", name);
                            groupIntent.putExtra("createdDate", createdDate);
                            groupIntent.putExtra("adminName", adminName);
                            startActivity(groupIntent);
                        }
                    }
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(club_search.this, Login.class);
                startActivity(mainIntent);
            }
        });
    }
}
