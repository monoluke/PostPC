package shai.maarek.selfchat;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SuicidalFragmentListener {
    EditText editText;
    private static RecyclerViewAdapter adapter;
    private DatabaseReference mDatabase;
    private MessageViewModel mMessageViewModel;
    private Boolean initialized = false;
    private long idSync = 0;
    private final String uid = "constant_user";
    public static boolean DeleteMessage;

    static class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        private MessageViewModel mMessageViewModel = null;
        private DataSnapshot dataSnapshot = null;
        private ExampleAsyncTaskListener listener;

        public void setListener(ExampleAsyncTaskListener listener) {
            this.listener = listener;
        }

        public interface ExampleAsyncTaskListener {
            void onExampleAsyncTaskFinished(Void value);
        }

        public void setData(DataSnapshot dataSnapshot, MessageViewModel messageViewModel) {
            this.dataSnapshot = dataSnapshot;
            this.mMessageViewModel = messageViewModel;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        protected Void doInBackground(Void... param) {
            List<Message> messagesFromDB = new ArrayList<>();
            dataSnapshot.getChildren().forEach(x -> messagesFromDB.add(x.getValue(Message.class)));
            this.mMessageViewModel.insertBulk(messagesFromDB);
            Log.d("Init", String.format("Loaded %s messages from DB", messagesFromDB.size()));
            return null;
        }

        @Override
        protected void onPostExecute(Void value) {
            super.onPostExecute(value);
            if (listener != null) {
                listener.onExampleAsyncTaskFinished(value);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();



        // get username if there is one.
        // todo unmark it
        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");
//        String username = "Vecino";


        this.mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        this.mMessageViewModel.clear();
        RecyclerView recyclerView = findViewById(R.id.recyclerView0);
        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button = findViewById(R.id.Button0);
        this.editText = findViewById(R.id.editText0);


//        this.editText.setText("");
        button.setText(R.string.button);
        button.setOnClickListener(this);

        TextView textView_user_name = findViewById(R.id.textView4);
        textView_user_name.setText(username);


        mDatabase.child("users").child(uid).addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
                asyncTaskRunner.setListener(value -> {
                    Toast.makeText(getApplicationContext(), "Finished Loading Messages from Firebase!", Toast.LENGTH_SHORT).show();
                    initialized = true;
                });
                asyncTaskRunner.setData(dataSnapshot, mMessageViewModel);
                asyncTaskRunner.execute();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child("users").child(uid).child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!initialized) {
                    return;
                }
                mMessageViewModel.insert(dataSnapshot.getValue(Message.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Deleted message!", Toast.LENGTH_SHORT).show();
                Message message = dataSnapshot.getValue(Message.class);
                mMessageViewModel.delete(message);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageViewModel.getmAllMessages().observeForever(words -> {
            String TAG = "MessageInit";
            String MSG = "Current size of chat messages list:  ";
            if (adapter.getTextMessages() == null && words != null) {
                Log.d(TAG, MSG + words.size());
            }
            adapter.setTextMessages(words);
        });

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                List<Message> messageList = mMessageViewModel.getmAllMessages().getValue();
                Message messageToRemove = messageList.get(position);
                FragmentDeleteMsg.messageID = mDatabase.child("users").child(uid).child("messages").child(messageToRemove.getId()).getKey();
                FragmentDeleteMsg fragmentDeleteMsg= new FragmentDeleteMsg();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container_delete_msg, fragmentDeleteMsg, FragmentDeleteMsg.FRAG_DEL_MSG_TAG);
                transaction.addToBackStack(null);
                transaction.commit();

//                new AlertDialog.Builder(view.getContext())
//                        .setTitle("Delete message?")
//                        .setMessage("The selected message will be deleted")
//                        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
//                            List<Message> messageList = mMessageViewModel.getmAllMessages().getValue();
//                            if (messageList != null && messageList.size() >= position) {
//                                Message messageToRemove = messageList.get(position);
//                                mDatabase.child("users").child(uid).child("messages").child(messageToRemove.getId()).removeValue();
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, null)
//                        .show();
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (editText.getText().toString().trim().equals("")) {
            String EMPTY_STRING_MSG = "Beep Boop. You cannot send an empty message.";
            Toast.makeText(getApplicationContext(), EMPTY_STRING_MSG, Toast.LENGTH_SHORT).show();
        } else {
            Message item = new Message(editText.getText().toString());
            long timeInMillis = Calendar.getInstance().getTimeInMillis() + ++idSync;
            item.setTimestamp(timeInMillis);
            String key = mDatabase.child("users").child(uid).child("messages").push().getKey();
            item.setId(key);
            item.setManufacturer(android.os.Build.MANUFACTURER);
            item.setModel(android.os.Build.MODEL);
            mDatabase.child("users").child(uid).child("messages").child(key).setValue(item);
            editText.setText("");
        }
        this.editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        this.editText.setText("");
    }

    @Override
    public void onFragmentSuicide(String tag) {
        // Check tag if you do this with more than one fragmen, then:
        getFragmentManager().popBackStackImmediate();
        // delete message
        mDatabase.child("users").child(uid).child("messages").child(FragmentDeleteMsg.messageID).removeValue();
        Log.d("FUCK", "onFragmentSuicide: ");
    }

}


