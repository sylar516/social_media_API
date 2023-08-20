package ru.effective_mobile.social_media_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effective_mobile.social_media_api.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
