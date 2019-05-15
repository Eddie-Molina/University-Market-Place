package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;

public class ViewAnnouncement extends AppCompatActivity {

    TextView announcement;
    DatabaseHelper myDb;
    int ID;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_announcement);

        setTitle("Announcement");

        myDb = new DatabaseHelper(this);
        announcement = (TextView) findViewById(R.id.tv);

        Intent i = getIntent();
        int ID = i.getIntExtra("ID", -1);
        String idStr = Integer.toString(ID);

        Cursor myAnnouncement = myDb.generalSearch("Announcements", "ID = ?", idStr);
        myAnnouncement.moveToNext();
        String message = myAnnouncement.getString(2); // announcement message

        if( message.equals("") )
        {
            announcement.setText("\"NO ANNOUNCEMENT\nCARRY ON\"");
        }
        else
        {
            announcement.setText("\"" + message + "\"");
        }
        backbutton();
    }

    public void backbutton()
    {
        back = (Button) findViewById(R.id.bBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ret = new Intent(ViewAnnouncement.this, AnnouncementsHub.class);
                String clubName = getIntent().getStringExtra("clubName");
                ret.putExtra("clubName", clubName);
                startActivity(ret);
            }
        });
    }
}
