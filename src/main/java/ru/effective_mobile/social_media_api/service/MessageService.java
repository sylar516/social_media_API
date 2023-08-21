package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto getMessageById(int id);
    List<MessageDto> getAllMessages();
    List<MessageDto> getMessagesByUsers(int senderId, int receiverId);
    Integer createMessage(MessageDto messageDto);
    void updateMessage(Integer id, MessageDto messageDto);
    void deleteMessageById(Integer id);
    void deleteAllMessages();
}
