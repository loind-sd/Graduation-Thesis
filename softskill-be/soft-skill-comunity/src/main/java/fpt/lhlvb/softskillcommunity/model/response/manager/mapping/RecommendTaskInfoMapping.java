package fpt.lhlvb.softskillcommunity.model.response.manager.mapping;

import java.sql.Timestamp;

public interface RecommendTaskInfoMapping {
    Long getId();
    String getCreatedName();
    String getTaskContent();
    String getSoftSkillName();
    String getTaskType();
    String getStatus();
    Timestamp getCreatedDate();
    String getUpdateName();
    byte[] getTaskImage();
    String getGuideline();
    byte[] getGuidelineImage();
    String getMail();
}
