package ru.effective_mobile.social_media_api.entity;

import jakarta.persistence.*;

@Entity
@Table(schema = "social_media", name = "relationship")
public class Relationship {
    @EmbeddedId
    private RelationshipId relationshipId;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Relationship() {
    }

    public Relationship(RelationshipId relationshipId, Status status) {
        this.relationshipId = relationshipId;
        this.status = status;
    }

    public RelationshipId getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(RelationshipId relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
