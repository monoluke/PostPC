package shai.maarek.ex6;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button buttonSendSms = findViewById(R.id.button_send_sms);
        final MainActivity activity = this;
        requestPermissions();

        sharedPreferences = getSharedPreferences(SHARED_PREF, 0); // 0 - for private mode



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

//        Intent intent = getIntent();
//        String outgoingPhoneCallNum = "";
//        if (intent.getExtras() != null){
//            outgoingPhoneCallNum = intent.getStringExtra(OUTGOING_PHONE_NUMBER);
//        }

        if (sharedPreferences.contains("custom_message")){
            custom_message.setText(sharedPreferences.getString("custom_message", ""));
        }
        if(sharedPreferences.contains("phone_number")){
            phone_number.setText(sharedPreferences.getString("phone_number", ""));
        }



//        phone_number.addTextChangedListener(new TextWatcher() {
//
//        @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("A", "beforeTextChanged: ");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("B", "onTextChanged: "  );
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.d("C", "afterTextChanged: ");
//                if (s.length() < 1){
//                    emptyField.setText("Please enter phone number and text message above");
//                }
//                else{
//                    emptyField.setText("");
//                }
//            }
//        });

        // The   request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()


//            hasSmsPermission =
//                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) ==
//                            PackageManager.PERMISSION_GRANTED;
//            if (!hasSmsPermission) {
//                ActivityCompat.requestPermissions(
//                        activity,
//                        new String[]{Manifest.permission.SEND_SMS},
//                        REQUEST_CODE_PERMISSION_SMS);
//            }

    }

    TextWatcher watch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("A", "beforeTextChanged: ");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("B", "onTextChanged: ");
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
            Log.d("C", "afterTextChanged: ");
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


//        if (grantResults[SEND_SMS] == PackageManager.PERMISSION_GRANTED) {
//            sendSms(); // cool
//            Log.d("+_+_+ A", "Permission granted onRequestPermissionsResult: " + requestCode + " +_+_+");
//        }
//        else {
//            // the user has denied our request! =-O
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
//                Log.d("+_+_+ denied", "SEND_SMS onRequestPermissionsResult: +_+_+");
//                final MainActivity activity = this;
//                boolean hasSmsPermission =
//                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) ==
//                                PackageManager.PERMISSION_GRANTED;
//                if (!hasSmsPermission) {
//                    ActivityCompat.requestPermissions(
//                            activity,
//                            new String[]{Manifest.permission.SEND_SMS},
//                            REQUEST_CODE_PERMISSION_SMS);
//                }
                // reached here? means we asked the user for this permission more than once,
                // and they still refuse. This would be a good time to open up a dialog
                // explaining why we need this permission
//            }
//        }
//        if (grantResults[PHONE_STATE] == PackageManager.PERMISSION_GRANTED) {
//            Log.d("+_+_+ A", "Permission granted onRequestPermissionsResult: " + requestCode + " +_+_+");
//        }
//        else{
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
//                Log.d("+_+_+ denied", "READ_PHONE_STATE onRequestPermissionsResult: +_+_+");
//                final MainActivity activity = this;
//                boolean hasReadPhoneStatePermission =
//                        ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) ==
//                                PackageManager.PERMISSION_GRANTED;
//                if (!hasReadPhoneStatePermission) {
//                    ActivityCompat.requestPermissions(
//                            activity,
//                            new String[]{Manifest.permission.READ_PHONE_STATE},
//                            REQUEST_CODE_PERMISSION_SMS);
//                }
                // reached here? means we asked the user for this permission more than once,
                // and they still refuse. This would be a good time to open up a dialog
                // explaining why we need this permission
//            }
//        }
        if (! isAllGranted){
            requestPermissions();
        }
    }
//
//    private void sendSms() {
//        SmsManager.getDefault().sendTextMessage(
//                "+972-5452-334-48",
//                null,
//                "BUHAHHAHAHAH",
//                null,
//                null);
//    }

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
}