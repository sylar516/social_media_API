package ru.effective_mobile.social_media_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.effective_mobile.social_media_api.entity.Relationship;
import ru.effective_mobile.social_media_api.entity.RelationshipId;
import ru.effective_mobile.social_media_api.entity.Status;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, RelationshipId> {
    @Query("from Relationship where relationshipId.user1.id = :id")
    List<Relationship> findRelationshipsByUserId(@Param("id") int id);

    @Query("from Relationship where relationshipId.user1.id = :user1_id and relationshipId.user2.id = :user2_id and status = :status")
    Optional<Relationship> findRelationshipByRelationshipId(@Param("user1_id") int user1id, @Param("user2_id") int user2Id, @Param("status") Status status);

    @Query("from Relationship where relationshipId.user1.id in (:sender_id, :receiver_id) and relationshipId.user2.id in (:sender_id, :receiver_id)")
    List<Relationship> findRelationshipsByUsers(@Param("sender_id") int senderId, @Param("receiver_id") int receiverId);

    @Query("select relationshipId.user2.id from Relationship where relationshipId.user1.id = :user_id")
    List<Integer> findUserIdsBySubscriber(@Param("user_id") int userId);

    @Modifying
    @Query("delete from Relationship where relationshipId.user1.id = :sender_id and relationshipId.user2.id = :receiver_id")
    void deleteRelationshipsByRelationshipId(@Param("sender_id") int senderId, @Param("receiver_id") int receiverId);
    @Modifying
    @Query("delete from Relationship where relationshipId.user1.id = :user_id or relationshipId.user2.id = :user_id")
    void deleteRelationshipsByUser(@Param("user_id") int userId);
}
