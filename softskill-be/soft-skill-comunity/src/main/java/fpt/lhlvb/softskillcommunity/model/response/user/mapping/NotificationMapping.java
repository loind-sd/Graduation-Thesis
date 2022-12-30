package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Timestamp;

public interface NotificationMapping {
    Long getId();
    String getContent();
    Long getSenderId();
    boolean getStatus();
    boolean getDelivered();
    String getTimeCreated();
    Long getTypeId();
    String getTypeName();
    String getPicture();
}
