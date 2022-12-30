package fpt.lhlvb.softskillcommunity.model.response.manager.mapping;

import java.sql.Date;
import java.sql.Timestamp;

public interface GroupTaskInfoMapping {
    Long getId();

    String getTaskName();

    String getTaskContent();

    Long getSoftSkillId();

    String getSoftSkillName();

    String getStatus();

    Timestamp getCreatedDate();

    String getCreatedName();

    String getGuideName();

    Date getStartDate();

    Date getEndDate();

    Integer getDoingNumber();

    Integer getDoneNumber();

    Integer getFavoriteNumber();

    Long getRequiredTask();

    byte[] getTaskImage();

    byte[] getGuidelineImage();
}
