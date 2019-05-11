package shai.maarek.ex4;

import android.app.Application;
import android.os.Build;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository mRepository;
    private LiveData<List<Message>> mAllMessages;

    public MessageViewModel(Application application) {
        super(application);
        mRepository = new MessageRepository(application);
        mAllMessages = mRepository.getAllMessages();
    }

    LiveData<List<Message>> getmAllMessages() {
        return mAllMessages;
    }


    public void insert(Message message) {
        message.setKey(message.getId().hashCode());
        mRepository.insert(message);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void insertBulk(List<Message> messages) {
        messages.forEach(x -> x.setKey(x.getId().hashCode()));
        mRepository.getmMessageDao().insert(messages);
    }

    public void delete(Message message) {
        message.setKey(message.getId().hashCode());
        mRepository.getmMessageDao().deleteMessage(message);
    }

    public void clear() {
        mRepository.getmMessageDao().deleteAll();
    }

}
