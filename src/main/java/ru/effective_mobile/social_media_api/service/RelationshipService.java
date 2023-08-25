package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.RelationshipDto;
import ru.effective_mobile.social_media_api.dto.RelationshipIdDto;

import java.util.List;

public interface RelationshipService {
    List<RelationshipDto> getAllRelationships();
    List<RelationshipDto> getRelationshipsByUserId(int userId);
    String sendRequestFriend(RelationshipIdDto relationshipIdDto);
    String acceptRequestFriend(RelationshipIdDto relationshipIdDto);
    String rejectRequestFriend(RelationshipIdDto relationshipIdDto);
    String deleteFriend(RelationshipIdDto relationshipIdDto);
    String unsubscribe(RelationshipIdDto relationshipIdDto);
}
