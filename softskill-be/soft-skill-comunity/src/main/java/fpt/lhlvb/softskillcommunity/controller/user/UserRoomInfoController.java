package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.UserRoomInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.USER_ROOM_INFO_URL)
public class UserRoomInfoController {

    @Autowired
    private UserRoomInfoService userRoomInfoService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addUserRoomInfo(@RequestBody Map<String, Object> req) {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, userRoomInfoService.addUserRoomInfo(req, CommonConstant.NUMBER_2));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateUserRoomInfo(@RequestBody Map<String, Object> req) {
        try {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, userRoomInfoService.updateUserRoomInfo(req));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(CommonConstant.HTTP_CODE_400,CommonConstant.ERROR, e.getMessage());
            return ResponseEntity.ok().body(response);
        }
    }
}
