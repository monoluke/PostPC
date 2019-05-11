package shai.maarek.ex4;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Message message);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Message> messages);

    @Query("DELETE FROM message_table")
    void deleteAll();

    @Delete
    void deleteMessage(Message message);

    @Query("SELECT * from message_table ORDER BY `timestamp` ASC")
    LiveData<List<Message>> getAllMessages();

}
