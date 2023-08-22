package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.RelationshipDto;
import ru.effective_mobile.social_media_api.dto.RequestParamsDto;

import java.util.List;

public interface RelationshipService {
    List<RelationshipDto> getAllRelationships();
    List<RelationshipDto> getRelationshipsByUserId(int userId);
    String sendRequestFriend(RequestParamsDto requestParamsDto);
    String acceptRequestFriend(RequestParamsDto requestParamsDto);
    String rejectRequestFriend(RequestParamsDto requestParamsDto);
    String deleteFriend(RequestParamsDto requestParamsDto);
    String unsubscribe(RequestParamsDto requestParamsDto);
}
