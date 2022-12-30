package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Timestamp;

public interface RoomsMapping {
    Long getRoomId();

    String getRoomCode();

    String getRoomName();

    String getDescription();

    Long getSoftSkillId();

    Integer getRoomSize();

    Integer getCountUserOnline();

    Integer getCountUserRegister();

    // task Latest in room
    String getTaskId();

    String getTaskName();

    Boolean getIsLock();

    Long getCreateById();

    Timestamp getStartTime();

    String getSoftSkillName();
}
