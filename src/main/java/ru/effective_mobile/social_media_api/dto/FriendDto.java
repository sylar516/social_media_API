package ru.effective_mobile.social_media_api.dto;

import ru.effective_mobile.social_media_api.entity.Status;

public class FriendDto {
    private Integer userId;

    private Integer friendId;

    private Status status;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer userId;
        private Integer friendId;
        private Status status;

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder setFriendId(Integer friendId) {
            this.friendId = friendId;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public FriendDto build() {
            return new FriendDto(this);
        }
    }

    public FriendDto(Builder builder) {
        this.userId = builder.userId;
        this.friendId = builder.friendId;
        this.status = builder.status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
