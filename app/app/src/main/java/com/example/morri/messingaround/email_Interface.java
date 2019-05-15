package com.example.morri.messingaround;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;



public class email_Interface extends AppCompatActivity {

    EditText et_email, et_subject, et_message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email__interface);

        send = (Button) findViewById(R.id.send_email);
        et_email =(EditText) findViewById(R.id.email_address);
        et_subject =(EditText) findViewById(R.id.subject);
        et_message =(EditText) findViewById(R.id.field);

        send.setOnClickListener(new View.OnClickListener(){
            String to;
            String subject;
            String message;
            Intent intent = null;

            @Override
            public void onClick(View view) {
                try {
                     to = et_email.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    subject = et_subject.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    message = et_message.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                intent = new Intent(intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent, "Select email app"));

            }
        });


    }
}
