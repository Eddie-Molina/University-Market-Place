package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText usernameText;
    Button submitButton;
    Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        myDb = new DatabaseHelper(this);
        usernameText = (EditText) findViewById(R.id.usernameTextID);
        submitButton = (Button) findViewById(R.id.submitButtonID);
        cancelButton = (Button) findViewById(R.id.cancelButtonID);
        cancel();
        submit();
    }

    public void submit(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                Cursor users = myDb.getAllUserData();
                while(users.moveToNext()){
                    if(username.equals(users.getString(6))){
                        Intent securityQuestionIntent = new Intent(ForgotPassword.this, SecurityQuestion.class);
                        securityQuestionIntent.putExtra("user", username);
                        startActivity(securityQuestionIntent);
                        return;
                    }
                }
                Toast.makeText(ForgotPassword.this, "Username Does Not Exist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(ForgotPassword.this, Login.class);
                startActivity(loginIntent);
            }
        });
    }
}
