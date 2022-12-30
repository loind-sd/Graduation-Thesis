package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.request.admin.DeleteRoomRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.DeleteRoomUserRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomSearchRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.RoomDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface RoomsService {
    RoomDetailResponse getRoomDetailsByRoomId(Long roomId);

    Map<String, Object> addRoom(RoomRequest roomRequest);

    List<Long> addNewRoomBooking();

    Map<String, Object> updateRoom(RoomRequest roomRequest);

    Map<String, Object> deleteRoom(DeleteRoomRequest request);

    Map<String, Object> getRoomsBookingBySoftSkillId(RoomSearchRequest request);

    Map<String, Object> getRoomsActiveBySoftSkillId(RoomSearchRequest request);

    Map<String, Object> getRoomsBookingByCreateBy(RoomSearchRequest request);

    Map<String, Object> getRoomsActiveByCreateBy(RoomSearchRequest request);

    Map<String, Object> getRoomsBookingByIsJoined(RoomSearchRequest request);

    Map<String, Object> getRoomsActiveByIsJoined(RoomSearchRequest request);

    Map<String, Object> searchAndFilterForActiveRoom(RoomSearchRequest request);

    Map<String, Object> searchAndFilterForBookingRoom(RoomSearchRequest request);

    Map<String, Object> userDeleteRoomBooking(DeleteRoomUserRequest request);

    ApiResponse getRooms();

    Map<String, Object> getRoomsActiveForChat(Map<String, Object> request);
}
