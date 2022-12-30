package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private Long id;
    private String content;
    private boolean status;
    private boolean delivered;
    private String timeCreated;
    private Long senderId;
    private Long typeId;
    private String notificationTypeName;
    private String picture;
}
