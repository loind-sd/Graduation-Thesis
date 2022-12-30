package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTaskOtherRequest {
    private Long taskId;
    private Long softSkillId;
    private Integer statusTask;
}
