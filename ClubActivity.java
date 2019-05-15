package com.example.morri.messingaround;///logged inot the group from group selection menu

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class ClubActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Button edit;
    Button search_button;//jake
    Button message_button;//jake message
    Button backButton;
    String clubName;
    Button bAnnouncements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        myDb = new DatabaseHelper(this);
        clubName = getIntent().getStringExtra("clubName");

        setTitle(clubName);

        backButton = (Button) findViewById(R.id.back_);
        back();
        search_button = (Button) findViewById(R.id.search_button);//jake

        search_button.setOnClickListener(new View.OnClickListener() {//jake
            @Override
            public void onClick(View v) {//jake
                Intent gotosearch = new Intent(ClubActivity.this, search_club.class);
                gotosearch.putExtra("clubName", clubName);
                startActivity(gotosearch);
            }///jake add this button
        });
        message_button = (Button) findViewById(R.id.message_button_);//jake

        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomessaging = new Intent(ClubActivity.this, sms_mess.class);
                gotomessaging.putExtra("clubName", clubName);
                startActivity(gotomessaging);
            }
        });//jake additons


        edit = (Button) findViewById(R.id.bEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor user = myDb.getCurrentUser();
                user.moveToNext();
                int userID = user.getInt(0);
                Cursor group = myDb.generalSearch("groups", "Name = ?", clubName);
                group.moveToNext();
                int groupID = group.getInt(0);
                Cursor userRole = myDb.getRole(userID, groupID);
                userRole.moveToNext();
                String role = userRole.getString(0);

                if (role.equals("Admin")) {
                    Intent i = new Intent(ClubActivity.this, EditActivity.class);
                    i.putExtra("clubName", clubName);
                    startActivity(i);
                } else {
                    Toast.makeText(ClubActivity.this, "Only Admins Can Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        announce();
        //getAnnouncementInfo();


    }

    public void announce() {
        bAnnouncements = (Button) findViewById(R.id.bAnnounce);
        bAnnouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClubActivity.this, AnnouncementsHub.class);
                i.putExtra("clubName", clubName);
                startActivity(i);
            }
        });


    }

    public void back() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ClubActivity.this, ClubActivity.class);
                startActivity(backIntent);
            }
        });
    }
}
