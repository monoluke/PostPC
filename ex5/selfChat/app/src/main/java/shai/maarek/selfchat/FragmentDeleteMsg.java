package shai.maarek.selfchat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDeleteMsg extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_delete_message, container, false);

        TextView textView_msg_details = v.findViewById(R.id.textView_message_details);
        textView_msg_details.setText("Message Details");

        TextView textView_timestamp = v.findViewById(R.id.textView_message_timestamp);

        TextView textView_phone_src = v.findViewById(R.id.textView_message_phone_src);

//        Button goFishButton = v.findViewById(R.id.goFishButton1);
//        Message messageToDelete = getArguments().get
        return v;
    }
}
