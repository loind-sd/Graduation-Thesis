package fpt.lhlvb.softskillcommunity.model.response.manager;

import java.sql.Date;
import java.sql.Timestamp;

public interface TasksBySoftSkillMapping {
    Long getTaskId();

    String getTaskName();

    String getTaskContent();

    Boolean getIsGeneral();

    Date getCreateDate();

    String getCreatedBy();

    Date getStartTime();

    Date getEndTime();

    Integer getCountCompleted();

    Integer getCountFavourite();
}
