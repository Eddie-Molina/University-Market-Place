
package com.example.morri.messingaround;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class sms_mess extends AppCompatActivity {
    EditText str_mess;
    EditText str_num;
    Button send_button;
    IntentFilter intentFilter;
    ArrayList<contact_struct> list = new ArrayList<contact_struct>();
    private DatabaseHelper myDB = null;
    ArrayList<String> messages = new ArrayList<>();
    contact_struct temp = null;
    ListView lv;
    ArrayAdapter<String> adapter;
    private GestureDetectorCompat gestureObject;


    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String foo = intent.getStringExtra("sms");
            Toast.makeText(sms_mess.this, foo, Toast.LENGTH_SHORT).show();
            lv = (ListView) findViewById(R.id.list_view_);
            if(foo != null)
            {
                messages.add(foo);//adds incoming messages to converstation
             }
            Collections.reverse(messages);
            Cursor res = myDB.get_messages();
            while(res.moveToNext()) {
                messages.add(res.getString(1));
            }
             adapter = new ArrayAdapter<String>(sms_mess.this, android.R.layout.simple_list_item_1, messages);
            lv.setAdapter(adapter);

   }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Messenger");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_mess);
        myDB = new DatabaseHelper(this);
        lv = (ListView) findViewById(R.id.list_view_);
        Cursor res = myDB.get_messages();
       if (res.getCount()==0)
       {
           return;
       }
        while(res.moveToNext()) {
            messages.add(res.getString(1));
       }
       Collections.reverse(messages);
        adapter = new ArrayAdapter<String>(sms_mess.this, android.R.layout.simple_list_item_1, messages);
        lv.setAdapter(adapter);

        while(res.moveToNext()){//htis is a list of all contacts
            String fname = res.getString(1);
            fname +=" ";
            fname +=(res.getString(2));
            String number = res.getString(4);
            temp = new contact_struct(fname,number);
            list.add(temp);
            System.out.print(fname);
        }

        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        final int i = 0;
        str_mess = (EditText) findViewById(R.id.the_message);
        str_num = (EditText) findViewById(R.id.number);
        send_button = (Button) findViewById(R.id.send);
        send_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String str_mess_sms = null;
                String str_num_sms= null;

                try {
                    str_mess_sms = str_num.getText().toString();//these are flipped
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "No Number", Toast.LENGTH_SHORT).show();
                }
                try {
                    str_num_sms = str_mess.getText().toString();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "No input", Toast.LENGTH_SHORT).show();

                }
                try {
                    sendmess(str_mess_sms, str_num_sms);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }
    private void sendmess(String str_mess_sms, String str_num_sms) {
                Toast.makeText(sms_mess.this, str_mess_sms,Toast.LENGTH_SHORT).show();
                String SENT = "Message Sent";
                final String DELIVERED = "Message Delivered";

                PendingIntent sentPI = PendingIntent.getBroadcast(sms_mess.this,0,new Intent(SENT),0);
                PendingIntent deliveredPI = PendingIntent.getBroadcast(sms_mess.this,0, new Intent(DELIVERED), 0);
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context arg0, Intent arg1) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(sms_mess.this, "SMS Sent", Toast.LENGTH_LONG).show();
                                break;
                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                Toast.makeText(getBaseContext(), "Generice Failure", Toast.LENGTH_LONG).show();
                                break;
                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                Toast.makeText(getBaseContext(), "No Service", Toast.LENGTH_LONG).show();
                                break;
                        }

                    }

                }, new IntentFilter(SENT));

                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context arg0, Intent arg1)
                    {
                        switch(getResultCode())
                        {
                            case Activity.RESULT_OK:
                                Toast.makeText(getBaseContext(), "SMS Delivered", Toast.LENGTH_LONG).show();
                                break;
                            case Activity.RESULT_CANCELED:
                                Toast.makeText(getBaseContext(),"SMS Not Delivered", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }, new IntentFilter(DELIVERED));

                myDB.put_new_message(str_num_sms,"99999");//put messages in the database
                SmsManager SMS = SmsManager.getDefault();
                SMS.sendTextMessage(str_mess_sms,null,str_num_sms,sentPI,deliveredPI);

            }



    public void send_default_message( String name, String mess_too){
        String number;
        sendmess(name,mess_too);

    }

        protected void onResume(){

            registerReceiver(intentReceiver,intentFilter);
            sms_mess.super.onResume();
        }

        protected void onPause(){

            unregisterReceiver(intentReceiver);
            sms_mess.super.onPause();
        }

}
