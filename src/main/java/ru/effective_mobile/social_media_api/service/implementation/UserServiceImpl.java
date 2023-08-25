package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.PostDto;
import ru.effective_mobile.social_media_api.dto.RelationshipDto;
import ru.effective_mobile.social_media_api.dto.UserDto;
import ru.effective_mobile.social_media_api.dto.UserDataDto;
import ru.effective_mobile.social_media_api.entity.Post;
import ru.effective_mobile.social_media_api.entity.Relationship;
import ru.effective_mobile.social_media_api.entity.RelationshipId;
import ru.effective_mobile.social_media_api.entity.User;
import ru.effective_mobile.social_media_api.exception.NotFoundException;
import ru.effective_mobile.social_media_api.repository.MessageRepository;
import ru.effective_mobile.social_media_api.repository.RelationshipRepository;
import ru.effective_mobile.social_media_api.repository.UserRepository;
import ru.effective_mobile.social_media_api.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    private RelationshipRepository relationshipRepository;

    public UserServiceImpl(UserRepository userRepository, MessageRepository messageRepository, RelationshipRepository relationshipRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.relationshipRepository = relationshipRepository;
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        return userListToDto(userRepository.findAll());
    }

    @Override
    @Transactional
    public UserDto getUserById(int id) {
        return userToDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(id))));
    }

    @Override
    @Transactional
    public Integer createUser(UserDataDto userDataDto) {
        User user = dtoToUser(userDataDto);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserDataDto userDataDto) {
        User user = dtoToUser(id, userDataDto);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(id)));
        messageRepository.deleteMessagesBySender(id);
        relationshipRepository.deleteRelationshipsByUser(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        messageRepository.deleteAll();
        relationshipRepository.deleteAll();
        userRepository.deleteAll();
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

    private List<Post> dtoListToPost(List<PostDto> postDtos) {
        List<Post> posts = new ArrayList<>();
        if (isNull(postDtos)) {
            return posts;
        } else {
            postDtos.forEach(
                    postDto -> {
                        Post post = new Post();
                        post.setHeader(postDto.getHeader());
                        post.setText(postDto.getText());
                        post.setImage(postDto.getImage());
                        post.setCreateDate(postDto.getCreateDate());
                        post.setUpdateDate(postDto.getUpdateDate());
                        User user = userRepository.findById(postDto.getUserId()).get();
                        post.setUser(user);
                        posts.add(post);
                    }
            );
            return posts;
        }
    }

    private List<RelationshipDto> relationshipListToDto(List<Relationship> relationships) {
        List<RelationshipDto> relationshipDtos = new ArrayList<>();
        relationships.forEach(
                relationship -> {
                    RelationshipDto relationshipDto = RelationshipDto.builder()
                            .setSenderId(relationship.getRelationshipId().getUser1().getId())
                            .setReceiverId(relationship.getRelationshipId().getUser2().getId())
                            .setStatus(relationship.getStatus())
                            .build();
                    relationshipDtos.add(relationshipDto);
                }
        );
        return relationshipDtos;
    }

    private List<Relationship> dtoListToRelationship(List<RelationshipDto> relationshipDtos) {
        List<Relationship> relationships = new ArrayList<>();
        if (isNull(relationshipDtos)) {
            return relationships;
        } else {
            relationshipDtos.forEach(
                    relationshipDto -> {
                        Relationship relationship = new Relationship();
                        User user1 = userRepository.findById(relationshipDto.getSenderId()).get();
                        User user2 = userRepository.findById(relationshipDto.getReceiverId()).get();
                        RelationshipId relationshipId = new RelationshipId(user1, user2);

                        relationship.setRelationshipId(relationshipId);
                        relationship.setStatus(relationshipDto.getStatus());
                        relationships.add(relationship);
                    }
            );
            return relationships;
        }
    }

    private List<UserDto> userListToDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(
                user -> {
                    UserDto userDto = UserDto.builder()
                            .setName(user.getName())
                            .setEmail(user.getEmail())
                            .setPosts(postListToDto(user.getPosts()))
                            .setRelationships(relationshipListToDto(user.getRelationships()))
                            .build();
                    userDtos.add(userDto);
                }
        );
        return userDtos;
    }

    private UserDto userToDto(User user) {
        return UserDto.builder()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPosts(postListToDto(user.getPosts()))
                .setRelationships(relationshipListToDto(user.getRelationships()))
                .build();
    }

    private User dtoToUser(UserDataDto userDataDto) {
        User user = new User();
        user.setName(userDataDto.getName());
        user.setEmail(userDataDto.getEmail());
//        user.setPosts(dtoListToPost(userDto.getPosts()));
//        user.setRelationships(dtoListToRelationship(userDto.getRelationships()));

        return user;
    }

    private User dtoToUser(int id, UserDataDto userDataDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(id)));
        user.setName(userDataDto.getName());
        user.setEmail(userDataDto.getEmail());

        return user;
    }
}
