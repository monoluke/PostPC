package shai.maarek.ex4;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message_table")
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int key;

    @NonNull
    @ColumnInfo(name = "message")
    private String mMessage;



    public Message(@NonNull String message){
        this.mMessage = message;
    }

    public void setKey(int key){ this.key=key; }

    public int getKey() { return this.key; }

    public String getMMessage(){
        return this.mMessage;
    }

}
