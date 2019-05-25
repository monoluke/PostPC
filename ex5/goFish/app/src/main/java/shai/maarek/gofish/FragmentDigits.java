package shai.maarek.gofish;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentDigits extends Fragment {
//    private FragmentDigitsListener listener;

    //    public interface FragmentDigitsListener{
//        void onInputSent(CharSequence input);
//    }
    public static final String FRAME_TAG = "digits_tag";
    public static String CHOSEN_DIGITS = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_digits, container, false);

        final EditText editText = v.findViewById(R.id.editText1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 5) {
                    String TAG = "onTextChanged";
                    Log.d(TAG, "onTextChanged: " + " " + s);
                    CHOSEN_DIGITS = s.toString();
                    Fragment mainFrag = getFragmentManager().findFragmentById(R.id.container_main);
                    Fragment digitsFrag = getFragmentManager().findFragmentByTag(FRAME_TAG);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.detach(digitsFrag);
                    transaction.show(mainFrag);
                    transaction.commit();

//                    Fragment mainFrag = getFragmentManager().findFragmentById(R.id.container_main);
//                    Fragment digitsFrag = getFragmentManager().findFragmentById(R.id.container_digits);
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.hide(digitsFrag);
//                    transaction.show(mainFrag);
//                    transaction.commit();

//                    editText.setText("");
//                    listener.onInputSent(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;


    }

    @Override
    public void onDetach() {
        super.onDetach();
//        Fragment mainFrag = getFragmentManager().findFragmentById(R.id.container_main);
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.show(mainFrag);
//        transaction.commit();
//        listener = null;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        // check if activity / fragment implements the listener FragmentDigitsListener
//        if (context instanceof FragmentDigitsListener){
//            listener = (FragmentDigitsListener) context;
//        }
//        else{
//            throw new RuntimeException(context.toString() + " must implement FragmentDigitsListener");
//        }
//
//    }
}
