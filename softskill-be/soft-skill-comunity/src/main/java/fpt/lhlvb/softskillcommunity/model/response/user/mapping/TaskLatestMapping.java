package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Timestamp;

public interface TaskLatestMapping {
    Long getRoomId();

    Long getTaskId();

    Integer getStatusTask();

    Timestamp getStartTime();

    Integer getCountCancel();
}
