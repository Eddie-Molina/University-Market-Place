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

public class Search extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText searchText;
    Button searchButton;
    Button cancelButton;
    Spinner searchSpinner;
    ListView resultsView;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Search");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myDb = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        searchSpinner = (Spinner) findViewById(R.id.searchSpinner);
        resultsView = (ListView) findViewById(R.id.resultsView);
        list = new ArrayList<String>();
        cancel();
        search();
    }

    public void search(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchPhrase = searchText.getText().toString();
                String searchCategory = searchSpinner.getSelectedItem().toString();
                ArrayList<String> initial = new ArrayList<String>();
                ArrayAdapter<String> initialAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, initial);
                resultsView.setAdapter(initialAdapter);
                if(searchCategory.equals("Users")){
                    list.clear();
                    Cursor users = myDb.searchUsers(searchPhrase);
                    if(users.getCount() == 0){
                        myDb.showMessage("No Results Found", "Could not find any users that matched the search term.", Search.this);
                        return;
                    }
                    while(users.moveToNext()){
                        String fname = users.getString(1);
                        String lname = users.getString(2);
                        String username = users.getString(6);
                        String name = fname + " " + lname + ", " + username;
                        list.add(name);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, list);
                    resultsView.setAdapter(dataAdapter);
                    resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String choice = list.get(position);
                            Cursor users = myDb.getAllUserData();
                            while(users.moveToNext()){
                                String fname = users.getString(1);
                                String lname = users.getString(2);
                                String username = users.getString(6);
                                String test = fname + " " + lname + ", " + username;
                                if(choice.equals(test)){
                                    int userid = users.getInt(0);
                                    String name = fname + " " + lname;
                                    String email = users.getString(3);
                                    String phone = users.getString(4);
                                    Intent userIntent = new Intent(Search.this, UserResults.class);//change UserActivity to whatever is needed
                                    userIntent.putExtra("id", id);
                                    userIntent.putExtra("name", name);
                                    userIntent.putExtra("email", email);
                                    userIntent.putExtra("phone", phone);
                                    userIntent.putExtra("username", username);
                                    startActivity(userIntent);
                                }
                            }
                        }
                    });
                } else if(searchCategory.equals("Items/Services")){
                    list.clear();
                    Cursor items = myDb.searchItems(searchPhrase);
                    if(items.getCount() == 0){
                        myDb.showMessage("No Results Found", "Could not find any items/services that matched the search term.", Search.this);
                        return;
                    }
                    while(items.moveToNext()){
                        int id = items.getInt(0);
                        String idStr = Integer.toString(id);
                        String name = items.getString(1);
                        String entry = name + ", ID: " + idStr;
                        list.add(entry);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, list);
                    resultsView.setAdapter(dataAdapter);
                    resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String choice = list.get(position);
                            Cursor items = myDb.getAllItemData();
                            while(items.moveToNext()){
                                int itemid = items.getInt(0);
                                String idStr = Integer.toString(itemid);
                                String name = items.getString(1);
                                String test = name + ", ID: " + idStr;
                                if(choice.equals(test)){
                                    String description = items.getString(2);
                                    String listDate = items.getString(3);
                                    int quantity = items.getInt(4);
                                    double price = items.getDouble(5);
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
                                    Intent itemIntent = new Intent(Search.this, ItemResults.class);//change ItemActivity to whatever is needed
                                    itemIntent.putExtra("id", itemid);
                                    itemIntent.putExtra("name", name);
                                    itemIntent.putExtra("description", description);
                                    itemIntent.putExtra("listDate", listDate);
                                    itemIntent.putExtra("quantity", quantity);
                                    itemIntent.putExtra("price", price);
                                    itemIntent.putExtra("sellerName", sellerName);
                                    startActivity(itemIntent);
                                }
                            }
                        }
                    });
                } else if(searchCategory.equals("Groups")){
                    list.clear();
                    Cursor groups = myDb.searchGroups(searchPhrase);
                    if(groups.getCount() == 0){
                        myDb.showMessage("No Results Found", "Could not find any groups that matched the search term.", Search.this);
                        return;
                    }
                    while(groups.moveToNext()) {
                        String name = groups.getString(1);
                        list.add(name);
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1, list);
                    resultsView.setAdapter(dataAdapter);
                    resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String choice = list.get(position);
                            Intent groupIntent = new Intent(Search.this, GroupResults.class);
                            groupIntent.putExtra("clubName", choice);
                            startActivity(groupIntent);
                        }
                    });
                } else if(searchCategory.equals("Select a Search Category")){
                    Toast.makeText(Search.this, "Please Select a Search Category", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(Search.this, Main_menu.class);
                startActivity(mainIntent);
            }
        });
    }
}
