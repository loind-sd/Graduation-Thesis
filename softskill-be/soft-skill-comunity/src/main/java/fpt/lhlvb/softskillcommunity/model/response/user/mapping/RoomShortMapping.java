package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Timestamp;

public interface RoomShortMapping {
    Long getRoomId();

    String getRoomCode();

    String getRoomName();

    Integer getRoomSize();

    Long getTaskId();

    Integer getCountUserOnline();

    Boolean getIsLock();
}
