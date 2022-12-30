package fpt.lhlvb.softskillcommunity.model.request.admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackRequest {
    private Long feedbackId;
    private String contentResponse;
    private String email;
}
