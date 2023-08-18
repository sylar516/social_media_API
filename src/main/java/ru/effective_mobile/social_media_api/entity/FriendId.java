package ru.effective_mobile.social_media_api.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendId implements Serializable {
    @ManyToOne
    private User user;
    @ManyToOne
    private User friend;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendId friendId = (FriendId) o;
        return user.equals(friendId.user) && friend.equals(friendId.friend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, friend);
    }

    @Override
    public String toString() {
        return "FriendId{" +
                "user=" + user +
                ", friend=" + friend +
                '}';
    }
}
