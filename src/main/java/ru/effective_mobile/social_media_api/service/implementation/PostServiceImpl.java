package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.PostDto;
import ru.effective_mobile.social_media_api.entity.Post;
import ru.effective_mobile.social_media_api.entity.User;
import ru.effective_mobile.social_media_api.repository.PostRepository;
import ru.effective_mobile.social_media_api.repository.RelationshipRepository;
import ru.effective_mobile.social_media_api.repository.UserRepository;
import ru.effective_mobile.social_media_api.service.PostService;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    private UserRepository userRepository;

    private RelationshipRepository relationshipRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, RelationshipRepository relationshipRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.relationshipRepository = relationshipRepository;
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
        return postListToDto(postRepository.getAllPostsByUserId(userId));
    }

    @Override
    @Transactional
    public List<PostDto> getUserFeed(int userId, int page, int limit) {
        List<Integer> userIds = relationshipRepository.findUserIdsBySubscriber(userId);
        List<Post> postsByUserIds = postRepository.getPostsByUserIds(userIds);
        List<Post> feedPosts = new ArrayList<>();
        for (Integer userId1 : userIds) {
            List<Post> postsByUserId = postsByUserIds.stream().filter(post -> post.getUser().getId().equals(userId1)).toList();
            postsByUserId.stream().max(Comparator.comparing(Post::getCreateDate)).ifPresent(feedPosts::add);
        }

        feedPosts.sort((post1, post2) -> post2.getCreateDate().compareTo(post1.getCreateDate()));
        int offset = (page - 1) * limit;
        List<Post> feedPostsLimit = feedPosts.stream().skip(offset).limit(limit).toList();

        return postListToDto(feedPostsLimit);
    }

    @Override
    @Transactional
    public Integer publishPost(PostDto postDto) {
        Post post = dtoToPost(postDto);
        postRepository.save(post);
        return post.getId();
    }

    @Override
    @Transactional
    public void editPost(Integer id, PostDto postDto) {
        Post post = dtoToPost(postDto);
        post.setId(id);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePostById(Integer id) {
        postRepository.deleteById(id);
    }

    private List<PostDto> postListToDto(List<Post> posts) {
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
