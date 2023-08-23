package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.RelationshipDto;

import java.util.List;

public interface RelationshipService {
    List<RelationshipDto> getAllRelationships();
    List<RelationshipDto> getRelationshipsByUserId(int userId);
    String sendRequestFriend(RelationshipDto relationshipDto);
    String acceptRequestFriend(RelationshipDto relationshipDto);
    String rejectRequestFriend(RelationshipDto relationshipDto);
    String deleteFriend(RelationshipDto relationshipDto);
    String unsubscribe(RelationshipDto relationshipDto);
}
