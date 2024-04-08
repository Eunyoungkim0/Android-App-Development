package edu.uncc.emessageme.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class Message implements Serializable {
    String messageId, senderId, senderName, receiverId, receiverName, title, messageText;
    Timestamp date;
    Boolean receiverOpen, senderDelete, receiverDelete;

    public Message() {
    }

    public Message(String messageId, String senderId, String senderName, String receiverId, String receiverName, String title, String messageText, Timestamp date, Boolean receiverOpen, Boolean senderDelete, Boolean receiverDelete) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.title = title;
        this.messageText = messageText;
        this.date = date;
        this.receiverOpen = receiverOpen;
        this.senderDelete = senderDelete;
        this.receiverDelete = receiverDelete;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean getReceiverOpen() {
        return receiverOpen;
    }

    public void setReceiverOpen(Boolean receiverOpen) {
        this.receiverOpen = receiverOpen;
    }

    public Boolean getSenderDelete() {
        return senderDelete;
    }

    public void setSenderDelete(Boolean senderDelete) {
        this.senderDelete = senderDelete;
    }

    public Boolean getReceiverDelete() {
        return receiverDelete;
    }

    public void setReceiverDelete(Boolean receiverDelete) {
        this.receiverDelete = receiverDelete;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", title='" + title + '\'' +
                ", messageText='" + messageText + '\'' +
                ", date=" + date +
                ", receiverOpen=" + receiverOpen +
                ", senderDelete=" + senderDelete +
                ", receiverDelete=" + receiverDelete +
                '}';
    }
}
