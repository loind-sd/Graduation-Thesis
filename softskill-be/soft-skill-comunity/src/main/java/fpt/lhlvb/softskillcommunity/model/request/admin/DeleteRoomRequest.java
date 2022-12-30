package fpt.lhlvb.softskillcommunity.model.request.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRoomRequest {
    private Long roomId;
    private Boolean deleteRoomMaster;
}
