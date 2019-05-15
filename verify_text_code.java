package com.example.morri.messingaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class verify_text_code extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_text_code);
        int secret_number = 9999;
        Button validate = (Button) findViewById(R.id.validate_button_);
        final EditText code_input = (EditText) findViewById(R.id.code_input_);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code_given = code_input.getText().toString();

                if ( code_given == "9999");
                Intent intent = new Intent(verify_text_code.this,ResetPassword.class);
                startActivity(intent);
            }
        });

    }
}
