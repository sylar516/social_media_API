package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto getPostById(int id);
    List<PostDto> getAllPostsByUser(int userId);
    Integer createPost(PostDto postDto);
    void updatePost(Integer id, PostDto postDto);
    void deletePostById(Integer id);
}
