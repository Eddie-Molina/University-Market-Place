package com.example.morri.messingaround;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText phoneText;
    EditText studentIDText;
    EditText usernameText;
    EditText passwordText;
    EditText confirmpassText;
    Spinner question1spinner;
    Spinner question2spinner;
    EditText answer1Text;
    EditText answer2Text;
    Button registerButton;
    Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);
        firstNameText = (EditText) findViewById(R.id.firstname_text);
        lastNameText = (EditText) findViewById(R.id.lastname_text);
        emailText = (EditText) findViewById(R.id.email_text);
        phoneText = (EditText) findViewById(R.id.phone_text);
        studentIDText = (EditText) findViewById(R.id.studentno_text);
        usernameText = (EditText) findViewById(R.id.username_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        confirmpassText = (EditText) findViewById(R.id.confirmpass_text);
        question1spinner = (Spinner) findViewById(R.id.question1_spinner);
        question2spinner = (Spinner) findViewById(R.id.question2_spinner);
        answer1Text = (EditText) findViewById(R.id.answer1);
        answer2Text = (EditText) findViewById(R.id.answer2);
        registerButton = (Button) findViewById(R.id.register_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancel();
        registerUser();

    }
    public void registerUser(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validFirstName = false;
                boolean validLastName = false;
                boolean validEmail = false;
                boolean validPhoneNo = false;
                boolean validStudentID = false;
                boolean validUsername = false;
                boolean validPassword = false;
                boolean passwordMatch = false;
                boolean validQuestion1 = false;
                boolean validQuestion2 = false;
                boolean validAnswer1 = false;
                boolean validAnswer2 = false;
                String firstName = firstNameText.getText().toString();
                if(!firstName.equals("") && firstName.matches("[a-zA-Z]+")){
                    validFirstName = true;
                } else {
                    Toast.makeText(Register.this, " First Name Should Only Contain Letters", Toast.LENGTH_SHORT).show();
                    return;
                }
                String lastName = lastNameText.getText().toString();
                if(!lastName.equals("") && lastName.matches("[a-zA-Z]+")){
                    validLastName = true;
                } else {
                    Toast.makeText(Register.this, "Last Name Should Only Contain Letters", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = emailText.getText().toString();
                if(!email.equals("") && email.matches(".+@mavs\\.uta\\.edu")){
                    Cursor users = myDb.getAllUserData();
                    boolean emailFound = false;
                    while(users.moveToNext()){
                        if(email.equals(users.getString(3))){
                            emailFound = true;
                            break;
                        }
                    }
                    if(!emailFound){
                        validEmail = true;
                    } else {
                        Toast.makeText(Register.this, "Email Already Used by Another Account", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(Register.this, "Email Should Be a Valid UTA Email Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phoneNo = phoneText.getText().toString();
                if(!phoneNo.equals("") && phoneNo.matches("\\d{10}|\\d{3}-\\d{3}-\\d{4}")){
                    validPhoneNo = true;
                } else {
                    Toast.makeText(Register.this, "Phone number should match the form ########## or ###-###-####",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String studentID = studentIDText.getText().toString();
                if(!studentID.equals("") && studentID.matches("\\d{10}")){
                    validStudentID = true;
                } else {
                    Toast.makeText(Register.this, "Student ID should be a valid UTA student ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = usernameText.getText().toString();
                if(!username.equals("")){
                    Cursor users = myDb.getAllUserData();
                    boolean usernameFound = false;
                    while(users.moveToNext()){
                        if(username.equals(users.getString(6))){
                            usernameFound = true;
                            break;
                        }
                    }
                    if(!usernameFound){
                        validUsername = true;
                    } else {
                        Toast.makeText(Register.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(Register.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password = passwordText.getText().toString();
                if(!password.equals("") && validatePassword(password)){
                    validPassword = true;
                } else {
                    Toast.makeText(Register.this, "Password should contain between 8 and 25 characters, at least one " +
                            "capital letter, at least one lower case letter, at least one special character, " +
                            "at least one number, and no spaces", Toast.LENGTH_LONG).show();
                    return;
                }
                String confirmPass = confirmpassText.getText().toString();
                if(password.equals(confirmPass)){
                    passwordMatch = true;
                } else {
                    Toast.makeText(Register.this, "The entered passwords should match", Toast.LENGTH_SHORT).show();
                    return;
                }
                String question1 = question1spinner.getSelectedItem().toString();
                if(!question1.equals("Select 1st Security Question")){
                    validQuestion1 = true;
                } else {
                    Toast.makeText(Register.this, "Select your first security question", Toast.LENGTH_SHORT).show();
                    return;
                }
                String answer1 = answer1Text.getText().toString();
                if(!answer1.equals("")){
                    validAnswer1 = true;
                } else {
                    Toast.makeText(Register.this, "Enter an answer for security question 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                String question2 = question2spinner.getSelectedItem().toString();
                if(!question2.equals("Select 2nd Security Question") && !question2.equals(question1)){
                    validQuestion2 = true;
                } else {
                    if(question2.equals(question1)){
                        Toast.makeText(Register.this, "The second security question should be different from the first",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(Register.this, "Select your second security question", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                String answer2 = answer2Text.getText().toString();
                if(!answer2.equals("")){
                    validAnswer2 = true;
                } else{
                    Toast.makeText(Register.this, "Enter an answer for security question 2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(validFirstName && validLastName && validEmail && validPhoneNo && validStudentID && validUsername && validPassword
                        && passwordMatch && validQuestion1 && validAnswer1 && validQuestion2 && validAnswer2){
                    boolean isInserted = myDb.insertUser(firstName, lastName, email, phoneNo, studentID, username, password,
                            question1, answer1, question2, answer2);
                    if(isInserted){
                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(Register.this, Login.class);
                        startActivity(loginIntent);
                    } else{
                        Toast.makeText(Register.this, "Failed to Register", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openingIntent = new Intent(Register.this, Opening_screen.class);
                startActivity(openingIntent);
            }
        });
    }

    public boolean validatePassword(String password){
        if(password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#\\$%\\^&\\*\\+\\=])(?=.*[0-9])(?=\\S+$).{8,25}$")){
            return true;
        } else {
            return false;
        }
    }
}
