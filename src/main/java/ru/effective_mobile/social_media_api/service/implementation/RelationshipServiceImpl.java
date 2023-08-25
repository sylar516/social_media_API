package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.Action;
import ru.effective_mobile.social_media_api.dto.RelationshipDto;
import ru.effective_mobile.social_media_api.dto.RelationshipIdDto;
import ru.effective_mobile.social_media_api.entity.Relationship;
import ru.effective_mobile.social_media_api.entity.RelationshipId;
import ru.effective_mobile.social_media_api.entity.Status;
import ru.effective_mobile.social_media_api.entity.User;
import ru.effective_mobile.social_media_api.exception.NotFoundException;
import ru.effective_mobile.social_media_api.exception.RelationshipAlreadyExistException;
import ru.effective_mobile.social_media_api.repository.RelationshipRepository;
import ru.effective_mobile.social_media_api.repository.UserRepository;
import ru.effective_mobile.social_media_api.service.RelationshipService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {
    private RelationshipRepository relationshipRepository;

    private UserRepository userRepository;

    public RelationshipServiceImpl(RelationshipRepository relationshipRepository, UserRepository userRepository) {
        this.relationshipRepository = relationshipRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<RelationshipDto> getAllRelationships() {
        return relationshipListToDto(relationshipRepository.findAll());
    }

    @Override
    @Transactional
    public List<RelationshipDto> getRelationshipsByUserId(int userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("user with id = %d not found".formatted(userId)));
        return relationshipListToDto(relationshipRepository.findRelationshipsByUserId(userId));
    }

    @Override
    @Transactional
    public String sendRequestFriend(RelationshipIdDto relationshipIdDto) {
        checkRelationshipIdDto(relationshipIdDto, Action.SEND_REQUEST);

        Relationship relationship = dtoToRelationship(relationshipIdDto, Status.FRIEND_REQUEST);
        relationshipRepository.save(relationship);
        return String.format("Заявка в друзья пользователю %s отправлена", relationship.getRelationshipId().getUser2().getName());
    }

    @Override
    @Transactional
    public String acceptRequestFriend(RelationshipIdDto relationshipIdDto) {
        checkRelationshipIdDto(relationshipIdDto, Action.ACCEPT_REQUEST);
        Status status = Status.FRIENDS;

        Relationship relationship1 = dtoToRelationship(relationshipIdDto, status);
        relationshipRepository.save(relationship1);

        RelationshipIdDto reverseRelationshipIdDto = reverseIdInRelationshipIdDto(relationshipIdDto);
        Relationship relationship2 = dtoToRelationship(reverseRelationshipIdDto, status);
        relationshipRepository.save(relationship2);

        return String.format("Теперь пользователь %s у вас в друзьях", relationship1.getRelationshipId().getUser1().getName());
    }

    @Override
    @Transactional
    public String rejectRequestFriend(RelationshipIdDto relationshipIdDto) {
        checkRelationshipIdDto(relationshipIdDto, Action.REJECT_REQUEST);

        Relationship relationship = dtoToRelationship(relationshipIdDto, Status.SUBSCRIBER);
        relationshipRepository.save(relationship);
        return String.format("Вы отклонили заявку в друзья от пользователя %s", relationship.getRelationshipId().getUser1().getName());
    }

    @Override
    @Transactional
    public String unsubscribe(RelationshipIdDto relationshipIdDto) {
        checkRelationshipIdDto(relationshipIdDto, Action.UNSUBSCRIBE);

        relationshipRepository.deleteRelationshipsByRelationshipId(relationshipIdDto.getSenderId(), relationshipIdDto.getReceiverId());

        User receiver = userRepository.findById(relationshipIdDto.getReceiverId()).get();
        return String.format("Вы отписались от пользователя %s", receiver.getName());
    }

    @Override
    @Transactional
    public String deleteFriend(RelationshipIdDto relationshipIdDto) {
        checkRelationshipIdDto(relationshipIdDto, Action.DELETE_FRIEND);

        relationshipRepository.deleteRelationshipsByRelationshipId(relationshipIdDto.getSenderId(), relationshipIdDto.getReceiverId());

        RelationshipIdDto reverseRelationshipIdDto = reverseIdInRelationshipIdDto(relationshipIdDto);
        Relationship relationship2 = dtoToRelationship(reverseRelationshipIdDto, Status.SUBSCRIBER);
        relationshipRepository.save(relationship2);

        User receiver = userRepository.findById(relationshipIdDto.getReceiverId()).get();

        return String.format("Вы удалили пользователя %s из друзей", receiver.getName());
    }

    private void checkRelationshipIdDto(RelationshipIdDto relationshipIdDto, Action action) {
        int senderId = relationshipIdDto.getSenderId();
        int receiverId = relationshipIdDto.getReceiverId();

        userRepository.findById(senderId).orElseThrow(()
                -> new NotFoundException("user with id = %d not found".formatted(relationshipIdDto.getSenderId())));
        userRepository.findById(receiverId).orElseThrow(()
                -> new NotFoundException("user with id = %d not found".formatted(relationshipIdDto.getReceiverId())));

        switch (action) {
            case SEND_REQUEST -> {
                List<Relationship> relationshipsByUsers = relationshipRepository.findRelationshipsByUsers(senderId, receiverId);
                if (relationshipsByUsers.size() > 0) {
                    throw new RelationshipAlreadyExistException("relationships between users with id = %d and id = %d already exist"
                            .formatted(senderId, receiverId));
                }
            }

            case ACCEPT_REQUEST, REJECT_REQUEST -> {
                relationshipRepository.findRelationshipByRelationshipId(senderId, receiverId, Status.FRIEND_REQUEST)
                        .orElseThrow(() -> new NotFoundException("friend request from user with id = %d to user with id = %d not found"
                                .formatted(senderId, receiverId)));
            }

            case UNSUBSCRIBE -> {
                relationshipRepository.findRelationshipByRelationshipId(senderId, receiverId, Status.SUBSCRIBER)
                        .orElseThrow(() -> new NotFoundException("subscription from user with id = %d to user with id = %d not found"
                                .formatted(senderId, receiverId)));
            }

            case DELETE_FRIEND -> {
                relationshipRepository.findRelationshipByRelationshipId(senderId, receiverId, Status.FRIENDS)
                        .orElseThrow(() -> new NotFoundException("user with id = %d is not a friend of user with id = %d"
                                .formatted(senderId, receiverId)));

                relationshipRepository.findRelationshipByRelationshipId(receiverId, senderId, Status.FRIENDS)
                        .orElseThrow(() -> new NotFoundException("user with id = %d is not a friend of user with id = %d"
                                .formatted(receiverId, senderId)));
            }
        }
    }

    private Relationship dtoToRelationship(RelationshipIdDto relationshipIdDto, Status status) {
        Relationship relationship = new Relationship();
        User sender = userRepository.findById(relationshipIdDto.getSenderId()).get();
        User receiver = userRepository.findById(relationshipIdDto.getReceiverId()).get();

        RelationshipId relationshipId = new RelationshipId(sender, receiver);

        relationship.setRelationshipId(relationshipId);
        relationship.setStatus(status);

        return relationship;
    }

    private RelationshipIdDto reverseIdInRelationshipIdDto(RelationshipIdDto relationshipIdDto) {
        RelationshipIdDto reverseRelationshipIdDto = new RelationshipIdDto();
        reverseRelationshipIdDto.setSenderId(relationshipIdDto.getReceiverId());
        reverseRelationshipIdDto.setReceiverId(relationshipIdDto.getSenderId());
        return reverseRelationshipIdDto;
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
}
