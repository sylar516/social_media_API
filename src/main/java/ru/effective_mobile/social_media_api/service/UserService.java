package ru.effective_mobile.social_media_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.UserDto;
import ru.effective_mobile.social_media_api.entity.User;
import ru.effective_mobile.social_media_api.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.effective_mobile.social_media_api.service.FriendService.friendListToDto;
import static ru.effective_mobile.social_media_api.service.PostService.postListToDto;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userListToDto(userRepository.findAll());
    }

    public UserDto getUserById(int id) {
        return userToDto(userRepository.findById(id).get());
    }

    private List<UserDto> userListToDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(
                user -> {
                    UserDto userDto = UserDto.builder()
                            .setName(user.getName())
                            .setEmail(user.getEmail())
                            .setPosts(postListToDto(user.getPosts()))
                            .setFriends(friendListToDto(user.getFriends()))
                            .build();
                    userDtos.add(userDto);
                }
        );
        return userDtos;
    }

    private UserDto userToDto(User user) {
        return UserDto.builder()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPosts(postListToDto(user.getPosts()))
                .setFriends(friendListToDto(user.getFriends()))
                .build();
    }
}
