package ru.effective_mobile.social_media_api.entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "social_media", name = "friend")
public class Friend {
    @EmbeddedId
    private FriendId friendId;
}
