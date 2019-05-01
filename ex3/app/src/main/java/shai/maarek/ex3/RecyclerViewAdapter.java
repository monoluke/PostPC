package shai.maarek.ex3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private List<Message> textMessages;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    // responsible for inflating the view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recycleviewitem, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "+++onBindViewHolder: "+ textMessages.get(i));
        if(textMessages != null){
            Message current = textMessages.get(i);
            viewHolder.textView.setText(current.getMMessage());
        }
        else{
            viewHolder.textView.setText("no messages");
        }
    }

    public void setTextMessages (List<Message> messages){
        textMessages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (textMessages != null) {
            return this.textMessages.size();
        }
        else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
//        CircleImageView circleImageView;
        TextView textView;
//        ConstraintLayout recyclerViewLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
//            recyclerViewLayout = itemView.findViewById(R.id.recyclerViewLayout);
        }
    }


}
