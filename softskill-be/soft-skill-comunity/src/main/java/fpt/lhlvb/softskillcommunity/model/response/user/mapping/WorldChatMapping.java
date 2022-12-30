package fpt.lhlvb.softskillcommunity.model.response.user.mapping;

import java.sql.Timestamp;

public interface WorldChatMapping {
    Long getWorldChatId();
    String getWorldChatContent();
    Long getCreatedBy();
    String getCreatedName();
    String getCreatedMail();
    String getCreatedNickName();

    String getCreatedPicture();
    Timestamp getCreatedTime();
    String getStatus();
    String getIsFix();
}
