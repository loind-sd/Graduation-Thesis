package fpt.lhlvb.softskillcommunity.model.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TaskRequest {
    @NotNull
    private Long softSkillId;
    private Integer statusId;
}
