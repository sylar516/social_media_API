package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto getPostById(int id);
    List<PostDto> getAllPostsByUser(int userId);
    List<PostDto> getUserFeed(int userId, int page, int limit);
    Integer publishPost(PostDto postDto);
    void editPost(Integer id, PostDto postDto);
    void deletePostById(Integer id);
}
