package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.TaskForYou;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.TaskForYouResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.TaskForYouMapping;
import fpt.lhlvb.softskillcommunity.repository.TaskForYouRepository;
import fpt.lhlvb.softskillcommunity.service.TaskForYouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskForYouServiceImpl implements TaskForYouService {

    @Autowired
    private TaskForYouRepository taskForYouRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public ApiResponse getTaskForYou() {
        Map<String, Object> result = new HashMap<>();
        List<TaskForYou> tasks = taskForYouRepository.findByUserIdAndDeleteFlagOrderById(commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE);
        List<TaskForYouResponse> taskDoing = new ArrayList<>();
        List<TaskForYouResponse> taskDone = new ArrayList<>();

        for (TaskForYou t : tasks) {
            if (t.getStatus() == CommonConstant.NUMBER_3) {
                taskDone.add(new TaskForYouResponse(t.getId(), t.getName()));
            } else if (t.getStatus() == CommonConstant.NUMBER_4) {
                taskDoing.add(new TaskForYouResponse(t.getId(), t.getName()));
            }
        }
        result.put("NotCompleted", taskDoing);
        result.put("Completed", taskDone);
        result.put("idMax", tasks.size() > 0 ? tasks.get(tasks.size() - 1).getId() : 0);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, result);
    }

    @Override
    public ApiResponse addTaskForYou(Map<String, Object> req) {
        TaskForYou task = new TaskForYou();
        task.setUserId(commonService.idUserAccountLogin());
        task.setName(String.valueOf(req.get("name")));
        task.setStatus(CommonConstant.NUMBER_4);
        task.setCommonRegister(commonService.idUserAccountLogin());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS,
                taskForYouRepository.save(task).getId());
    }

    @Override
    public ApiResponse updateTaskForYou(Map<String, Object> req) {
        TaskForYou task = taskForYouRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("id"))), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        task.setName(String.valueOf(req.get("name")));
        task.setStatus(Integer.parseInt(String.valueOf(req.get("status"))));
        task.setCommonUpdate(commonService.idUserAccountLogin());

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS,
                taskForYouRepository.save(task).getId());
    }

    @Override
    public ApiResponse deleteTaskForYou(Map<String, Object> req) {
        TaskForYou task = taskForYouRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("id"))), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        task.setCommonDelete(commonService.idUserAccountLogin());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS,
                taskForYouRepository.save(task).getId());
    }
}
