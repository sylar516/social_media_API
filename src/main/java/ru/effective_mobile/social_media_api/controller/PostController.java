package ru.effective_mobile.social_media_api.controller;

import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.social_media_api.dto.PostDto;
import ru.effective_mobile.social_media_api.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") int id) {
        return postService.getPostById(id);
    }

    @GetMapping("/user/{user_id}")
    public List<PostDto> getAllPostsByUser(@PathVariable("user_id") int userId) {
        return postService.getAllPostsByUser(userId);
    }

//    @PostMapping("/upload_image")
//    public String uploadImage(@RequestParam("file") MultipartFile file) {
//        System.out.println("В консоль вывелась информация из метода по загрузке изображения");
//        return "Отработал метод загрузки изображения";
//    }

    @PostMapping("/create")
    public Integer createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @PostMapping("/update/{id}")
    public void updatePost(@PathVariable("id") Integer id, @RequestBody PostDto postDto) {
        postService.updatePost(id, postDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePostById(@PathVariable Integer id) {
        postService.deletePostById(id);
    }
}
