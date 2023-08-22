package ru.effective_mobile.social_media_api.controller;

import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.social_media_api.dto.RelationshipDto;
import ru.effective_mobile.social_media_api.dto.RequestParamsDto;
import ru.effective_mobile.social_media_api.service.implementation.RelationshipServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class RelationshipController {
    private RelationshipServiceImpl relationshipService;

    public RelationshipController(RelationshipServiceImpl relationshipService) {
        this.relationshipService = relationshipService;
    }

    @GetMapping("/")
    public List<RelationshipDto> getAllRelationships() {
        return relationshipService.getAllRelationships();
    }

    @GetMapping("/{userId}")
    public List<RelationshipDto> getRelationshipsByUserId(@PathVariable("userId") int userId) {
        return relationshipService.getRelationshipsByUserId(userId);
    }

    @PostMapping("/send_request")
    public String sendRequestFriend(@RequestBody RequestParamsDto requestParamsDto) {
        return relationshipService.sendRequestFriend(requestParamsDto);
    }

    @PostMapping("/accept_request")
    public String acceptRequestFriend(@RequestBody RequestParamsDto requestParamsDto) {
        return relationshipService.acceptRequestFriend(requestParamsDto);
    }

    @PostMapping("/reject_request")
    public String rejectRequestFriend(@RequestBody RequestParamsDto requestParamsDto) {
        return relationshipService.rejectRequestFriend(requestParamsDto);
    }

    @DeleteMapping("/unsubscribe")
    public String unsubscribe(@RequestBody RequestParamsDto requestParamsDto) {
        return relationshipService.unsubscribe(requestParamsDto);
    }

    @PostMapping("/delete_friend")
    public String deleteFriend(@RequestBody RequestParamsDto requestParamsDto) {
        return relationshipService.deleteFriend(requestParamsDto);
    }
}
