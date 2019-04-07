package shai.maarek.ex1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textView;
    EditText editText;
    final String TEXTBOX_STRING = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.textView = (TextView)findViewById(R.id.textView2);
        Button button = (Button)findViewById(R.id.Button0);
        this.editText = (EditText)findViewById(R.id.editText0);

        this.textView.requestFocus();
        button.setOnClickListener(this);
//        editText.setSelectAllOnFocus(true);
//        this.editText.setOnClickListener(this);


//        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
//        editText.setOnFocusChangeListener(ofcListener);


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
//        switch(view.getId()){
//            case R.id.Button0:
                this.editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                this.textView.setText(editText.getText());
//                break;

//            case R.id.editText0:
//                this.editText.getText().clear();
//                this.editText.selectAll();
//                this.editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
//                break;
//        }

    }
//    class MyFocusChangeListener implements View.OnFocusChangeListener {
//        public void onFocusChange(View v, boolean hasFocus){
//
//            if(v.getId() == R.id.editText0 && !hasFocus) {
//
//                InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                assert imm != null;
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//
//            }
//        }
//
//    }


}


