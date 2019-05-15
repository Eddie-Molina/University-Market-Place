package com.example.morri.messingaround;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class RemoveItem extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText searchText;
    Button searchButton;
    Spinner resultsSpinner;
    Button cancelButton;
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_item);
        myDb = new DatabaseHelper(this);
        searchText = (EditText) findViewById(R.id.searchText);
        searchButton = (Button) findViewById(R.id.searchButton);
        resultsSpinner = (Spinner) findViewById(R.id.resultsSpinner);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        removeButton = (Button) findViewById(R.id.removeButton);
        search();
        cancel();
        remove();
    }

    public void search(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchPhrase = searchText.getText().toString();
                List<String> initial = new ArrayList<String>();
                ArrayAdapter<String> initialAdapter = new ArrayAdapter<String>(RemoveItem.this, android.R.layout.simple_spinner_item, initial);
                initialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                resultsSpinner.setAdapter(initialAdapter);
                Cursor items = myDb.searchItems(searchPhrase);
                if(items.getCount() == 0){
                    myDb.showMessage("No Results Found", "Could not find any items/services that matched the search term.", RemoveItem.this);
                    return;
                }
                List<String> list = new ArrayList<String>();
                list.add("Item/Service Results");
                while(items.moveToNext()){
                    int id = items.getInt(0);
                    String idStr = Integer.toString(id);
                    String name = items.getString(1);
                    String description = items.getString(2);
                    double price = items.getDouble(5);
                    String priceStr = Double.toString(price);
                    String entry = name + ", " + description + ", Price: " + priceStr + ", ID: " + idStr;
                    list.add(entry);
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(RemoveItem.this, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                resultsSpinner.setAdapter(dataAdapter);
            }
        });
    }

    public void remove(){
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected = resultsSpinner.getSelectedItem().toString();
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
                        int res = myDb.deleteItem(idStr);
                        if(res > 0){
                            Toast.makeText(RemoveItem.this, "Item Successfully Deleted", Toast.LENGTH_SHORT).show();
                            Intent nextIntent = new Intent(RemoveItem.this, Login.class);
                            startActivity(nextIntent);
                        } else {
                            Toast.makeText(RemoveItem.this, "Failed to Delete Item", Toast.LENGTH_SHORT).show();
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
                Intent cancelIntent = new Intent(RemoveItem.this, Login.class);
                startActivity(cancelIntent);
            }
        });
    }
}
