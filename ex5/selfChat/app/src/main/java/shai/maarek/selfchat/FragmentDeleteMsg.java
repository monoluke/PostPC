package shai.maarek.selfchat;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.util.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class FragmentDeleteMsg extends Fragment {//implements View.OnClickListener {
    public static String messageID;
    private DatabaseReference mDatabase;
    private Message messageToDelete;
    public static final String FRAG_DEL_MSG_TAG = "fragment_delete_message";
    public SuicidalFragmentListener suicideListener;
    private Button button_delete_permanently;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_delete_message, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        TextView textView_msg_details = v.findViewById(R.id.textView_message_details);


        TextView textView_timestamp = v.findViewById(R.id.textView_message_timestamp);

        TextView textView_phone_src = v.findViewById(R.id.textView_message_phone_src);

        button_delete_permanently = v.findViewById(R.id.button_delete_permanently);
        button_delete_permanently.setText("Delete permanently?");
//        button_delete_permanently.setOnClickListener(this);


        mDatabase.child("users").child(ConfigNameActivity.uid).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!Strings.isEmptyOrWhitespace(messageID)) {
                    if (dataSnapshot.hasChild(messageID)) {
                        messageToDelete = dataSnapshot.child(messageID).getValue(Message.class);
                        textView_msg_details.setText("Message to delete: " + messageToDelete.getMessage());
                        textView_timestamp.setText("Time stamp: " + messageToDelete.getTimestamp() + "");
                        textView_phone_src.setText("Phone: " + messageToDelete.getManufacturer() + " " + messageToDelete.getModel());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

//    @Override
//    public void onClick(View v) {
//        String TAG = "haha";
//        Log.d(TAG, "onClick: ");
//    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            suicideListener = (SuicidalFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new RuntimeException(getActivity().getClass().getSimpleName() + " must implement the suicide listener to use this fragment", e);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Attach the close listener to whatever action on the fragment you want

        addSuicideTouchListener();
    }

    private void addSuicideTouchListener() {
        Objects.requireNonNull(button_delete_permanently).setOnClickListener(v -> suicideListener.onFragmentSuicide(getTag()));
    }





//    @Override
//    public void onClick(View v) {
//
//
//    }
//        mDatabase.child("users").child(ConfigNameActivity.uid).child("messages").child(messageToDelete.getId()).removeValue();
//        android.app.Fragment delete_msg_frag = getFragmentManager().findFragmentByTag(FRAG_DEL_MSG_TAG);
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.detach(delete_msg_frag);
//        transaction.commit();
//    }
}
