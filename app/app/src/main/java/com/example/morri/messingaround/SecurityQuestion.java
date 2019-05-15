package com.example.morri.messingaround;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SecurityQuestion extends AppCompatActivity {
    DatabaseHelper myDb;
    Spinner securityQuestionSpinner;
    EditText answerText;
    Button submitButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_question);
        myDb = new DatabaseHelper(this);
        securityQuestionSpinner = (Spinner) findViewById(R.id.securityQuestion);
        answerText = (EditText) findViewById(R.id.answerText);
        submitButton = (Button) findViewById(R.id.submitButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        String username = getIntent().getStringExtra("user");
        Cursor users = myDb.getAllUserData();
        while(users.moveToNext()){
            if(username.equals(users.getString(6))){
                String question1 = users.getString(8);
                String question2 = users.getString(10);
                List<String> list = new ArrayList<String>();
                list.add("Select a security question");
                list.add(question1);
                list.add(question2);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                securityQuestionSpinner.setAdapter(dataAdapter);
                break;
            }
        }
        /*if(curUser.getCount() == 0){
            Toast.makeText(SecurityQuestion.this, "Could not retrieve security questions", Toast.LENGTH_SHORT).show();
        } else {
            String question1 = curUser.getString(8);
            String question2 = curUser.getString(10);
            List<String> list = new ArrayList<String>();
            list.add("Select a security question");
            list.add(question1);
            list.add(question2);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            securityQuestionSpinner.setAdapter(dataAdapter);
        }*/
        submit();
        cancel();
    }

    public void submit(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getIntent().getStringExtra("user");
                Cursor users = myDb.getAllUserData();
                while(users.moveToNext()){
                    if(username.equals(users.getString(6))){
                        String question = securityQuestionSpinner.getSelectedItem().toString();
                        String answer = answerText.getText().toString();
                        String question1 = users.getString(8);
                        String answer1 = users.getString(9);
                        String question2 = users.getString(10);
                        String answer2 = users.getString(11);
                        if((question.equals(question1) && answer.equals(answer1)) || (question.equals(question2) && answer.equals(answer2))){
                            Intent resetIntent = new Intent(SecurityQuestion.this, ResetPassword.class);
                            String studentID = users.getString(5);
                            String tempPass = studentID.substring(0, 4);
                            if(myDb.updatePassword(username, tempPass)){
                                Toast.makeText(SecurityQuestion.this, "Temporary Password Assigned", Toast.LENGTH_SHORT).show();
                                startActivity(resetIntent);
                            } else {
                                Toast.makeText(SecurityQuestion.this, "Failed to assign temporary password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        break;
                    }
                }
            }
        });
    }

    public void cancel(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SecurityQuestion.this, Login.class);
                startActivity(loginIntent);
            }
        });
    }
}
