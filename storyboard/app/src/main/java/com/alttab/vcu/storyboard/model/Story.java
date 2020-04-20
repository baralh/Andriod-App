package com.alttab.vcu.storyboard.model;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*Story POJO*/

@IgnoreExtraProperties
public class Story {


    public static final String FIELD_NAME = "name";
    public static final String FIELD_TIMESTAMP = "timestamp";
    public static final String FIELD_UID = "uid";
    public static final String FIELD_TEXT = "text";
    public static final String FIELD_CHUNK = "chunk";
    public static final String FIELD_UNAME = "uname";




    private String name;
    private @ServerTimestamp Date timestamp;
    private String uid;
    private String uname;
    private String text;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    private List<Chunk> chunk;


    public Story() {
        this.chunk = new ArrayList<Chunk>() ;
    }

    public Story(String name, Date timestamp, String uid) {
        this.name = name;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Story{" + "name='" + name + '\''
                + ", timestamp=" + timestamp
                + ", uid='" + uid + '\''
                + ", text='" + text + '\''
                + ", chunk=" + chunk
                + '}';
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Chunk> getChunk() {
        return chunk;
    }

    public void setChunk(List<Chunk> chunk) {
        this.chunk = chunk;
    }

}
