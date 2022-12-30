package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomAddResponse {
    private Long roomId;
    private Long softSkillId;
    private Long taskId;
    private Long createById;
    private String roomName;
    private String roomCode;
}
