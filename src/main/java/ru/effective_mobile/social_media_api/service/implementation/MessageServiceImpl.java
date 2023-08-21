package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.MessageDto;
import ru.effective_mobile.social_media_api.entity.Message;
import ru.effective_mobile.social_media_api.entity.User;
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
        return messageToDto(messageRepository.findById(id).get());
    }

    @Override
    @Transactional
    public List<MessageDto> getAllMessages() {
        return messageListToDto(messageRepository.findAll());
    }

    @Override
    public List<MessageDto> getMessagesByUsers(int senderId, int receiverId) {
        return messageListToDto(messageRepository.findMessagesByUsers(senderId, receiverId));
    }

    @Override
    @Transactional
    public Integer createMessage(MessageDto messageDto) {
        Message message = dtoToMessage(messageDto);
        messageRepository.save(message);
        return message.getId();
    }

    @Override
    @Transactional
    public void updateMessage(Integer id, MessageDto messageDto) {
        Message message = dtoToMessage(messageDto);
        message.setId(id);
        messageRepository.save(message);
    }

    @Override
    @Transactional
    public void deleteMessageById(Integer id) {
        messageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllMessages() {
        messageRepository.deleteAll();
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
        User sender = userRepository.findById(messageDto.getSenderId()).get();
        User receiver = userRepository.findById(messageDto.getReceiverId()).get();

        message.setText(messageDto.getText());
        message.setSendDate(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);

        return message;
    }
}
