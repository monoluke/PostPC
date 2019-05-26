package shai.maarek.selfchat;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MessageRepository {
    public MessageDao getmMessageDao() {
        return mMessageDao;
    }

    public LiveData<List<Message>> getmAllMessages() {
        return mAllMessages;
    }

    private MessageDao mMessageDao;
    private LiveData<List<Message>> mAllMessages;

    MessageRepository(Application application) {
        MessageRoomDatabase db = MessageRoomDatabase.getDatabase(application);
        mMessageDao = db.messageDao();
        mAllMessages = mMessageDao.getAllMessages();
    }

    LiveData<List<Message>> getAllMessages() {
        return mAllMessages;
    }

    public void insert(Message message) {
        new insertAsyncTask(mMessageDao).execute(message);
    }

    private static class insertAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao mAsyncTaskDao;

        insertAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
