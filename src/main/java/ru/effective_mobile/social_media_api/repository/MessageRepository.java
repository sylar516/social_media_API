package ru.effective_mobile.social_media_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.effective_mobile.social_media_api.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("from Message where sender.id in (:sender_id, :receiver_id) and receiver.id in (:sender_id, :receiver_id) order by sendDate asc")
    List<Message> findMessagesByUsers(@Param("sender_id") int senderId, @Param("receiver_id") int receiverId);

    @Modifying
    @Query("delete from Message where sender.id = :user_id or receiver.id = :user_id")
    void deleteMessagesBySender(@Param("user_id") int userId);
}
