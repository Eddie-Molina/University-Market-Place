package com.example.morri.messingaround;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import java.util.ArrayList;
import android.content.Intent;

public class ClubInterface extends AppCompatActivity {

    DatabaseHelper myDb;
    ArrayList<String> clubList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Club Management");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_interface);

        // Link java and xml together
        myDb = new DatabaseHelper(this);
        Button backButton = (Button) findViewById(R.id.backButton);
        ListView lv = (ListView) findViewById(R.id.lv);

        clubList = new ArrayList<String>();
        //clubList.add("Groups");
        Cursor user = myDb.getCurrentUser();
        user.moveToNext();
        int userID = user.getInt(0);
        Cursor groups = myDb.getUserGroups(userID);
        while(groups.moveToNext()){
            String role = groups.getString(1);
            if(role.equals("Admin") || role.equals("Member")){
                int groupID = groups.getInt(0);
                String groupIDstr = Integer.toString(groupID);
                Cursor group = myDb.generalSearch("groups", "ID = ?", groupIDstr);
                group.moveToNext();
                String name = group.getString(1);
                if(name != null){
                    clubList.add(name);
                }
            }
        }
        adapter = new ArrayAdapter<String>(ClubInterface.this, android.R.layout.simple_list_item_1, clubList);
        lv.setAdapter(adapter);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ClubInterface.this, ClubMenu.class);
                startActivity(intent);
            }
        });

        // Click item on List View
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ClubInterface.this, ClubActivity.class);
                String clubName = clubList.get(position);
                //startActivity(i);

                Cursor clubs = myDb.generalSearch("groups", "Name = ?", clubName);
                if(clubs.getCount()==0){
                    return;
                }
                i.putExtra("clubName", clubName);
                startActivity(i);
                /*while(clubs.moveToNext())
                {
                    if(clubName.equals( clubs.getString(1)) )
                    {
                        int clubID = clubs.getInt(0);
                        // second activity iterate through database using ID
                        //Intent intent = new Intent(ClubInterface.this, ClubActivity.class);
                        i.putExtra("Club ID", clubID);
                        startActivity(i);
                    }

                }*/
            }
        });
    }
}
