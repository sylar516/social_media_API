package ru.effective_mobile.social_media_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective_mobile.social_media_api.dto.FriendDto;
import ru.effective_mobile.social_media_api.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    private FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/")
    public List<FriendDto> getAllFriends() {
        return friendService.getAllFriends();
    }

    @GetMapping("/{userId}")
    public List<FriendDto> getFriendsByUserId(@PathVariable("userId") int userId) {
        return friendService.getFriendsByUserId(userId);
    }
}
