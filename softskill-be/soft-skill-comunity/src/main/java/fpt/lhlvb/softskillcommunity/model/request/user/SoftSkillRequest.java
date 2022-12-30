package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SoftSkillRequest {
    private Long softSkillId;
    private String softSkillName;
    private String status;
}
