package shai.maarek.ex1;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    EditText editText;
    final String TEXTBOX_STRING = "";
    private static final String TAG = "mainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.textView = (TextView)findViewById(R.id.textView2);
        Button button = (Button)findViewById(R.id.Button0);
        this.editText = (EditText)findViewById(R.id.editText0);

        this.editText.setText(R.string.edit_text);
        button.setText(R.string.button);
        this.textView.setText(R.string.text_view);

        this.textView.requestFocus();
        button.setOnClickListener(this);


    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putString(TEXTBOX_STRING, this.textView.getText().toString());
        super.onSaveInstanceState(state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state){
        super.onRestoreInstanceState(state);
        this.textView.setText(state.getString(TEXTBOX_STRING));
    }

    @Override
    public void onClick(View view){
                this.editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                this.textView.setText(editText.getText());
                this.editText.setText("");
    }


}


