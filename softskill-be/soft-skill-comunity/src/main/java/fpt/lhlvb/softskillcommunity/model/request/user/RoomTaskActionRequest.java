package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class RoomTaskActionRequest {
    @NotNull
    private Long tasksId;
    @NotNull
    private Long roomId;
    @NotNull
    private Long softSkillId;
    private Long userId;
}
