package shai.maarek.ex6;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendBroadcastReceiver extends BroadcastReceiver {
    private final String DEBUG_TAG = getClass().getSimpleName().toString();
//    private static final String ACTION_SMS_SENT = "SMS_SENT";
    // When the SMS has been sent
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (Constants.SMS_SENT.equals(action)) {

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Intent intentServiceSentNotification = new Intent(context, SentSMSService.class);
                    context.startService(intentServiceSentNotification);
//                    Bundle b = intent.getExtras();
//                    Log.d(DEBUG_TAG, "sendBroadcastReceiver : b is " + b);
//                    if (b != null) {
//                        String value = b.getString("extra_key");
//                        Log.d(DEBUG_TAG, "sendBroadcastReceiver : value is " + value);
//                    }
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                    Toast.makeText(context, "Generic failure", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
//                    Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
//                    Toast.makeText(context, "Null PDU", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
//                    Toast.makeText(context, "Radio off", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

}
