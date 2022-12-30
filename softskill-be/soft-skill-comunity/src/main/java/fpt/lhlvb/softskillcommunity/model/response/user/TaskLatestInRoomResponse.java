package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.GuidelineTaskMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TaskLatestInRoomMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskLatestInRoomResponse {
    private TaskLatestInRoomMapping taskLatestInRoomMapping;
    private List<GuidelineTaskMapping> guidelineTasks;
}
