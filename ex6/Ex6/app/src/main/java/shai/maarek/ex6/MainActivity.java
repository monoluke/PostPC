package shai.maarek.ex6;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int SEND_SMS = 0;
    private final int PHONE_STATE = 1;
    private final int PROCESS_OUTGOING_CALLS = 2;
    private final int PERMISSION_ALL = 1;
    private EditText custom_message;
    private EditText phone_number;
    private TextView emptyField;
    private static final int REQUEST_CODE_PERMISSION_SMS = 1546;
    private static final int REQUEST_CODE_PERMISSION_PHONE_STATE = 1547;
    private static final int REQUEST_CODE_PERMISSION_PROCESS_OUTGOING_CALLS = 1548;
    private SharedPreferences sharedPreferences;
    public static String OUTGOING_PHONE_NUMBER = "OUTGOING_PHONE_NUMBER";
    public static String SHARED_PREF = "MyPref";
    NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button buttonSendSms = findViewById(R.id.button_send_sms);
        final MainActivity activity = this;
        requestPermissions();

        sharedPreferences = getSharedPreferences(SHARED_PREF, 0); // 0 - for private mode


//        createNotificationChannel();
//        sendNotification("test", "is there anybody out there?");
        TextView title = findViewById(R.id.textViewTitle);
        title.setText("Settings");

        TextView enterPhoneNumber = findViewById(R.id.textView_enter_phone_number);
        enterPhoneNumber.setText("Enter phone number: ");

        TextView predefinedText = findViewById(R.id.textView_predefined_text);
        predefinedText.setText("Set text to be sent: ");

        emptyField = findViewById(R.id.textView_empty_field);
        emptyField.setText("");

        custom_message = findViewById(R.id.editText_custom_message);
        custom_message.addTextChangedListener(watch);
        phone_number = findViewById(R.id.editText_phone_number);
        phone_number.addTextChangedListener(watch);


        SendBroadcastReceiver sendBroadcastReceiver = new SendBroadcastReceiver();
        DeliveryBroadcastReceiver deliveryBroadcastReceiver = new DeliveryBroadcastReceiver();

        registerReceiver(sendBroadcastReceiver, new IntentFilter(Constants.SMS_SENT));
        registerReceiver(deliveryBroadcastReceiver, new IntentFilter(Constants.SMS_DELIVERED));


        if (sharedPreferences.contains("custom_message")){
            custom_message.setText(sharedPreferences.getString("custom_message", ""));
        }
        if(sharedPreferences.contains("phone_number")){
            phone_number.setText(sharedPreferences.getString("phone_number", ""));
        }
        createNotificationChannel(this);

//        updateNotification("update", "coucou");
    }

    TextWatcher watch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            Log.d("A", "beforeTextChanged: ");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Log.d("B", "onTextChanged: ");
            if (phone_number.getText().length() < 1 && custom_message.getText().length() < 1){
                emptyField.setText("Please enter a phone number and text message");
            }
            else {
                if (phone_number.getText().length() < 1){
                    emptyField.setText("Please enter a phone number");
                }
                else if(custom_message.getText().length() < 1){
                    emptyField.setText("Please enter a text message");
                }
                else{
                    emptyField.setText("");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("custom_message", custom_message.getText().toString());
                    editor.putString("phone_number", phone_number.getText().toString());
                    editor.apply();

                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
//            Log.d("C", "afterTextChanged: ");
        }
    };

    public void requestPermissions(){
        String[] PERMISSIONS = {
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.PROCESS_OUTGOING_CALLS,
        };
//
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    public static boolean hasPermissions(Context context, String ... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        // we know we asked for only 1 permission, so we will surely get exactly 1 result
        // (grantResults.size == 1)
        // depending on your use case, if you get only SOME of your permissions
        // (but not all of them), you can act accordingly
        //
        int i = 0;
        boolean isAllGranted = true;
        if (grantResults.length != 0){
            while (isAllGranted && i < grantResults.length){
                if (grantResults[i] == -1){
                    isAllGranted = false;
                }
                i++;
            }
        }
        if (! isAllGranted){
            requestPermissions();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("custom_message", custom_message.getText().toString());
        outState.putString("phone_number", phone_number.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        custom_message.setText(savedInstanceState.getString("custom_message"));
        phone_number.setText(savedInstanceState.getString("phone_number"));
    }

//    private void  createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            CharSequence name = getString(R.string.channel_name);
//            CharSequence name = "ChannelName";
////            String description = getString(R.string.channel_description);
//            String description = "ChannelDescription";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(PhoneReceiver.CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//
//    private void sendNotification(String textTitle, String textContent){
//        int notificationId = 112;
//        this.builder = new NotificationCompat.Builder(this, PhoneReceiver.CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setContentTitle(textTitle)
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(notificationId, builder.build());
//    }
//
//    private void updateNotification(String textTitle, String textContent){
//        this.builder.setContentTitle(textTitle);
//        this.builder.setContentText(textContent);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(112, builder.build());
//    }



    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
            CharSequence name = "ChannelName";
//            String description = getString(R.string.channel_description);
            String description = "ChannelDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFY_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    private void sendNotification(Context context, String textTitle, String textContent) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.NOTIFY_CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
//                .setContentTitle(textTitle)
//                .setContentText(textContent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build());
//    }
}

