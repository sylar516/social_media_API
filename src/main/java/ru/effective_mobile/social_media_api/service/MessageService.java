package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.MessageDto;
import ru.effective_mobile.social_media_api.entity.Message;
import ru.effective_mobile.social_media_api.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    static List<MessageDto> messageListToDto(List<Message> messages) {
        List<MessageDto> messageDtos = new ArrayList<>();
        messages.forEach(
                message -> {
                    MessageDto messageDto = MessageDto.builder()
                            .setText(message.getText())
                            .setSendDate(message.getSendDate())
                            .setSenderId(message.getSender().getId())
                            .setReceiverId(message.getReceiver().getId())
                            .build();
                    messageDtos.add(messageDto);
                }
        );
        return messageDtos;
    }

    private MessageDto messageToDto(Message message) {
        return MessageDto.builder()
                .setText(message.getText())
                .setSendDate(message.getSendDate())
                .setSenderId(message.getSender().getId())
                .setReceiverId(message.getReceiver().getId())
                .build();
    }
}
