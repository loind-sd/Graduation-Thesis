package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.WorldInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.WORLD_INVITATION_URL)
public class WorldInvitationController {

    @Autowired
    private WorldInvitationService worldInvitationService;

    @PostMapping("/load")
    public ResponseEntity<ApiResponse> loadInvitation(@RequestBody Map<String, Object> req) {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, worldInvitationService.loadInvitation(req));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> sentInvitation(@RequestBody Map<String, Object> req) {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200,
                    CommonConstant.CREATE_SUCCESS,
                    worldInvitationService.addInvitation(Long.parseLong(req.get("roomId").toString())));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> deleteMsg(@PathVariable Long id) {
        return ResponseEntity.ok().body(worldInvitationService.deleteMessage(id));
    }
}
