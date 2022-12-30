package fpt.lhlvb.softskillcommunity.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private Long id;
    private String title;
    private String content;
    private String createdName;
    private String createdNickName;
    private String createdMail;
}
