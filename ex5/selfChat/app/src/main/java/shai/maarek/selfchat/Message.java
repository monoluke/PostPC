package shai.maarek.selfchat;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message_table")
public class Message {//implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    private int key;
    private String id;
    private String message;
    private long timestamp;
    private String manufacturer;
    private String model;


    // Ctors
    public Message() { }

    public Message(String message) {
        this.message = message;
    }

    // Setters and Getters
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


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public void setManufacturer(String manufacturerParam) {
        this.manufacturer = manufacturerParam; // manufacturer == "xiaomi"
    }

    public String getManufacturer(){ return this.manufacturer; }


    public void setModel(String modelParam){
        this.model = modelParam; // model == "redmi note 4"
    }

    public String getModel() { return this.model; }


//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(id);
//        dest.writeString(message);
//        dest.writeLong(timestamp);
//        dest.writeString(this.manufacturer);
////        dest.writeString(this.model);
//    }
//
//
//    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
//        public Message createFromParcel(Parcel in) {
//            return new Message(in);
//        }
//
//        public Message[] newArray(int size) {
//            return new Message[size];
//        }
//    };
//
//    private Message(Parcel in) {
//        this.id = in.readString();
//        this.message = in.readString();
//        this.timestamp = in.readLong();
//        this.manufacturer = in.readString();
//        this.model = in.readString();
//    }
}
