package shai.maarek.gofish;

import android.app.FragmentTransaction;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "+=+= onClickButton =+=+";

//    private FragmentGofish fragmentGofish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentMain fragmentMain = new FragmentMain();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_main, fragmentMain);
        transaction.addToBackStack(null);
        transaction.commit();


    }

//    public void replaceFragment(Fragment someFragment) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.container_goFish, someFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}
