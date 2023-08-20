package ru.effective_mobile.social_media_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective_mobile.social_media_api.dto.MessageDto;
import ru.effective_mobile.social_media_api.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
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
}
