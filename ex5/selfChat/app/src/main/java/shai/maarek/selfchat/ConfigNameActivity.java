package shai.maarek.selfchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigNameActivity extends AppCompatActivity {//implements View.OnClickListener {

    public static final String USERNAME = "username";
    public static final String EMPTY_USERNAME = "";
    private String userName;
    private DatabaseReference mDatabase;
    public static final String uid = "constant_user";
    private EditText editText_enter_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config_name);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.userName = "";


            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(USERNAME)) {
                        userName = (String) dataSnapshot.child(USERNAME).getValue();
                    }

                    if (!Strings.isEmptyOrWhitespace(userName)) {
                        goToMainActivity(userName);
                    }
                    else{


                        TextView textView_hello_user = findViewById(R.id.textView_hello_user);
                        textView_hello_user.setText("Hello, new user!");

                        TextView textView_please_fill = findViewById(R.id.textView_please_fill);
                        textView_please_fill.setText("Please fill up your name:");

                        editText_enter_name = findViewById(R.id.editText_enter_name);

                        Button button_my_name = findViewById(R.id.button_my_name);
                        button_my_name.setText("This is my name");
                        button_my_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String username = editText_enter_name.getText().toString();
                                mDatabase.child("users").child(uid).child(USERNAME).setValue(username);
                                goToMainActivity(username);
                            }
                        });

                        Button button_skip = findViewById(R.id.button_skip);
                        button_skip.setText("Skip");
                        button_skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goToMainActivity(EMPTY_USERNAME);
                            }
                        });




                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button_my_name:
//                String username = editText_enter_name.getText().toString();
//                mDatabase.child("users").child(uid).child(USERNAME).setValue(username);
//                goToMainActivity(username);
//                break;
//
//            case R.id.button_skip:
//                goToMainActivity(EMPTY_USERNAME);
//                break;
//            default:
//                break;
//        }
//    }


    private void goToMainActivity(String userNameParam) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra(USERNAME, userNameParam);
        startActivity(i);

    }

}
