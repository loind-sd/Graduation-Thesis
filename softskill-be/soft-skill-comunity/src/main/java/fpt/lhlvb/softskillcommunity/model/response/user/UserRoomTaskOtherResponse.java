package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserRoomTaskOtherMapping;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoomTaskOtherResponse {
    private UserRoomTaskOtherMapping userRoomTaskOtherMapping;
    private String joinedTimeAgo;
}
