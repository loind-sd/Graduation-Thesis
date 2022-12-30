package fpt.lhlvb.softskillcommunity.controller.admin;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.admin.DeleteRoomRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(URLConstant.ADMIN_ROOM_URL)
public class RoomAdminController {
    @Autowired
    private RoomsService roomsService;

    @GetMapping
    public ResponseEntity<ApiResponse> getRooms() {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS, roomsService.getRooms());
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteRoom(@RequestBody DeleteRoomRequest request) {
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS, roomsService.deleteRoom(request));
        return ResponseEntity.ok().body(apiResponse);
    }
}
