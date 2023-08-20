package ru.effective_mobile.social_media_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.effective_mobile.social_media_api.entity.Friend;
import ru.effective_mobile.social_media_api.entity.FriendId;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    @Query("from Friend where friendId.user.id = :id")
    public List<Friend> findByUserId(@Param("id") int id);
}
