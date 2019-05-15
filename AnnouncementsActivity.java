package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class AnnouncementsActivity extends AppCompatActivity {

    EditText subject;
    EditText announcment;
    Button post;
    String strSubject;
    String strAnnounce;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        setTitle("Announcements");

        myDb = new DatabaseHelper(this);

        subject = (EditText) findViewById(R.id.etSubject);
        announcment = (EditText) findViewById(R.id.etAnnounce);
        post = (Button) findViewById(R.id.bPost);

        /*strSubject = subject.getText().toString();
        strAnnounce = announcment.getText().toString();*/

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectStr = subject.getText().toString();
                String messageStr = announcment.getText().toString();
                if(myDb.insertAnnouncement(1, messageStr, subjectStr))
                {
                    Toast.makeText(AnnouncementsActivity.this, "Successfully added Announcement",
                                Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AnnouncementsActivity.this, "Failed to add Announcement",
                            Toast.LENGTH_SHORT).show();
                }
                Intent returni = new Intent(AnnouncementsActivity.this, AnnouncementsHub.class);
                String clubName = getIntent().getStringExtra("clubName");
                returni.putExtra("clubName", clubName);
                startActivity(returni);
            }
        });

    }
}
