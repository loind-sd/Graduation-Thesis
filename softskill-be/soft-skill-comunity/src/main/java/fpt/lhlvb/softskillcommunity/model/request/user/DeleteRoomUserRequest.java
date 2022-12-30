package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class DeleteRoomUserRequest {
    @NotNull
    private Long roomId;
    @NotNull
    private Long taskId;
}
