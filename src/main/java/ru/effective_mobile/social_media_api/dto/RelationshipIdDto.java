package ru.effective_mobile.social_media_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class RelationshipIdDto {
    @Positive(message = "senderId cannot be zero or negative")
    private Integer senderId;

    @Positive(message = "receiverId cannot be zero or negative")
    private Integer receiverId;

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }
}
