package fpt.lhlvb.softskillcommunity.model.response.manager.mapping;

import java.sql.Date;
import java.sql.Timestamp;

public interface IndividualTaskInfoMapping {
    Long getId();

    String getTaskName();

    String getTaskContent();

    Long getSoftSkillId();

    String getSoftSkillName();

    String getStatus();

    Timestamp getCreatedDate();

    String getCreatedName();

    Date getStartDate();

    Date getEndDate();

    Integer getDoneNumber();
}
