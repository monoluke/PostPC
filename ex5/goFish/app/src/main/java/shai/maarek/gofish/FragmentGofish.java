package shai.maarek.gofish;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentGofish extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gofish, container, false);
        if (savedInstanceState == null) {
            String TAG = "ha";
            Log.d(TAG, "onCreate: ");
        }
        return v;
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Fragment mainFrag = getFragmentManager().findFragmentById(R.id.container_main);
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.show(mainFrag);
//        transaction.commit();
//
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
