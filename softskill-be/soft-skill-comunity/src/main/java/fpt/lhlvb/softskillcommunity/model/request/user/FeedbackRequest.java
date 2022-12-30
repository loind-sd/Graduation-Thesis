package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeedbackRequest {
    private String rateStar;
    private String titleId;
    private String content;
}
