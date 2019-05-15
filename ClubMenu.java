package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClubMenu extends AppCompatActivity {
    Button viewClubButton;
    Button newClubButton;
    Button joinClubButton;
    Button viewInvitesButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_menu);
        setTitle("Club Management");
        viewClubButton = (Button) findViewById(R.id.viewClubButton);
        newClubButton = (Button) findViewById(R.id.newClubButton);
        joinClubButton = (Button) findViewById(R.id.joinClubButton);
        viewInvitesButton = (Button) findViewById(R.id.viewInvitesButton);
        backButton = (Button) findViewById(R.id.backButton);
        viewClub();
        newClub();
        joinClub();
        viewInvites();
        back();
    }

    public void viewClub(){
        viewClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewClubIntent = new Intent(ClubMenu.this, ClubInterface.class);
                startActivity(viewClubIntent);
            }
        });
    }

    public void newClub(){
        newClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newClubIntent = new Intent(ClubMenu.this, NewClub.class);
                startActivity(newClubIntent);
            }
        });
    }

    public void joinClub(){
        joinClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent joinClubIntent = new Intent(ClubMenu.this, SearchJoinClub.class);
                startActivity(joinClubIntent);
            }
        });
    }

    public void viewInvites(){
        viewInvitesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent clubInviteIntent = new Intent(ClubMenu.this, ClubInvites.class);
                startActivity(clubInviteIntent);
            }
        });
    }

    public void back(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ClubMenu.this, Main_menu.class);
                startActivity(backIntent);
            }
        });
    }
}
