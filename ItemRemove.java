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

import java.io.InputStream;
import java.util.ArrayList;

public class ItemRemove extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText searchText;
    Button cancelButton;
    Button searchButton;
    ListView resultsView;
    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_remove);
        setTitle("Remove Item");
        myDb = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        resultsView = (ListView) findViewById(R.id.resultsView);
        list = new ArrayList<String>();
        cancel();
        search();
        Cursor user = myDb.getCurrentUser();
        user.moveToNext();
        int userID = user.getInt(0);
        String userIDstr = Integer.toString(userID);
        Cursor items = myDb.generalSearch("items_services", "Seller = ?", userIDstr);
        if(items.getCount() == 0){
            return;
        }
        while(items.moveToNext()){
            int itemID = items.getInt(0);
            String itemIDstr = Integer.toString(itemID);
            String name = items.getString(1);
            String entry = name + ", ID: " + itemIDstr;
            list.add(entry);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ItemRemove.this, android.R.layout.simple_list_item_1, list);
        resultsView.setAdapter(dataAdapter);
        resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String choice = list.get(position);
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int userID = user.getInt(0);
                String userIDstr = Integer.toString(userID);
                Cursor items = myDb.generalSearch("items_services", "Seller = ?", userIDstr);
                while(items.moveToNext()){
                    int itemID = items.getInt(0);
                    String itemIDstr = Integer.toString(itemID);
                    String name = items.getString(1);
                    String entry = name + ", ID: " + itemIDstr;
                    if(choice.equals(entry)){
                        Intent removeIntent = new Intent(ItemRemove.this, ConfirmItemRemove.class);
                        removeIntent.putExtra("itemID", itemID);
                        startActivity(removeIntent);
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
                String searchPhrase = searchText.getText().toString();
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int userID = user.getInt(0);
                Cursor items = myDb.searchUserItems(userID, searchPhrase);
                if(items.getCount() == 0){
                    myDb.showMessage("No Results Found", "Could not find any results that matched the search term.", ItemRemove.this);
                    return;
                }
                while(items.moveToNext()){
                    int itemID = items.getInt(0);
                    String itemIDstr = Integer.toString(itemID);
                    String name = items.getString(1);
                    String entry = name + ", ID: " + itemIDstr;
                    list.add(entry);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ItemRemove.this, android.R.layout.simple_list_item_1, list);
                resultsView.setAdapter(dataAdapter);
                resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String choice = list.get(position);
                        Cursor user = myDb.getCurrentUser();
                        user.moveToNext();
                        int userID = user.getInt(0);
                        String userIDstr = Integer.toString(userID);
                        Cursor items = myDb.generalSearch("items_services", "Seller = ?", userIDstr);
                        while(items.moveToNext()){
                            int itemID = items.getInt(0);
                            String itemIDstr = Integer.toString(itemID);
                            String name = items.getString(1);
                            String entry = name + ", ID: " + itemIDstr;
                            if(choice.equals(entry)){
                                Intent removeIntent = new Intent(ItemRemove.this, ConfirmItemRemove.class);
                                removeIntent.putExtra("itemID", itemID);
                                startActivity(removeIntent);
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
                Intent cancelIntent = new Intent(ItemRemove.this, TradeMenu.class);
                startActivity(cancelIntent);
            }
        });
    }
}
