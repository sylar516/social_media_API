package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.RelationshipDto;
import ru.effective_mobile.social_media_api.dto.RequestParamsDto;
import ru.effective_mobile.social_media_api.entity.Relationship;
import ru.effective_mobile.social_media_api.entity.RelationshipId;
import ru.effective_mobile.social_media_api.entity.Status;
import ru.effective_mobile.social_media_api.entity.User;
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
        return relationshipListToDto(relationshipRepository.findRelationshipsByUserId(userId));
    }

    @Override
    @Transactional
    public String sendRequestFriend(RequestParamsDto requestParamsDto) {
        Relationship relationship = dtoToRelationship(requestParamsDto, Status.FRIEND_REQUEST);
        relationshipRepository.save(relationship);
        return String.format("Заявка в друзья пользователю %s отправлена", relationship.getRelationshipId().getUser2().getName());
    }

    @Override
    @Transactional
    public String acceptRequestFriend(RequestParamsDto requestParamsDto) {
        Relationship relationship = dtoToRelationship(requestParamsDto, Status.FRIEND);
        relationshipRepository.save(relationship);
        return String.format("Теперь пользователь %s у вас в друзьях", relationship.getRelationshipId().getUser1().getName());
    }

    @Override
    @Transactional
    public String rejectRequestFriend(RequestParamsDto requestParamsDto) {
        Relationship relationship = dtoToRelationship(requestParamsDto, Status.SUBSCRIBER);
        relationshipRepository.save(relationship);
        return String.format("Вы отклонили заявку в друзья от пользователя %s", relationship.getRelationshipId().getUser1().getName());
    }

    @Override
    @Transactional
    public String deleteFriend(RequestParamsDto requestParamsDto) {
        Relationship relationshipByUsers = relationshipRepository.findRelationshipByUsers(requestParamsDto.getSenderId(), requestParamsDto.getReceiverId());
        Integer user1Id = relationshipByUsers.getRelationshipId().getUser1().getId();
        String deletedUserName;
        if (user1Id.equals(requestParamsDto.getSenderId())) {
            RelationshipId relationshipId = relationshipByUsers.getRelationshipId();
            User user1 = relationshipId.getUser1();
            User user2 = relationshipId.getUser2();

            relationshipRepository.deleteById(relationshipId);

            RelationshipId newRelationshipId = new RelationshipId(user2, user1);
            Relationship newRelationShip = new Relationship(newRelationshipId, Status.SUBSCRIBER);

            relationshipRepository.save(newRelationShip);
            deletedUserName = relationshipByUsers.getRelationshipId().getUser2().getName();
        } else {
            relationshipByUsers.setStatus(Status.SUBSCRIBER);
            relationshipRepository.save(relationshipByUsers);
            deletedUserName = relationshipByUsers.getRelationshipId().getUser1().getName();
        }
        return String.format("Вы удалили пользователя %s из друзей", deletedUserName);
    }

    @Override
    @Transactional
    public String unsubscribe(RequestParamsDto requestParamsDto) {
        User user1 = userRepository.findById(requestParamsDto.getSenderId()).get();
        User user2 = userRepository.findById(requestParamsDto.getReceiverId()).get();
        RelationshipId relationshipId = new RelationshipId(user1, user2);
        relationshipRepository.deleteById(relationshipId);
        return String.format("Вы отписались от пользователя %s", user2.getName());
    }

    static List<RelationshipDto> relationshipListToDto(List<Relationship> relationships) {
        List<RelationshipDto> relationshipDtos = new ArrayList<>();
        relationships.forEach(
                relationship -> {
                    RelationshipDto relationshipDto = RelationshipDto.builder()
                            .setUser1Id(relationship.getRelationshipId().getUser1().getId())
                            .setUser2Id(relationship.getRelationshipId().getUser2().getId())
                            .setStatus(relationship.getStatus())
                            .build();
                    relationshipDtos.add(relationshipDto);
                }
        );
        return relationshipDtos;
    }

    private RelationshipDto relationshipToDto(Relationship relationship) {
        return RelationshipDto.builder()
                .setUser1Id(relationship.getRelationshipId().getUser1().getId())
                .setUser2Id(relationship.getRelationshipId().getUser2().getId())
                .setStatus(relationship.getStatus())
                .build();
    }

    private Relationship dtoToRelationship(RelationshipDto relationshipDto) {
        Relationship relationship = new Relationship();
        User user = userRepository.findById(relationshipDto.getUser1Id()).get();
        User userFriend = userRepository.findById(relationshipDto.getUser2Id()).get();
        RelationshipId relationshipId = new RelationshipId(user, userFriend);

        relationship.setRelationshipId(relationshipId);
        relationship.setStatus(relationshipDto.getStatus());

        return relationship;
    }

    private Relationship dtoToRelationship(RequestParamsDto requestParamsDto, Status status) {
        Relationship relationship = new Relationship();
        User user = userRepository.findById(requestParamsDto.getSenderId()).get();
        User userFriend = userRepository.findById(requestParamsDto.getReceiverId()).get();
        RelationshipId relationshipId = new RelationshipId(user, userFriend);

        relationship.setRelationshipId(relationshipId);
        relationship.setStatus(status);

        return relationship;
    }
}
