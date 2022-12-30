package fpt.lhlvb.softskillcommunity.model.response.admin.mapping;

import java.sql.Timestamp;

public interface SoftSkillInfoMapping {
    Long getId();
    String getSoftSkillName();
    int getNumberRoom();
    int getCountTaskCompleted();
    String getStatus();
    Timestamp getCreatedDate();
    String getCreatedName();
}
