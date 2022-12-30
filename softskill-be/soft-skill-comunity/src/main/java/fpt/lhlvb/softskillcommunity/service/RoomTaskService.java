package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.request.user.*;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface RoomTaskService {
    Map<String, Object> registerRoomTask(RegisterRoomTaskRequest request);

    Map<String, Object> voteRoomTask(Long roomId);

    Map<String, Object> submitTask(RoomTaskActionRequest roomTaskActionRequest);

    Map<String, Object> changeLockRoom(Long roomId);

    Map<String, Object> completedRoomTask(RoomTaskActionRequest roomTaskActionRequest);

    Map<String, Object> cancelRoomTask(RoomTaskActionRequest roomTaskActionRequest);

    Map<String, Object> getRoomTaskMeetingDetail(Long roomId);

    Map<String, Object> acceptDoRoomTask(RoomTaskActionRequest request);

    Map<String, Object> outRoomMeeting(RoomTaskActionRequest request);

    Map<String, Object> getUserRoomTaskInfo(RoomTaskActionRequest roomTasksRequest);

    ApiResponse resetUserRoomTaskInfo(Long taskId);
}
