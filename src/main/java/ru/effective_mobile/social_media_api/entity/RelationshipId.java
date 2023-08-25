package ru.effective_mobile.social_media_api.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RelationshipId implements Serializable {
    @ManyToOne
    private User user1;
    @ManyToOne
    private User user2;

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public RelationshipId() {
    }

    public RelationshipId(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationshipId relationshipId = (RelationshipId) o;
        return user1.getId().equals(relationshipId.user1.getId()) && user2.getId().equals(relationshipId.user2.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }
}
