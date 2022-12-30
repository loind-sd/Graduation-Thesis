package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.request.manager.AddTaskRequest;
import fpt.lhlvb.softskillcommunity.model.request.manager.ManagerTaskRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.TaskRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.UpdateFavoriteTaskRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.manager.TasksBySoftSkillMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TasksMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TasksSearchMapping;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public interface TasksService {
    Map<String, Object> getTasksNotStartBySoftSkillId(Long softSkillId);

    ApiResponse getAllTasksNotStart();

    Map<String, Object> getTasksByCondition(TaskRequest taskRequest);

    ApiResponse getTasksFavorite();

    List<TasksSearchMapping> getAllTaskUserRoomTask();

    List<TasksSearchMapping> searchTaskByName(String taskName);

    List<TasksMapping> getAllTaskBySoftSkillAndTimeForUser(TaskRequest taskRequest);

    Map<String, Object> getTaskDetailsInRoom(Long taskId);

    List<TasksBySoftSkillMapping> getAllTaskBySoftSkillAndTimeForManager(ManagerTaskRequest taskRequest);

    TasksMapping getByUserIdAndTaskId(Long taskId);

    ApiResponse getTaskById(Long id);

    Map<String, Object> addTask();

//    Map<String, Object> updateTask(AddTaskRequest addTaskRequest);

    Map<String, Object> deleteTask(Long taskId);

    ApiResponse getGroupTaskInfo();

    ApiResponse addGroupTask(MultipartFile fileTask,
                             MultipartFile fileGuideline,
                             Integer softSkillId,
                             String taskName,
                             String taskContent,
                             Integer requiredTask,
                             String startDate,
                             String endDate,
                             String guideline,
                             String status) throws IOException;

    ApiResponse updateGroupTask(MultipartFile fileTask,
                                MultipartFile fileGuideline,
                                Integer softSkillId,
                                String taskName,
                                String taskContent,
                                Integer requiredTask,
                                String startDate,
                                String endDate,
                                String guideline,
                                String status,
                                Integer id,
                                String guidelineChange,
                                Integer no) throws IOException;

    ApiResponse deleteGroupTask(Long[] ids);

    ApiResponse updateFavoriteTaskById(UpdateFavoriteTaskRequest req);

    ApiResponse getAllTaskByCondition(Integer statusId);

}
