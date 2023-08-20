package ru.effective_mobile.social_media_api.service;

import ru.effective_mobile.social_media_api.dto.FriendDto;
import ru.effective_mobile.social_media_api.entity.Friend;
import ru.effective_mobile.social_media_api.repository.FriendRepository;

import java.util.ArrayList;
import java.util.List;

public class FriendService {
    private FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    static List<FriendDto> friendListToDto(List<Friend> friends) {
        List<FriendDto> friendDtos = new ArrayList<>();
        friends.forEach(
                friend -> {
                    FriendDto friendDto = FriendDto.builder()
                            .setUserId(friend.getFriendId().getUser().getId())
                            .setFriendId(friend.getFriendId().getFriend().getId())
                            .setStatus(friend.getStatus())
                            .build();
                    friendDtos.add(friendDto);
                }
        );
        return friendDtos;
    }

    private FriendDto friendToDto(Friend friend) {
        return FriendDto.builder()
                .setUserId(friend.getFriendId().getUser().getId())
                .setFriendId(friend.getFriendId().getFriend().getId())
                .setStatus(friend.getStatus())
                .build();
    }
}
