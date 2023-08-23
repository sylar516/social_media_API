package ru.effective_mobile.social_media_api.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effective_mobile.social_media_api.dto.RelationshipDto;
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
    public String sendRequestFriend(RelationshipDto relationshipDto) {
        Relationship relationship = dtoToRelationship(relationshipDto);
        relationshipRepository.save(relationship);
        return String.format("Заявка в друзья пользователю %s отправлена", relationship.getRelationshipId().getUser2().getName());
    }

    @Override
    @Transactional
    public String acceptRequestFriend(RelationshipDto relationshipDto) {
        Relationship relationship1 = dtoToRelationship(relationshipDto);
        relationshipRepository.save(relationship1);

        RelationshipDto reverseRelationshipDto = reverseIdInRelationshipDto(relationshipDto);
        Relationship relationship2 = dtoToRelationship(reverseRelationshipDto);
        relationshipRepository.save(relationship2);

        return String.format("Теперь пользователь %s у вас в друзьях", relationship1.getRelationshipId().getUser1().getName());
    }

    @Override
    @Transactional
    public String rejectRequestFriend(RelationshipDto relationshipDto) {
        Relationship relationship = dtoToRelationship(relationshipDto);
        relationshipRepository.save(relationship);
        return String.format("Вы отклонили заявку в друзья от пользователя %s", relationship.getRelationshipId().getUser1().getName());
    }

    @Override
    @Transactional
    public String unsubscribe(RelationshipDto relationshipDto) {
        Relationship relationship = dtoToRelationship(relationshipDto);
        relationshipRepository.delete(relationship);
        User receiver = userRepository.findById(relationshipDto.getReceiverId()).get();
        return String.format("Вы отписались от пользователя %s", receiver.getName());
    }

    @Override
    @Transactional
    public String deleteFriend(RelationshipDto relationshipDto) {
        Relationship relationship1 = dtoToRelationship(relationshipDto);
        relationshipRepository.delete(relationship1);

        RelationshipDto reverseRelationshipDto = reverseIdInRelationshipDto(relationshipDto);
        Relationship relationship2 = dtoToRelationship(reverseRelationshipDto);
        relationship2.setStatus(Status.SUBSCRIBER);
        relationshipRepository.save(relationship2);

        User receiver = userRepository.findById(relationshipDto.getReceiverId()).get();

        return String.format("Вы удалили пользователя %s из друзей", receiver.getName());
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

    private RelationshipDto relationshipToDto(Relationship relationship) {
        return RelationshipDto.builder()
                .setSenderId(relationship.getRelationshipId().getUser1().getId())
                .setReceiverId(relationship.getRelationshipId().getUser2().getId())
                .setStatus(relationship.getStatus())
                .build();
    }

    private Relationship dtoToRelationship(RelationshipDto relationshipDto) {
        Relationship relationship = new Relationship();
        User sender = userRepository.findById(relationshipDto.getSenderId()).get();
        User receiver = userRepository.findById(relationshipDto.getReceiverId()).get();

        RelationshipId relationshipId = new RelationshipId(sender, receiver);

        relationship.setRelationshipId(relationshipId);
        relationship.setStatus(relationshipDto.getStatus());

        return relationship;
    }

    private RelationshipDto reverseIdInRelationshipDto(RelationshipDto relationshipDto) {
        RelationshipDto reverseRelationshipDto = new RelationshipDto();
        reverseRelationshipDto.setSenderId(relationshipDto.getReceiverId());
        reverseRelationshipDto.setReceiverId(relationshipDto.getSenderId());
        reverseRelationshipDto.setStatus(relationshipDto.getStatus());
        return reverseRelationshipDto;
    }
}
