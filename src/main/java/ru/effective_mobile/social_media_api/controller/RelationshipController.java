package ru.effective_mobile.social_media_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.social_media_api.dto.RelationshipDto;
import ru.effective_mobile.social_media_api.dto.RelationshipIdDto;
import ru.effective_mobile.social_media_api.service.RelationshipService;

import java.util.List;

@RestController
@RequestMapping("/relationships")
@Tag(name = "relationship-controller", description = "контроллер для взаимодействия с сущностью \"отношения\". Отношения между пользователями могут иметь 3 состояния: заявка в друзья(FRIEND_REQUEST), подписчик(SUBSCRIBER) и друзья(FRIENDS)")
public class RelationshipController {
    private RelationshipService relationshipService;

    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = RelationshipDto.class),
                    examples = @ExampleObject(value = """
                            [
                              {
                                "senderId": 1,
                                "receiverId": 2,
                                "status": "FRIENDS"
                              },
                              {
                                "senderId": 1,
                                "receiverId": 3,
                                "status": "SUBSCRIBER"
                              },
                              {
                                "senderId": 2,
                                "receiverId": 1,
                                "status": "FRIENDS"
                              }
                            ]
                            """)))
    @Operation(summary = "Получить все отношения между пользователями")
    @GetMapping("/")
    public List<RelationshipDto> getAllRelationships() {
        return relationshipService.getAllRelationships();
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = RelationshipDto.class),
                    examples = @ExampleObject(name = "Relationships",value = """
                            [
                              {
                                "senderId": 1,
                                "receiverId": 2,
                                "status": "FRIENDS"
                              },
                              {
                                "senderId": 1,
                                "receiverId": 3,
                                "status": "SUBSCRIBER"
                              }
                            ]
                            """)))
    @Operation(summary = "Получить все отношения по пользователю с указанным идентификатором")
    @GetMapping("/{userId}")
    public List<RelationshipDto> getRelationshipsByUserId(@PathVariable("userId") @Parameter(name = "userId", description = "User ID", example = "1") int userId) {
        return relationshipService.getRelationshipsByUserId(userId);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RelationshipIdDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "senderId": 1,
                                      "receiverId": 2
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "Заявка в друзья пользователю Alex отправлена")))
    @Operation(summary = "Отправить заявку в друзья от одного пользователя(senderId) к другому(receiverId)")
    @PostMapping("/send_request")
    public String sendRequestFriend(@Valid @RequestBody RelationshipIdDto relationshipIdDto) {
        return relationshipService.sendRequestFriend(relationshipIdDto);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RelationshipIdDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "senderId": 1,
                                      "receiverId": 2
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "Теперь пользователь Ivan у вас в друзьях")))
    @Operation(summary = "Принять заявку в друзья от пользователя(senderId)")
    @PostMapping("/accept_request")
    public String acceptRequestFriend(@Valid @RequestBody RelationshipIdDto relationshipIdDto) {
        return relationshipService.acceptRequestFriend(relationshipIdDto);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RelationshipIdDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "senderId": 1,
                                      "receiverId": 3
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "Вы отклонили заявку в друзья от пользователя Ivan")))
    @Operation(summary = "Отклонить заявку в друзья от пользователя(senderId)")
    @PostMapping("/reject_request")
    public String rejectRequestFriend(@Valid @RequestBody RelationshipIdDto relationshipIdDto) {
        return relationshipService.rejectRequestFriend(relationshipIdDto);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RelationshipIdDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "senderId": 1,
                                      "receiverId": 3
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "Вы отписались от пользователя Vladimir")))
    @Operation(summary = "Отписаться от пользователя(receiverId)")
    @DeleteMapping("/unsubscribe")
    public String unsubscribe(@Valid @RequestBody RelationshipIdDto relationshipIdDto) {
        return relationshipService.unsubscribe(relationshipIdDto);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RelationshipIdDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "senderId": 2,
                                      "receiverId": 1
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "Вы удалили пользователя Ivan из друзей")))
    @Operation(summary = "Удалить пользователя из друзей")
    @DeleteMapping("/delete_friend")
    public String deleteFriend(@Valid @RequestBody RelationshipIdDto relationshipIdDto) {
        return relationshipService.deleteFriend(relationshipIdDto);
    }
}
