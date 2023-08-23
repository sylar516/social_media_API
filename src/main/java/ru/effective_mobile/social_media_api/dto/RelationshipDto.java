package ru.effective_mobile.social_media_api.dto;

import ru.effective_mobile.social_media_api.entity.Status;

public class RelationshipDto {
    private Integer senderId;

    private Integer receiverId;

    private Status status;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer senderId;
        private Integer receiverId;
        private Status status;

        public Builder setSenderId(Integer senderId) {
            this.senderId = senderId;
            return this;
        }

        public Builder setReceiverId(Integer receiverId) {
            this.receiverId = receiverId;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public RelationshipDto build() {
            return new RelationshipDto(this);
        }
    }

    public RelationshipDto() {
    }

    public RelationshipDto(Builder builder) {
        this.senderId = builder.senderId;
        this.receiverId = builder.receiverId;
        this.status = builder.status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
