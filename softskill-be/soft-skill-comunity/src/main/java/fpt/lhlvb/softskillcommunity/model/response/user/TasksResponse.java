package fpt.lhlvb.softskillcommunity.model.response.user;

import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RoomShortMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TasksMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TasksResponse {
    private TasksMapping tasksMapping;
    private Integer countUsers;
    private List<RoomShortMapping> roomShortMapping;
}
