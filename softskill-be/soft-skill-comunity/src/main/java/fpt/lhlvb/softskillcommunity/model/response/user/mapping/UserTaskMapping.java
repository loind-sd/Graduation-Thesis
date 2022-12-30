package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Timestamp;

public interface UserTaskMapping {
    Long getTaskId();
    String getTaskName();
    String getTaskContent();
    Integer getStatusId();
    String getStatusName();
}
