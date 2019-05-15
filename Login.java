package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DatabaseHelper myDb = new DatabaseHelper(this);
    EditText userText;
    EditText passText;
    Button loginButton;
    Button cancelButton;
    Button registerButton;
    Button forgotPassButton;
    sms_mess messageactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myDb = new DatabaseHelper(this);
        userText = (EditText) findViewById(R.id.username_text);
        passText = (EditText) findViewById(R.id.password_text);
        loginButton = (Button) findViewById(R.id.login_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        registerButton = (Button) findViewById(R.id.button_register);
        forgotPassButton = (Button) findViewById(R.id.button_forgot_pass);

        login();
        cancel();
        register();
        forgotPass();
    }

    public void login() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userText.getText().toString();
                String password = passText.getText().toString();
                Cursor res = myDb.getUserLogin();
                if(res.getCount() == 0){
                    return;
                } else {
                    String curUsername;
                    String curPassword;
                    while(res.moveToNext()){
                        curUsername = res.getString(0);
                        curPassword = res.getString(1);
                        if(username.equals(curUsername) && password.equals(curPassword)){
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                            int reset = myDb.resetCurrentUser();
                            Cursor user = myDb.generalSearch("users", "Username = ?", username);
                            if(user.getCount() == 0){
                                Toast.makeText(Login.this, "Failed to access user", Toast.LENGTH_SHORT).show();
                                return;
                            } else if(user.getCount() > 1){
                                Toast.makeText(Login.this, "Error: Duplicate Users", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            user.moveToNext();
                            int id = user.getInt(0);
                            String fname = user.getString(1);
                            String lname = user.getString(2);
                            String email = user.getString(3);
                            String phoneNo = user.getString(4);
                            String studentID = user.getString(5);
                            String userPass = user.getString(7);
                            if(myDb.setCurrentUser(id, fname, lname, email, phoneNo, studentID, username, userPass)){
                                Intent mainIntent = new Intent(Login.this, Main_menu.class);
                                mainIntent.putExtra("user", username);
                                startActivity(mainIntent);
                                return;
                            } else {
                                Toast.makeText(Login.this, "Failed to set current user", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    Toast.makeText(Login.this, "Incorrect Username-Password Combination", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cancel() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openingIntent = new Intent(Login.this, Opening_screen.class);
                startActivity(openingIntent);
            }
        });
    }

    public void register(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });
    }

    public void forgotPass(){
        forgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotIntent = new Intent(Login.this, ForgotPassword.class);
                startActivity(forgotIntent);
            }
        });
    }
}
