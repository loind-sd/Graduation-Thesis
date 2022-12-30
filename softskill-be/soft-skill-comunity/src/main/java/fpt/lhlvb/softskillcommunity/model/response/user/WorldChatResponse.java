package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldChatResponse {
    private Long id;
    private String content;
    private Long createdBy;
    private String createdName;
    private String createdMail;
    private String createdNickName;
    private String createdPicture;
    private String createdTime;
    private String status;
    private String isFix;
}
