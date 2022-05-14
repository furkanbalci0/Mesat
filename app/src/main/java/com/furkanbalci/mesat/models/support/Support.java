package com.furkanbalci.mesat.models.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Support {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("sender_id")
    @Expose
    private Long senderId;
    @SerializedName("content_title")
    @Expose
    private String contentTitle;
    @SerializedName("content_message")
    @Expose
    private String contentMessage;

    public Support(Long id, Long senderId, String contentTitle, String contentMessage) {
        this.id = id;
        this.senderId = senderId;
        this.contentTitle = contentTitle;
        this.contentMessage = contentMessage;
    }

    public Long getId() {
        return id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public String getContentMessage() {
        return contentMessage;
    }
}
