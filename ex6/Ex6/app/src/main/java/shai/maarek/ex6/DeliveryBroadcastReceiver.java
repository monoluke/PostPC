package shai.maarek.ex6;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DeliveryBroadcastReceiver extends BroadcastReceiver {
    private final String DEBUG_TAG = getClass().getSimpleName().toString();
//    private static final String ACTION_SMS_DELIVERED = "SMS_DELIVERED";

    // When the SMS has been delivered
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (Constants.SMS_DELIVERED.equals(action)) {

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Intent intentServiceDeliveredNotification = new Intent(context, DeliveredSMSService.class);
                    context.startService(intentServiceDeliveredNotification);
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
    }
}
