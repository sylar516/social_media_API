package ru.effective_mobile.social_media_api.controller;

import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.social_media_api.dto.MessageDto;
import ru.effective_mobile.social_media_api.service.implementation.MessageServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public MessageDto getMessageById(@PathVariable("id") int id) {
        return messageService.getMessageById(id);
    }

    @GetMapping("/chat")
    public List<MessageDto> getMessagesByUsers(@RequestParam("sender_id") int senderId, @RequestParam("receiver_id") int receiverId) {
        return messageService.getMessagesByUsers(senderId, receiverId);
    }

    @PostMapping("/send")
    public int sendMessage(@RequestBody MessageDto messageDto) {
        return messageService.createMessage(messageDto);
    }

    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable("id") int id) {
        messageService.deleteMessageById(id);
    }
}
