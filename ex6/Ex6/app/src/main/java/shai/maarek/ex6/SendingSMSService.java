package shai.maarek.ex6;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class SendingSMSService extends IntentService {

    public SendingSMSService(){
        super("SendingSMSService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification(getApplicationContext(), Constants.NOTIFICATION_TITLE, Constants.NOTIFICATION_CONTENT_SENDING);
    }

    private void sendNotification(Context context, String textTitle, String textContent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.NOTIFY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
    }
}
