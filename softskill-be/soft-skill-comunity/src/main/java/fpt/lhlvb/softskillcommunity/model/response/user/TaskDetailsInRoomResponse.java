package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.GuidelineTaskMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TaskDetailsInRoomMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskDetailsInRoomResponse {
    private TaskDetailsInRoomMapping taskDetailsInRoomMapping;
    private List<GuidelineTaskMapping> guidelineTasks;
}
