package shai.maarek.ex2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.RelativeLayout;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import java.util.ArrayList;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> textMessages = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> textMessages,  Context context) {
        this.textMessages = textMessages;
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
        viewHolder.textView.setText(textMessages.get(i));
    }

    @Override
    public int getItemCount() {
        return this.textMessages.size();
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
