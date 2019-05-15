package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchJoinClub extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText searchText;
    Button cancelButton;
    Button searchButton;
    ListView resultsView;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_join_club);
        setTitle("Join Club");
        myDb = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        searchButton = (Button) findViewById(R.id.searchButton);
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
                ArrayList<String> initial = new ArrayList<String>();
                ArrayAdapter<String> initialAdapter = new ArrayAdapter<String>(SearchJoinClub.this, android.R.layout.simple_list_item_1, initial);
                resultsView.setAdapter(initialAdapter);
                list.clear();
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int id = user.getInt(0);
                String idStr = Integer.toString(id);
                Cursor groups = myDb.getJoinClub(searchPhrase, idStr);
                if(groups.getCount() == 0){
                    myDb.showMessage("No Results Found", "Could not find any groups that matched the search term.", SearchJoinClub.this);
                    return;
                }
                while(groups.moveToNext()){
                    String name = groups.getString(0);
                    list.add(name);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SearchJoinClub.this, android.R.layout.simple_list_item_1, list);
                resultsView.setAdapter(dataAdapter);
                resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String choice = list.get(position);
                        Intent groupIntent = new Intent(SearchJoinClub.this, JoinClub.class);
                        groupIntent.putExtra("clubName", choice);
                        startActivity(groupIntent);
                    }
                });
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(SearchJoinClub.this, ClubMenu.class);
                startActivity(cancelIntent);
            }
        });
    }
}
