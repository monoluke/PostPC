package shai.maarek.ex4;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    private static final String KEY_MSG_LIST = "KEY_MSG_LIST";
    private static final String TAG = "mainActivity";
    private static final String SAVED_SUPER_STATE = "super-state";
    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";
    private MessageViewModel mMessageViewModel;
//    private ArrayList<String> textMessages = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView0);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button button = findViewById(R.id.Button0);
        this.editText = findViewById(R.id.editText0);
        this.editText.setText("");
        button.setText(R.string.button);
        button.setOnClickListener(this);


        //  recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageViewModel.getmAllMessages().observe(this, new Observer<List<Message>>() {

            @Override
            public void onChanged(@Nullable final List<Message> words) {
                String TAG = "MessageInit";
                String MSG = "Current size of chat messages list:  ";
                // Update the cached copy of the words in the adapter.
                if (adapter.getTextMessages() == null && words != null) {
                    Log.d(TAG, MSG + words.size());
                }
                adapter.setTextMessages(words);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

//                Toast.makeText(getApplicationContext(), mMessageViewModel.getmAllMessages().[position] + " is clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete message?")
                        .setMessage("The selected message will be deleted")
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                            List<Message> messageList = mMessageViewModel.getmAllMessages().getValue();
                            if (messageList != null && messageList.size() >= position) {
                                Message messageToRemove = messageList.get(position);
                                mMessageViewModel.getmRepository().getmMessageDao().deleteMessage(messageToRemove);
                                Toast.makeText(getApplicationContext(), "Deleted message!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        }));


    }


    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

    }


    @Override
    public void onClick(View view) {
        if (editText.getText().toString().trim().equals("")) {
            String EMPTY_STRING_MSG = "Bip Boop. You cannot send an empty message.";
            Toast.makeText(getApplicationContext(), EMPTY_STRING_MSG, Toast.LENGTH_SHORT).show();
        } else {
            Message message1 = new Message(editText.getText().toString());
            mMessageViewModel.insert(message1);
        }
        this.editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        this.editText.setText("");

    }


}


