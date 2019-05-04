package shai.maarek.ex3;

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
