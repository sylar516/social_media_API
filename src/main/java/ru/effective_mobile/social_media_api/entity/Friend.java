package ru.effective_mobile.social_media_api.entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "social_media", name = "friend")
public class Friend {
    @EmbeddedId
    private FriendId friendId;

    @Enumerated(EnumType.STRING)
    private Status status;

    public FriendId getFriendId() {
        return friendId;
    }

    public void setFriendId(FriendId friendId) {
        this.friendId = friendId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "friendId=" + friendId +
                ", status=" + status +
                '}';
    }
}
