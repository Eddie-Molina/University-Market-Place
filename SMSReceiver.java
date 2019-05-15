package com.example.morri.messingaround;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SMSReceiver extends BroadcastReceiver {
    ArrayList<String> list_;
    ArrayAdapter<String> adapter;
    private DatabaseHelper myDB;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle   = intent.getExtras();
        SmsMessage[] messages = null;
        myDB = new DatabaseHelper(context);

        String str = "";

        if(bundle!=null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");//get the text message into a usable sstring
            messages = new SmsMessage[pdus.length];
            String format = bundle.getString("format");
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                str += "Messages From" + messages[i].getOriginatingAddress();
                str += " :\n";
                str += messages[i].getMessageBody().toString();
                str += "\n";
            }
            myDB.put_new_message(str,"99999");//put messages in the database
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);

        }


    }

}
