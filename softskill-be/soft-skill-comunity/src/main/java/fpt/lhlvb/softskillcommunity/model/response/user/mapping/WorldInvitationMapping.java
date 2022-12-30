package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Timestamp;

public interface WorldInvitationMapping {
    Long getWorldInvitationId();
    Long getRoomId();
    Long getCreatedBy();
    String getCreatedName();
    String getCreatedMail();
    String getCreatedNickName();
    Timestamp getCreatedTime();
    String getStatus();
}
