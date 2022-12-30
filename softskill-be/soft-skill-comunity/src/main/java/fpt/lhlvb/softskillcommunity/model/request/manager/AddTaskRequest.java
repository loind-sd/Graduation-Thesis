package fpt.lhlvb.softskillcommunity.model.request.manager;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class AddTaskRequest {
    private Long taskId;
    @NotNull
    private String taskName;
    @NotNull
    private Long softSkillId;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private Boolean isGeneral;
}
