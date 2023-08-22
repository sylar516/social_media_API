package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.effective_mobile.social_media_api.dto.PostDto;
import ru.effective_mobile.social_media_api.entity.Post;
import ru.effective_mobile.social_media_api.entity.User;
import ru.effective_mobile.social_media_api.repository.PostRepository;
import ru.effective_mobile.social_media_api.repository.UserRepository;
import ru.effective_mobile.social_media_api.service.PostService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<PostDto> getAllPosts() {
        return postListToDto(postRepository.findAll());
    }

    @Override
    @Transactional
    public PostDto getPostById(int id) {
        return postToDto(postRepository.findById(id).get());
    }

    @Override
    @Transactional
    public List<PostDto> getAllPostsByUser(int userId) {
        return postListToDto(postRepository.getAllByUserId(userId));
    }

    @Override
    @Transactional
    public Integer createPost(PostDto postDto) {
        Post post = dtoToPost(postDto);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional
    public void updatePost(Integer id, PostDto postDto) {
        Post post = postRepository.findById(id).get();
        if (!isNull(postDto.getHeader())) post.setHeader(postDto.getHeader());
        if (!isNull(postDto.getText())) post.setText(postDto.getText());
        if (!isNull(postDto.getImage())) post.setImage(postDto.getImage());
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePostById(Integer id) {
        postRepository.deleteById(id);
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

    private Post dtoToPost(PostDto postDto) {
        Post post = new Post();
        User user = userRepository.findById(postDto.getUserId()).get();

        post.setHeader(postDto.getHeader());
        post.setText(postDto.getText());
        post.setImage(postDto.getImage());
        post.setCreateDate(LocalDateTime.now());
        post.setUser(user);

        return post;
    }
}
