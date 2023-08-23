package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    Integer createUser(UserDto userDto);
    void updateUser(Integer id, UserDto userDto);
    void deleteUserById(Integer id);
    void deleteAllUsers();
}
