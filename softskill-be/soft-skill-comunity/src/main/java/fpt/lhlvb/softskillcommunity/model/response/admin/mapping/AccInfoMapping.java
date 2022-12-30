package fpt.lhlvb.softskillcommunity.model.response.admin.mapping;

import java.sql.Timestamp;

public interface AccInfoMapping {
    Long getId();
    String getName();
    String getMail();
    Long getRoleId();
    String getRoleName();
    String getStatus();
    Timestamp getCreatedDate();
}