package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main_menu extends AppCompatActivity {
    DatabaseHelper myDb;
    Button searchButton;
    Button email_interface;
    Button sms_message;
    Button trade_interface_button;
    Button club_management_button;
  //  public Main_menu(DatabaseHelper myDb) {
      //  this.myDb = myDb;
  //  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        myDb = new DatabaseHelper(this);
        searchButton = (Button) findViewById(R.id.searchButton);
        email_interface = (Button) findViewById(R.id.email_button);
        sms_message = (Button) findViewById(R.id.this_button);//sms message button
        trade_interface_button = (Button) findViewById(R.id.trade_);
        club_management_button = (Button) findViewById(R.id.club_interface_);


            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        }

        sms_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//call sms messaging
                Intent mess_ = new Intent(Main_menu.this, sms_mess.class);
                startActivity(mess_);

            }
        });

        email_interface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email_ = new Intent(Main_menu.this, email_Interface.class);
                startActivity(email_);
            }
        });
        trade_interface_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trade_activity = new Intent(Main_menu.this, trade_interface.class);
                startActivity(trade_activity);
            }
        });


        club_management_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent club_manag_ = new Intent(Main_menu.this, ClubInterface.class);
                startActivity(club_manag_);
              }
          });


                String login = getIntent().getStringExtra("user");
        Cursor curUser = myDb.generalSearch("users", "Username = ?", login);
        curUser.moveToNext();

        /*
        * Use curUser.getString(INDEX) where index matches one of the below numbers to retrieve strings.
        * Index Values in order for curUser
        * 0 -> ID
        * 1 -> Fname (First name)
        * 2 -> Lname (Last name)
        * 3 -> Email
        * 4 -> Phone Number
        * 5 -> Student ID
        * 6 -> Username
        * 7 -> Password
        * */
        //the below query is handled very poorly, it will get changed later
        String myQuery = "SELECT * FROM GROUPS INNER JOIN INCLUDES WHERE UserID = ?";
        String argument[] = {curUser.getString(0)};
        Cursor userGroups = myDb.rawQuery(myQuery, argument);
        if(userGroups.getCount() == 0){
            myDb.showMessage("Error", "Could Not Find User Groups", Main_menu.this);
            //return;
        }
        //probable index values of the cursor, haven't verified it yet though
        /*
        * 0 -> ID
        * 1 -> Name
        * 2 -> CreatedDate
        * 3 -> Admin
        * 4 -> Role (User's role in the group)
        * */
        search();
    }

    public void search(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(Main_menu.this, Search.class);
                startActivity(searchIntent);
            }
        });
    }
}
