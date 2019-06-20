package shai.maarek.ex6;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class PhoneReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

//        createNotificationChannel(context);
        // If it is to call (outgoing)
        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
            Log.d("A", "onReceive: ");

//            sendNotification(context, "Sending message...", "");

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
                String customMessage = "";
                String destPhoneNumber = "";
                if (sharedPreferences.contains("custom_message")) {
                    customMessage = sharedPreferences.getString("custom_message", "");
                }
                if (sharedPreferences.contains("phone_number")) {
                    destPhoneNumber = sharedPreferences.getString("phone_number", "");
                }
                if ("".equals(destPhoneNumber) || "".equals(customMessage)) {
                    Toast.makeText(context, "SMS Failed to Send, missing phone number or text message", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(context, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                    customMessage = customMessage + " " + intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
//                    Intent in =new Intent(context, PhoneReceiver.class);

                    PendingIntent pendingIntentSend = PendingIntent.getBroadcast(context, 0, new Intent(Constants.SMS_SENT), 0);
                    PendingIntent pendingIntentReceived = PendingIntent.getBroadcast(context, 0, new Intent(Constants.SMS_DELIVERED), 0);

//                    Log.d("+_+_+PhoneReceiver", "onReceive: " +  pendingIntentSend.);

                    Intent intentServiceSendingNotification = new Intent(context, SendingSMSService.class);
                    context.startService(intentServiceSendingNotification);

                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(destPhoneNumber, null, customMessage, pendingIntentSend, pendingIntentReceived);
//
                }

            } catch (Exception e) {
                Toast.makeText(context, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
            }
//            Intent serviceIntent = new Intent(context, IntentServiceSMS.class);

//            context.startService(serviceIntent);

//
//     Intent i = new Intent(context, MainActivity.class);
//            i.putExtras(intent);
//            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
//            i.putExtra(MainActivity.OUTGOING_PHONE_NUMBER, phoneNumber);
//            context.startActivity(i);
        }

        //        if (extras != null) {
//            String state = extras.getString(TelephonyManager.EXTRA_STATE);
//            Log.w("MY_DEBUG_TAG", state);
//            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                String phoneNumber = extras
//                        .getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
//                Log.w("MY_DEBUG_TAG", phoneNumber);
//            }
//        }
    }



}
