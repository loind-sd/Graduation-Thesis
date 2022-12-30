package fpt.lhlvb.softskillcommunity.controller.user;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.URLConstant;
import fpt.lhlvb.softskillcommunity.model.request.user.DeleteRoomUserRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomSearchRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(URLConstant.ROOM_URL)
public class RoomController {

    @Autowired
    private RoomsService roomsService;

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse> getRoomDetailByRoomId(@PathVariable Long roomId){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomDetailsByRoomId(roomId));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping ("/active/searchAndFilter")
    public ResponseEntity<ApiResponse> searchAndFilterForActiveRoom(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.searchAndFilterForActiveRoom(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/booking/searchAndFilter")
    public ResponseEntity<ApiResponse> searchAndFilterForBookingRoom(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.searchAndFilterForBookingRoom(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/active/softSkill")
    public ResponseEntity<ApiResponse> getRoomsActiveBySoftSkill(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomsActiveBySoftSkillId(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/active/createBy")
    public ResponseEntity<ApiResponse> getRoomsActiveByCreateBy(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomsActiveByCreateBy(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/active/isJoined")
    public ResponseEntity<ApiResponse> getRoomsActiveByIsJoined(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomsActiveByIsJoined(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/booking/softSkill")
    public ResponseEntity<ApiResponse> getRoomsBookingBySoftSkill(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomsBookingBySoftSkillId(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/booking/createBy")
    public ResponseEntity<ApiResponse> getRoomsBookingByCreateBy(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomsBookingByCreateBy(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/booking/isJoined")
    public ResponseEntity<ApiResponse> getRoomsBookingByIsJoined(@RequestBody RoomSearchRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomsBookingByIsJoined(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addRoom(@RequestBody RoomRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, roomsService.addRoom(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping()
    public ResponseEntity<ApiResponse> updateRoom(@RequestBody RoomRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, roomsService.updateRoom(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteRoom(@RequestBody DeleteRoomUserRequest request){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, roomsService.userDeleteRoomBooking(request));
        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping("/active/chat")
    public ResponseEntity<ApiResponse> getRoomsActiveForChat(@RequestBody Map<String, Object> req){
        ApiResponse apiResponse = new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, roomsService.getRoomsActiveForChat(req));
        return ResponseEntity.ok().body(apiResponse);
    }
}
