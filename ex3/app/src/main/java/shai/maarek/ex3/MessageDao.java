package shai.maarek.ex3;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MessageDao {

    @Insert
    void insert (Message message);

    @Query("DELETE FROM message_table")
    void deleteAll();

//    @Query("DELETE FROM users WHERE user_id = :userId")
//    void deleteByUserId(long userId);

    @Delete
    public void deleteMessage(Message message);

    @Query("SELECT * from message_table ORDER BY `key` ASC")
        LiveData<List<Message>> getAllMessages();

}
