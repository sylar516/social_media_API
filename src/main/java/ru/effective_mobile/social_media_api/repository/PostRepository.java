package ru.effective_mobile.social_media_api.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.effective_mobile.social_media_api.entity.Post;

import java.util.List;
import java.util.Map;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> getAllPostsByUserId(int userId);

    @Query("from Post where user.id in (:user_ids)")
    List<Post> getPostsByUserIds(@Param("user_ids") List<Integer> userIds);
}
