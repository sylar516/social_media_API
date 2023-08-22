package ru.effective_mobile.social_media_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.effective_mobile.social_media_api.entity.Relationship;
import ru.effective_mobile.social_media_api.entity.RelationshipId;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipId> {
    @Query("from Relationship where relationshipId.user1.id = :id")
    List<Relationship> findRelationshipsByUserId(@Param("id") int id);

    @Query("from Relationship where relationshipId.user1.id in (:sender_id, :receiver_id) and relationshipId.user2.id in (:sender_id, :receiver_id)")
    Relationship findRelationshipByUsers(@Param("sender_id") int senderId, @Param("receiver_id") int receiverId);
}
