package shai.maarek.gofish;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static shai.maarek.gofish.FragmentDigits.FRAME_TAG;

public class FragmentMain extends Fragment implements View.OnClickListener {
    private static final String TAG = "+=+= onClickButton =+=+";
    private TextView digitsShow;
//    private FragmentDigits.FragmentDigitsListener listener;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        Button goFishButton = v.findViewById(R.id.goFishButton1);
        goFishButton.setText("Go Fish!");
        goFishButton.setOnClickListener(this);

        TextView digitsView = v.findViewById(R.id.digitsView1);
        digitsView.setText("Tap on me!");
        digitsView.setOnClickListener(this);

        this.digitsShow = v.findViewById(R.id.digitsView2);

        return v;

    }

    public void onHiddenChanged(boolean hidden) {
        if (!hidden && FragmentDigits.CHOSEN_DIGITS != null) {
            this.digitsShow.setText(new String(FragmentDigits.CHOSEN_DIGITS));
            FragmentDigits.CHOSEN_DIGITS = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goFishButton1:
                Log.d(TAG, "onClickButton: ");
                FragmentGofish fragmentGofish = new FragmentGofish();
                replaceFragment(fragmentGofish, "gofish_tag");
                break;

            case R.id.digitsView1:
                Log.d(TAG, "onClickTextView: ");
                FragmentDigits fragmentDigits = new FragmentDigits();
                replaceFragment(fragmentDigits, FRAME_TAG);
                break;

        }
    }


    public void replaceFragment(Fragment someFragment, String tagForNewFragment) {
        Fragment mainFrag = getFragmentManager().findFragmentById(R.id.container_main);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_goFish, someFragment, tagForNewFragment);
        transaction.addToBackStack(null);
        transaction.hide(mainFrag);
        transaction.commit();
    }


//    @Override
//    public void onInputSent(CharSequence input) {
//        this.digitsShow.setText(input);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        // check if activity / fragment implements the listener FragmentDigitsListener
//        if (context instanceof FragmentDigits.FragmentDigitsListener){
//            this.listener = (FragmentDigits.FragmentDigitsListener) context;
//        }
//        else{
//            throw new RuntimeException(context.toString() + " must implement FragmentDigitsListener");
//        }
//
//    }
}