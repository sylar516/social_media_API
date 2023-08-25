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
import ru.effective_mobile.social_media_api.dto.UserDataDto;
import ru.effective_mobile.social_media_api.dto.UserDto;
import ru.effective_mobile.social_media_api.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "user-controller", description = "контроллер для взаимодействия с сущностью \"пользователь\"")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = UserDto.class),
                    mediaType = "application/json",
                    examples = @ExampleObject(name = "Users",value = """
                            [
                              {
                                "name": "Ivan",
                                "email": "ivan@yandex.ru",
                                "posts": [
                                  {
                                    "header": "первый пост от Ивана",
                                    "text": "интересный контент",
                                    "image": null,
                                    "createDate": "2023-08-23T11:59:19",
                                    "updateDate": "2023-08-23T11:59:19",
                                    "userId": 1
                                  },
                                  {
                                    "header": "второй пост от Ивана",
                                    "text": "ещё более интересный контент",
                                    "image": null,
                                    "createDate": "2023-08-23T11:59:36",
                                    "updateDate": "2023-08-23T11:59:36",
                                    "userId": 1
                                  }
                                ],
                                "relationships": [
                                  {
                                    "senderId": 1,
                                    "receiverId": 2,
                                    "status": "FRIEND_REQUEST"
                                  },
                                  {
                                    "senderId": 1,
                                    "receiverId": 3,
                                    "status": "SUBSCRIBER"
                                  }
                                ]
                              },
                              {
                                "name": "Alex",
                                "email": "alex@gmail.com",
                                "posts": [],
                                "relationships": [
                                  {
                                    "senderId": 2,
                                    "receiverId": 3,
                                    "status": "FRIEND"
                                  }
                                ]
                              },
                              {
                                "name": "Nikita",
                                "email": "nikita@mail.ru",
                                "posts": [
                                  {
                                    "header": "первый пост от Никиты",
                                    "text": "захватывающий контент",
                                    "image": null,
                                    "createDate": "2023-08-23T11:59:59",
                                    "updateDate": "2023-08-23T11:59:59",
                                    "userId": 3
                                  }
                                ],
                                "relationships": []
                              },
                              {
                                "name": "Anton",
                                "email": "anton@rambler.ru",
                                "posts": [],
                                "relationships": []
                              }
                            ]
                                                                    """)))
    @Operation(summary = "Получить всех пользователей")
    @GetMapping("/")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = UserDto.class),
                    mediaType = "application/json",
                    examples = @ExampleObject(name = "User",value = """
                            {
                              "name": "Nikita",
                              "email": "nikita@mail.ru",
                              "posts": [
                                {
                                  "header": "первый пост от Никиты",
                                  "text": "захватывающий контент",
                                  "image": null,
                                  "createDate": "2023-08-23T11:59:59",
                                  "updateDate": "2023-08-23T11:59:59",
                                  "userId": 3
                                }
                              ],
                              "relationships": []
                            }
                            """)))
    @Operation(summary = "Получить пользователя с указанным id")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") @Parameter(name = "id", description = "User ID", example = "3") int id) {
        return userService.getUserById(id);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "name": "Anton",
                                      "email": "anton@rambler.ru"
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = Integer.class),
                    examples = @ExampleObject(name = "User id",value = "4")))
    @Operation(summary = "Создать пользователя")
    @PostMapping("/create")
    public Integer createUser(@Valid @RequestBody UserDataDto userDataDto) {
        return userService.createUser(userDataDto);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "name": "Nikita",
                                      "email": "nikita@mail.ru"
                                    }
                                    """)
            )
    )
    @Operation(summary = "Изменить данные пользователя с указанным id")
    @PostMapping("/update/{id}")
    public void updateUser(@PathVariable("id") @Parameter(name = "id", description = "User ID", example = "3") Integer id, @Valid @RequestBody UserDataDto userDataDto) {
        userService.updateUser(id, userDataDto);
    }

    @Operation(summary = "Удалить пользователя по id")
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable("id") @Parameter(name = "id", description = "User ID", example = "2") Integer id) {
        userService.deleteUserById(id);
    }

    @Operation(summary = "Удалить всех пользователей")
    @DeleteMapping("/delete_all")
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }
}
