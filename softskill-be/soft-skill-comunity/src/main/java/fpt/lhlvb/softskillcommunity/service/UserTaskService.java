package fpt.lhlvb.softskillcommunity.service;

import fpt.lhlvb.softskillcommunity.model.request.user.UserTaskOtherRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.UserTaskRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public interface UserTaskService {
    ApiResponse getAllUserTasks(Integer statusId);

    Long addNewIndividualTask();

    void deleteIndividualTask(Long taskId);

    Map<String, Object> updateStatusUserTask(Long taskId);

    ApiResponse getIndividualTaskInfo();

    ApiResponse addIndividualTask(Map<String, Object> req);

    ApiResponse updateIndividualTask(Map<String, Object> req);

    ApiResponse deleteIndividualTask(Long[] ids);
}
