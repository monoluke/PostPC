package shai.maarek.ex4;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository mRepository;
    private LiveData<List<Message>> mAllMessages;

    public MessageViewModel(Application application){
        super(application);
        mRepository = new MessageRepository(application);
        mAllMessages = mRepository.getAllMessages();
//        String LOG_MSG_LIST = " Current size of chat messages list: ";
//        String TAG = "+_+_+";
//        try{
//            Log.d(TAG, LOG_MSG_LIST + Objects.requireNonNull(mAllMessages.getValue()).size());
//        }
//        catch (NullPointerException nullPointerException){
//            Log.d(TAG, LOG_MSG_LIST + "0");
//        }
    }

    LiveData<List<Message>> getmAllMessages(){
        return mAllMessages;
    }


    public void insert(Message message){
        mRepository.insert(message);
    }

    public MessageRepository getmRepository() {
        return mRepository;
    }
}
