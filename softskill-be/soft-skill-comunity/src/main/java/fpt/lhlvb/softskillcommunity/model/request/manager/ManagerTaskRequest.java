package fpt.lhlvb.softskillcommunity.model.request.manager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerTaskRequest {
    private Long softSkillId;
    private String time;
    private Integer statusId;
}
