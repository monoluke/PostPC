package shai.maarek.ex3;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Message.class}, version = 1)
public abstract class MessageRoomDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
    private static MessageRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MessageDao mDao;

        PopulateDbAsync(MessageRoomDatabase db) {
            mDao = db.messageDao();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
//            mDao.deleteAll();
//
//            for (int i = 0; i <= messages.length - 1; i++) {
//                Message message = new Message(messages[i]);
//                mDao.insert(message);
//            }
            return null;
        }
    }
    static MessageRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MessageRoomDatabase.class) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MessageRoomDatabase.class, "message_database")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MessageRoomDatabase.class, "message_database")
//                            .fallbackToDestructiveMigration()
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
        }
        return INSTANCE;
    }




}
