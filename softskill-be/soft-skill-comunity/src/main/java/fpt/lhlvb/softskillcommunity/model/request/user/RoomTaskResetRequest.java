package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class RoomTaskResetRequest {
    @NotNull
    private Long tasksId;
    @NotNull
    private Long roomId;
}
