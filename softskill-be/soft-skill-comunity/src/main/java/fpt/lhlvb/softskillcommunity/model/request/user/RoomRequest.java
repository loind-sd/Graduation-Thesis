package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RoomRequest {
    private Long roomId;

    private String roomName;

    private String description;

    private String password;

    private Long softSkillId;

    private Integer roomSize;

    private String startTime;

    private Long taskId;

    public RoomRequest(String roomName, String description, Long softSkillId, Integer roomSize, String startTime, Long taskId) {
        this.roomName = roomName;
        this.description = description;
        this.softSkillId = softSkillId;
        this.roomSize = roomSize;
        this.startTime = startTime;
        this.taskId = taskId;
    }
}
