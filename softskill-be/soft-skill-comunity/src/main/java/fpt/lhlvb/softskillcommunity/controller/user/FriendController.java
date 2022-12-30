package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.SentFriendRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.FriendRelationService;
import fpt.lhlvb.softskillcommunity.service.UserRoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(URLConstant.FRIEND_URL)
public class FriendController {

    @Autowired
    private FriendRelationService friendRelationService;

    @Autowired
    private UserRoomInfoService userRoomInfoService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllFriend() {
        return ResponseEntity.ok().body(friendRelationService.getAllFriend());
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<ApiResponse> searchToAddFriend(@PathVariable String text) {
        return ResponseEntity.ok().body(friendRelationService.searchToAddFriend(text));
    }

    @GetMapping("/plus")
    public ResponseEntity<ApiResponse> getAllFriendEvenRequest() {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, friendRelationService.getAllFriendEvenRequest());
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400, CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addFriend(@RequestBody @Valid SentFriendRequest req) {
        return ResponseEntity.ok().body(friendRelationService.addFriend(req.getId()));
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> actionFriend(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(friendRelationService.actionFriend(req));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> getFriendsRecent() {
        return ResponseEntity.ok().body(userRoomInfoService.getUserRecent());
    }

    @GetMapping("/deleteFriend/{id}")
    public ResponseEntity<ApiResponse> deleteFriend(@PathVariable Long id) {
        return ResponseEntity.ok().body(friendRelationService.deleteFriend(id));
    }
}
