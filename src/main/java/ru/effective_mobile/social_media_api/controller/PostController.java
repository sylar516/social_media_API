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
import ru.effective_mobile.social_media_api.dto.MessageDto;
import ru.effective_mobile.social_media_api.dto.PostDto;
import ru.effective_mobile.social_media_api.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(name = "post-controller", description = "контроллер для взаимодействия с сущностью \"пост\"")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = PostDto.class),
                    examples = @ExampleObject(name = "Posts",value = """
                            [
                               {
                                 "header": "первый пост от Ивана",
                                 "text": "интересный контент",
                                 "image": null,
                                 "createDate": "2023-08-23T11:59:19",
                                 "updateDate": "2023-08-23T11:59:19",
                                 "userId": 2
                               },
                               {
                                 "header": "второй пост от Ивана",
                                 "text": "ещё более интересный контент",
                                 "image": null,
                                 "createDate": "2023-08-23T11:59:36",
                                 "updateDate": "2023-08-23T11:59:36",
                                 "userId": 1
                               },
                               {
                                 "header": "первый пост от Никиты",
                                 "text": "захватывающий контент",
                                 "image": null,
                                 "createDate": "2023-08-23T11:59:59",
                                 "updateDate": "2023-08-23T11:59:59",
                                 "userId": 3
                               },
                               {
                                 "header": "пост через сваггер",
                                 "text": "есть ли жизнь без сваггера?",
                                 "image": null,
                                 "createDate": "2023-08-24T00:31:07",
                                 "updateDate": "2023-08-24T00:31:07",
                                 "userId": 1
                               },
                               {
                                 "header": "ГВД",
                                 "text": "день азарта, возможность поднять золота или всё проиграть?",
                                 "image": null,
                                 "createDate": "2023-08-24T00:31:56",
                                 "updateDate": "2023-08-24T00:31:56",
                                 "userId": 3
                               }
                            ]
                            """)))
    @Operation(summary = "Получить все посты")
    @GetMapping("/")
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = PostDto.class),
                    examples = @ExampleObject(name = "Post",value = """
                            {
                              "header": "ГВД",
                              "text": "день азарта, возможность поднять золота или всё проиграть?",
                              "image": null,
                              "createDate": "2023-08-24T00:31:56",
                              "updateDate": "2023-08-24T00:31:56",
                              "userId": 3
                            }
                            """)))
    @Operation(summary = "Получить пост с указанным id")
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") @Parameter(name = "id", description = "Post ID", example = "1") int id) {
        return postService.getPostById(id);
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = PostDto.class),
                    examples = @ExampleObject(name = "Posts",value = """
                            
                              {
                                "header": "text1",
                                "text": "text1",
                                "image": null,
                                "createDate": "2023-08-24T02:28:33",
                                "updateDate": "2023-08-24T02:28:33",
                                "userId": 5
                              },
                              {
                                "header": "text2",
                                "text": "text2",
                                "image": null,
                                "createDate": "2023-08-24T02:28:39",
                                "updateDate": "2023-08-24T02:28:39",
                                "userId": 5
                              },
                              {
                                "header": "text3",
                                "text": "text3",
                                "image": null,
                                "createDate": "2023-08-24T02:28:45",
                                "updateDate": "2023-08-24T02:28:45",
                                "userId": 5
                              }
                            ]
                            """)))
    @Operation(summary = "Получить все посты пользователя с указанным id")
    @GetMapping("/user/{user_id}")
    public List<PostDto> getAllPostsByUser(@PathVariable("user_id") @Parameter(name = "user_id", description = "User ID", example = "5") int userId) {
        return postService.getAllPostsByUser(userId);
    }

    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = PostDto.class),
                    examples = @ExampleObject(name = "Feed",value = """
                            [
                              {
                                "header": "кошаки6",
                                "text": "кошули6",
                                "image": null,
                                "createDate": "2023-08-24T02:30:31",
                                "updateDate": "2023-08-24T02:30:31",
                                "userId": 7
                              },
                              {
                                "header": "postik5",
                                "text": "postik5",
                                "image": null,
                                "createDate": "2023-08-24T02:29:32",
                                "updateDate": "2023-08-24T02:29:32",
                                "userId": 6
                              },
                              {
                                "header": "ГВД 2",
                                "text": "а вот и я, не ждали",
                                "image": null,
                                "createDate": "2023-08-24T02:29:10",
                                "updateDate": "2023-08-24T00:32:10",
                                "userId": 3
                              },
                              {
                                "header": "СВО",
                                "text": "событие, о котором не возможно молчать",
                                "image": null,
                                "createDate": "2023-08-24T01:50:41",
                                "updateDate": "2023-08-24T01:13:39",
                                "userId": 2
                              }
                            ]
                            """)))
    @Operation(summary = "Получить ленту активности пользователя с указанным id")
    @GetMapping("/feed/{user_id}")
    public List<PostDto> getUserFeed(@PathVariable("user_id") @Parameter(name = "user_id", description = "User ID", example = "1") int userId,
                                     @RequestParam(required = false, defaultValue = "1") int page,
                                     @RequestParam(required = false, defaultValue = "5") int limit) {
        return postService.getUserFeed(userId, page, limit);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "header": "публикация №1",
                                      "text": "очень интересная публикация",
                                      "userId": 1
                                    }
                                    """)
            )
    )
    @ApiResponse(responseCode = "200", description = "ОК",
            content = @Content(
                    schema = @Schema(implementation = Integer.class),
                    examples = @ExampleObject(name = "Post id",value = "10")))
    @Operation(summary = "Опубликовать пост")
    @PostMapping("/publish")
    public Integer publishPost(@Valid @RequestBody PostDto postDto) {
        return postService.publishPost(postDto);
    }

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PostDto.class),
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "header": "публикация №1 c изменениями",
                                      "text": "ещё более интересная публикация",
                                      "userId": 1
                                    }
                                    """)
            )
    )
    @Operation(summary = "Редактировать пост")
    @PostMapping("/edit/{id}")
    public void editPost(@PathVariable("id") @Parameter(name = "id", description = "Post ID", example = "25") Integer id, @Valid @RequestBody PostDto postDto) {
        postService.editPost(id, postDto);
    }

    @Operation(summary = "Удалить пост с указанным id")
    @DeleteMapping("/delete/{id}")
    public void deletePostById(@PathVariable("id") @Parameter(name = "id", description = "Post ID", example = "10") Integer id) {
        postService.deletePostById(id);
    }
}
