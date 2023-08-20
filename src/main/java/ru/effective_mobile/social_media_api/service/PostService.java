package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.PostDto;
import ru.effective_mobile.social_media_api.entity.Post;
import ru.effective_mobile.social_media_api.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    static List<PostDto> postListToDto(List<Post> posts) {
        List<PostDto> postDtos = new ArrayList<>();
        posts.forEach(
                post -> {
                    PostDto postDto = PostDto.builder()
                            .setHeader(post.getHeader())
                            .setText(post.getText())
                            .setImage(post.getImage())
                            .setCreateDate(post.getCreateDate())
                            .setUpdateDate(post.getUpdateDate())
                            .setUserId(post.getUser().getId())
                            .build();
                    postDtos.add(postDto);
                }
        );
        return postDtos;
    }

    private PostDto postToDto(Post post) {
        return PostDto.builder()
                .setHeader(post.getHeader())
                .setText(post.getText())
                .setImage(post.getImage())
                .setCreateDate(post.getCreateDate())
                .setUpdateDate(post.getUpdateDate())
                .setUserId(post.getUser().getId())
                .build();
    }
}
