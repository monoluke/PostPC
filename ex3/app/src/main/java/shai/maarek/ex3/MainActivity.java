package shai.maarek.ex3;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    RecyclerView recyclerView;
    private ArrayList<String> textMessages = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private static final String KEY_MSG_LIST = "KEY_MSG_LIST";
    private static final String TAG = "mainActivity";
    private static final String SAVED_SUPER_STATE = "super-state";
    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button =findViewById(R.id.Button0);
        this.editText =findViewById(R.id.editText0);
        this.recyclerView = findViewById(R.id.recyclerView0);

        this.editText.setText(R.string.edit_text);
        button.setText(R.string.button);


        button.setOnClickListener(this);


        this.adapter = new RecyclerViewAdapter(textMessages, this);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putStringArrayList(KEY_MSG_LIST, this.textMessages);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        this.textMessages.clear();
        ArrayList<String> lkmlkm = state.getStringArrayList(KEY_MSG_LIST);
        if (lkmlkm != null) {
            this.textMessages.addAll(lkmlkm);
        }
        this.adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
//        Log.d(TAG, "~~~onClick: in onClick");
        if (editText.getText().toString().trim().equals("")) {
            String EMPTY_STRING_MSG = "Bip Boop. You cannot send an empty message.";
            Toast.makeText(getApplicationContext(), EMPTY_STRING_MSG, Toast.LENGTH_SHORT).show();
        } else {
            this.textMessages.add(editText.getText().toString());
//            Log.d(TAG, "~~~onClick: " + this.textMessages.get(this.textMessages.size() - 1));
//            Log.d(TAG, "~~~onClick: " + this.textMessages.size());
            this.adapter.notifyDataSetChanged();
        }

        this.editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
        this.editText.setText("");


//        arrayAdapter.notifyDataSetChanged();

    }


}


