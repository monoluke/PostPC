package shai.maarek.ex3;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
//    RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;



    private static final String KEY_MSG_LIST = "KEY_MSG_LIST";
    private static final String TAG = "mainActivity";
    private static final String SAVED_SUPER_STATE = "super-state";
    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";
    private MessageViewModel mMessageViewModel;
    private ArrayList<String> textMessages = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView0);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//
        Button button = findViewById(R.id.Button0);
        this.editText = findViewById(R.id.editText0);
//
        this.editText.setText(R.string.edit_text);
        button.setText(R.string.button);


        button.setOnClickListener(this);


//        this.adapter = new RecyclerViewAdapter(textMessages, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageViewModel.getmAllMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> words) {
                // Update the cached copy of the words in the adapter.
                adapter.setTextMessages(words);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

//        state.putStringArrayList(KEY_MSG_LIST, this.textMessages);
    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle state) {
//        super.onRestoreInstanceState(state);
//        this.textMessages.clear();
//        ArrayList<String> lkmlkm = state.getStringArrayList(KEY_MSG_LIST);
//        if (lkmlkm != null) {
//            this.textMessages.addAll(lkmlkm);
//        }
//        this.adapter.notifyDataSetChanged();
//    }
//
    @Override
    public void onClick(View view) {
        if (editText.getText().toString().trim().equals("")) {
            String EMPTY_STRING_MSG = "Bip Boop. You cannot send an empty message.";
            Toast.makeText(getApplicationContext(), EMPTY_STRING_MSG, Toast.LENGTH_SHORT).show();
        } else {
//            String message = editText.getText().toString();
            Message message1 = new Message(editText.getText().toString());
            mMessageViewModel.insert(message1);
//            this.textMessages.add(editText.getText().toString());
//            this.adapter.notifyDataSetChanged();
        }
//
        this.editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        this.editText.setText("");

    }



}


