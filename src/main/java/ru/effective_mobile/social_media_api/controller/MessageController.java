package ru.effective_mobile.social_media_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.social_media_api.dto.MessageDto;
import ru.effective_mobile.social_media_api.dto.UserDto;
import ru.effective_mobile.social_media_api.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
@Tag(name = "message-controller", description = "контроллер для взаимодействия с сущностью \"сообщение\"")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class),
                    mediaType = "application/json",
                    examples = @ExampleObject(name = "Messages",value = """
                            [
                              {
                                "text": "привет, как дела?",
                                "sendDate": "2023-08-23T12:00:57",
                                "senderId": 2,
                                "receiverId": 3
                              },
                              {
                                "text": "хорошо, а как у тебя?",
                                "sendDate": "2023-08-23T12:01:15",
                                "senderId": 3,
                                "receiverId": 2
                              },
                              {
                                "text": "лучше некуда",
                                "sendDate": "2023-08-23T12:01:31",
                                "senderId": 2,
                                "receiverId": 3
                              },
                              {
                                "text": "привет, как прошёл твой день?",
                                "sendDate": "2023-08-23T23:46:05",
                                "senderId": 1,
                                "receiverId": 2
                              }
                            ]
                            """)))
    @Operation(summary = "Получить все сообщения")
    @GetMapping("/")
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class),
                    mediaType = "application/json",
                    examples = @ExampleObject(name = "Message",value = """
                            {
                              "text": "привет, ничего особенного, очередной дождливый день",
                              "sendDate": "дата на момент создания сообщения",
                              "senderId": 2,
                              "receiverId": 1
                            }
                            """)))
    @Operation(summary = "Получить сообщение с указанным id")
    @GetMapping("/{id}")
    public MessageDto getMessageById(@PathVariable("id") @Parameter(name = "id", description = "Message ID", example = "5") int id) {
        return messageService.getMessageById(id);
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = MessageDto.class),
                    mediaType = "application/json",
                    examples = @ExampleObject(name = "Chat",value = """
                            [
                              {
                                "text": "привет, как дела?",
                                "sendDate": "2023-08-23T12:00:57",
                                "senderId": 2,
                                "receiverId": 3
                              },
                              {
                                "text": "хорошо, а как у тебя?",
                                "sendDate": "2023-08-23T12:01:15",
                                "senderId": 3,
                                "receiverId": 2
                              },
                              {
                                "text": "лучше некуда",
                                "sendDate": "2023-08-23T12:01:31",
                                "senderId": 2,
                                "receiverId": 3
                              }
                            ]
                            """)))
    @Operation(summary = "Получить переписку между пользователями")
    @GetMapping("/chat")
    public List<MessageDto> getMessagesByUsers(@RequestParam("sender_id") @Parameter(name = "sender_id", description = "Sender ID", example = "2") int senderId,
                                               @RequestParam("receiver_id") @Parameter(name = "receiver_id", description = "Receiver ID", example = "3") int receiverId) {
        return messageService.getMessagesByUsers(senderId, receiverId);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "text": "привет, ничего особенного, очередной дождливый день",
                                      "senderId": 2,
                                      "receiverId": 1
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = Integer.class),
                    examples = @ExampleObject(name = "Message id",value = "5")))
    @Operation(summary = "Отправить сообщение пользователю")
    @PostMapping("/send")
    public int sendMessage(@RequestBody MessageDto messageDto) {
        return messageService.sendMessage(messageDto);
    }

    @Operation(summary = "Удалить сообщение с указанным id")
    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable("id") @Parameter(name = "id", description = "Message ID", example = "5") int id) {
        messageService.deleteMessageById(id);
    }
}
