package ru.effective_mobile.social_media_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.effective_mobile.social_media_api.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
