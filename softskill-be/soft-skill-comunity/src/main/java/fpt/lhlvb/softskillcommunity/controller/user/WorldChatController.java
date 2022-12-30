package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.ChatChannelInfoService;
import fpt.lhlvb.softskillcommunity.service.ChatChannelService;
import fpt.lhlvb.softskillcommunity.service.FriendRelationService;
import fpt.lhlvb.softskillcommunity.service.WorldChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.WORLD_CHAT_URL)
public class WorldChatController {

    @Autowired
    private WorldChatService worldChatService;

    @Autowired
    private ChatChannelService chatChannelService;

    @Autowired
    private ChatChannelInfoService chatChannelInfoService;

    @GetMapping("/contact")
    public ResponseEntity<ApiResponse> getAllContacts() {
        return ResponseEntity.ok().body(chatChannelService.getAllContact());
    }

    @PostMapping("/member")
    public ResponseEntity<ApiResponse> getAllMember(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(chatChannelService.getAllMemberInRoom(req));
    }

    @DeleteMapping("/member")
    public ResponseEntity<ApiResponse> kickUser(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(chatChannelService.kickUser(req));
    }

    @PostMapping("/contact")
    public ResponseEntity<ApiResponse> addContact(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(chatChannelService.addNewContact(req));
    }

    @PutMapping("/contact")
    public ResponseEntity<ApiResponse> addMember(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(chatChannelService.addMember(req));
    }

    @PostMapping("/friend")
    public ResponseEntity<ApiResponse> getFriendToAdd(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(chatChannelService.getFriendToAdd(req));
    }

    @DeleteMapping("/contact")
    public ResponseEntity<ApiResponse> outRoom(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(chatChannelInfoService.outRoom(req));
    }

    @PostMapping("/load")
    public ResponseEntity<ApiResponse> loadMessage(@RequestBody Map<String, Object> req) {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, worldChatService.loadMessage(req));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> sentMessage(@RequestBody Map<String, Object> req) {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, worldChatService.addMessage(req));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateMsg(@RequestBody Map<String, Object> req) {
        return ResponseEntity.ok().body(worldChatService.updateMessage(req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMsg(@PathVariable Long id) {
        return ResponseEntity.ok().body(worldChatService.deleteMessage(id));
    }
}
