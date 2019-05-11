package shai.maarek.ex4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message_table")
public class Message {

    @PrimaryKey(autoGenerate = false)
    private int key;

    private String id;
    private String message;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }


    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
