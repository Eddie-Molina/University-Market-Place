package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class AnnouncementsHub extends AppCompatActivity {

    Button bNA; // new announcement button
    ListView lv;
    DatabaseHelper myDb;
    ArrayList<String> announceList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_hub);

        setTitle("Announcements");

        myDb = new DatabaseHelper(this);

        lv = (ListView) findViewById(R.id.lv);

        announceList = new ArrayList<String>();
        Cursor announcements = myDb.getAnnouncements();
        while(announcements.moveToNext()){
            int ID = announcements.getInt(0);
            String IDstr = Integer.toString(ID);
            String subject = announcements.getString(3); // subject of announcements
            String nametag = subject + ", " + IDstr;
            if(subject != null){
                announceList.add(nametag);
            }
        }
        adapter = new ArrayAdapter<String>(AnnouncementsHub.this, android.R.layout.simple_list_item_1, announceList);
        lv.setAdapter(adapter);

        newAnnounceClick();
        announcementClick();

    }

    public void newAnnounceClick()
    {
        bNA = (Button) findViewById(R.id.bNewAn);
        bNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnnouncementsHub.this, AnnouncementsActivity.class);
                String clubName = getIntent().getStringExtra("clubName");
                i.putExtra("clubName", clubName);
                startActivity(i);
            }
        });
    }

    public void announcementClick()
    {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AnnouncementsHub.this, ViewAnnouncement.class);
                //i.putExtra()
                //String aMessage =
                String clubName = getIntent().getStringExtra("clubName");
                i.putExtra("clubName", clubName);
                String announcementName = announceList.get(position);
                Cursor announcement = myDb.getAnnouncements();
                if(announcement.getCount() == 0)
                {
                    return;
                }

                while(announcement.moveToNext())
                {
                    String name = announcement.getString(3);
                    int ID = announcement.getInt(0);
                    String aID = Integer.toString(ID);
                    String nametag = name + ", " + aID;
                    if(announcementName.equals(nametag))
                    {
                        int annnouncementID = announcement.getInt(0);
                        i.putExtra("ID", annnouncementID);
                        startActivity(i);
                    }
                }
            }
        });
    }
}
