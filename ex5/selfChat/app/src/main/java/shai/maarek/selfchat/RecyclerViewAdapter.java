package shai.maarek.selfchat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
//    private static final String TAG = "RecyclerViewAdapter";

    public List<Message> getTextMessages() {
        return textMessages;
    }

    private List<Message> textMessages;
    private Context context;


    //
    private TextView nameTextView;
    //    private Fragment mFragment;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int p=getLayoutPosition();
//
//                Notes notes = mNotes.get(p);
//                Toast.makeText(getContext(), "Recycle Click" + p +"  ", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
//    public RecyclerViewAdapter(Fragment fragment) {
//        this.mFragment = fragment;
//    }


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
//        Log.d(TAG, "+_+_+onBindViewHolder: "+ textMessages.get(i));
        if (textMessages != null) {
            Message current = textMessages.get(i);
            viewHolder.textView.setText(current.getMessage());

        } else {
            viewHolder.textView.setText("no messages");
        }
    }

    public void setTextMessages(List<Message> messages) {
        textMessages = messages;
        notifyDataSetChanged();
    }

    public void addTextMessage(Message message) {
        if (textMessages == null) {
            textMessages = new ArrayList<>();
        }
        textMessages.add(message);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (textMessages != null) {
            return this.textMessages.size();
        } else {
            return 0;
        }
    }

    @SuppressLint("NewApi")
    public void removeMessage(Message message) {
        this.textMessages.removeIf(x -> x.getId().equals(message.getId()));
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
//            itemView.setOnLongClickListener(this);
        }
//        @Override
//        public boolean onLongClick(View view) {
//            // Handle long click
//            // Return true to indicate the click was handled
//            System.out.println("+++onLongClick:  "+ getAdapterPosition());
//
//            return true;
//        }

    }


}
