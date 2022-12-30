package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Date;

public interface UserRoomTaskOtherMapping {
    Long getUserId();
    String getNameUser();
    String getNickname();
    String getPicture();
    Long getTaskId();
    Date getJoinTime();
}