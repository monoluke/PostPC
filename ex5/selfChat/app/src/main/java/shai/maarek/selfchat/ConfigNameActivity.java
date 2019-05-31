package shai.maarek.selfchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ConfigNameActivity extends AppCompatActivity implements View.OnClickListener {

    private String userName;
    private DatabaseReference mDatabase;
    private final String uid = "constant_user";
    private EditText editText_enter_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config_name);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        this.userName = "";

        TextView textView_hello_user = findViewById(R.id.textView_hello_user);
        textView_hello_user.setText("Hello, new user!");

        TextView textView_please_fill = findViewById(R.id.textView_please_fill);
        textView_please_fill.setText("Please fill up your name:");

        editText_enter_name = findViewById(R.id.editText_enter_name);

        Button button_my_name = findViewById(R.id.button_my_name);
        button_my_name.setText("This is my name");
        button_my_name.setOnClickListener(this);

        Button button_skip = findViewById(R.id.button_skip);
        button_skip.setText("Skip");
        button_skip.setOnClickListener(this);


        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // if >1 then the user already entered their name. call main activity and send name
//                if (dataSnapshot.getChildrenCount()>1){
                Iterable iterable = dataSnapshot.getChildren();
                Iterator iterator = iterable.iterator();
                String name = "";
                while (iterator.hasNext()) {
                        DataSnapshot dataSnapshot1 = (DataSnapshot) iterator.next();
                        name = dataSnapshot1.getKey();
                        if (!name.equals("messages")){
                            userName = name;
                            break;
                        }
                    }
                if (!userName.equals("")){
                    goToMainActivity(userName);
                }
//                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_my_name:
                String username = editText_enter_name.getText().toString();
                mDatabase.child("users").child(uid).child(username).setValue("-");
                goToMainActivity(username);
                break;

            case R.id.button_skip:
                goToMainActivity("");
                break;
        }
    }


    private void goToMainActivity(String userNameParam){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("username", userNameParam);
        startActivity(i);

    }

}
