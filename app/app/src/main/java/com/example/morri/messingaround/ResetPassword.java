package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText usernameText;
    EditText oldPassText;
    EditText newPassText;
    EditText confirmPassText;
    Button cancelButton;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        myDb = new DatabaseHelper(this);
        usernameText = (EditText) findViewById(R.id.usernameText);
        oldPassText = (EditText) findViewById(R.id.oldPassText);
        newPassText = (EditText) findViewById(R.id.newPassText);
        confirmPassText = (EditText) findViewById(R.id.confirmPassText);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        submitButton = (Button) findViewById(R.id.submitButton);
        cancel();
        submit();
    }

    public void submit(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String oldPass = oldPassText.getText().toString();
                String newPass = newPassText.getText().toString();
                String confirmPass = confirmPassText.getText().toString();
                Cursor users = myDb.getAllUserData();
                while(users.moveToNext()){
                    if(username.equals(users.getString(6))){
                        if(oldPass.equals(users.getString(7))){
                            if(newPass.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#\\$%\\^&\\*\\+\\=])(?=.*[0-9])(?=\\S+$).{8,25}$")){
                                if(newPass.equals(confirmPass)){
                                    if(myDb.updatePassword(username, newPass)){
                                        Toast.makeText(ResetPassword.this, "Successfully Changed Password", Toast.LENGTH_SHORT).show();
                                        Intent loginIntent = new Intent(ResetPassword.this, Login.class);
                                        startActivity(loginIntent);
                                    } else {
                                        Toast.makeText(ResetPassword.this, "Failed to Update Password", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } else {
                                    Toast.makeText(ResetPassword.this, "The New Password and Confirm Password Do Not Match",
                                            Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ResetPassword.this, "Password should contain 8-25 characters, at least one capital letter," +
                                " at least one lower case letter, at least one number, at least one symbol, and no whitespace",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(ResetPassword.this, "Old Password Does Not Match Current Password", Toast.LENGTH_SHORT).show();
                            return;
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
                Intent loginIntent = new Intent(ResetPassword.this, Login.class);
                startActivity(loginIntent);
            }
        });
    }
}
