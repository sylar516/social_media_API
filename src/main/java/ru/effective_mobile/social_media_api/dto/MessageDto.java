package ru.effective_mobile.social_media_api.dto;

import java.time.LocalDateTime;

public class MessageDto {
    private String text;

    private LocalDateTime sendDate;

    private Integer senderId;

    private Integer receiverId;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String text;
        private LocalDateTime sendDate;
        private Integer senderId;
        private Integer receiverId;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setSendDate(LocalDateTime sendDate) {
            this.sendDate = sendDate;
            return this;
        }

        public Builder setSenderId(Integer senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder setReceiverId(Integer receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public MessageDto build() {
            return new MessageDto(this);
        }
    }

    public MessageDto(Builder builder) {
        this.text = builder.text;
        this.sendDate = builder.sendDate;
        this.senderId = builder.senderId;
        this.receiverId = builder.receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

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
