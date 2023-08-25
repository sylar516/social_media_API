package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.MessageDto;
import ru.effective_mobile.social_media_api.entity.Message;
import ru.effective_mobile.social_media_api.entity.User;
import ru.effective_mobile.social_media_api.exception.NotFoundException;
import ru.effective_mobile.social_media_api.repository.MessageRepository;
import ru.effective_mobile.social_media_api.repository.UserRepository;
import ru.effective_mobile.social_media_api.service.MessageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageRepository messageRepository;

    private UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public MessageDto getMessageById(int id) {
        return messageToDto(messageRepository.findById(id).orElseThrow(() -> new NotFoundException("message with id = %d not found".formatted(id))));
    }

    @Override
    @Transactional
    public List<MessageDto> getAllMessages() {
        return messageListToDto(messageRepository.findAll());
    }

    @Override
    @Transactional
    public List<MessageDto> getMessagesByUsers(int senderId, int receiverId) {
        userRepository.findById(senderId).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(senderId)));
        userRepository.findById(receiverId).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(receiverId)));
        return messageListToDto(messageRepository.findMessagesByUsers(senderId, receiverId));
    }

    @Override
    @Transactional
    public Integer sendMessage(MessageDto messageDto) {
        Message message = dtoToMessage(messageDto);
        messageRepository.save(message);
        return message.getId();
    }

    @Override
    @Transactional
    public void deleteMessageById(Integer id) {
        messageRepository.findById(id).orElseThrow(() -> new NotFoundException("message with id = %d not found".formatted(id)));
        messageRepository.deleteById(id);
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

    private Message dtoToMessage(MessageDto messageDto) {
        Message message = new Message();
        User sender = userRepository.findById(messageDto.getSenderId()).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(messageDto.getSenderId())));
        User receiver = userRepository.findById(messageDto.getReceiverId()).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(messageDto.getReceiverId())));

        message.setText(messageDto.getText());
        message.setSendDate(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);

        return message;
    }
}
