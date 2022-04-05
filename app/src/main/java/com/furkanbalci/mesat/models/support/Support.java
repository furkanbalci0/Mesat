package com.furkanbalci.mesat.models.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Support {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sender_id")
    @Expose
    private long senderId;

    @SerializedName("content_title")
    @Expose
    private String contentTitle;

    @SerializedName("content_message")
    @Expose
    private String contentMessage;

    public Support(int id, long senderId, String contentTitle, String contentMessage) {
        this.id = id;
        this.senderId = senderId;
        this.contentTitle = contentTitle;
        this.contentMessage = contentMessage;
    }

    public int getId() {
        return this.id;
    }

    public long getSenderId() {
        return this.senderId;
    }

    public String getContentTitle() {
        return this.contentTitle;
    }

    public String getContentMessage() {
        return this.contentMessage;
    }
}
