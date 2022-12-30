package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TasksMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserRoomDetailMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomTaskMeetingResponse {
    private Long roomId;
    private Long softSkillId;
    private List<UserRoomDetailMapping> usersRoom;
    private List<TasksMapping> tasks;
    private TaskLatestInRoomResponse taskDetails;
    private Boolean isLockRoom;
    private Boolean isRoomMaster;
    private Boolean isOldMember;
    private Boolean isClickCompleted;
    private Boolean isClickCancel;
}
