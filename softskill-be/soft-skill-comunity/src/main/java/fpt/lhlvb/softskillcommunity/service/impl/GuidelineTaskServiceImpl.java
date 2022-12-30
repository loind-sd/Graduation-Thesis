package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.GuidelineTask;
import fpt.lhlvb.softskillcommunity.entity.Tasks;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.GuidelineTaskMapping;
import fpt.lhlvb.softskillcommunity.repository.GuidelineTaskRepository;
import fpt.lhlvb.softskillcommunity.repository.RoomsRepository;
import fpt.lhlvb.softskillcommunity.repository.TasksRepository;
import fpt.lhlvb.softskillcommunity.service.GuidelineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuidelineTaskServiceImpl implements GuidelineTaskService {
    @Autowired
    private GuidelineTaskRepository guidelineRepository;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private CommonService commonService;
    @Override
    public ApiResponse getGuidelineByTaskId(Long taskId) {
        List<Object> list = new ArrayList<>();
        Map<String, Object> mapping = new HashMap<>();

        Optional<Tasks> tasks = tasksRepository.findByIdAndDeleteFlag(taskId,CommonConstant.DELETE_FLAG_FALSE);
        List<GuidelineTaskMapping> guidelineTasks = guidelineRepository.findByTaskIdAndDeleteFlagCustom(taskId, CommonConstant.DELETE_FLAG_FALSE);

        mapping.put("task", tasks.get());
        mapping.put("guidelineTask", guidelineTasks);
        mapping.put("rooms", roomsRepository.getTop3DoingRoom(taskId, commonService.idUserAccountLogin()));
        list.add(mapping);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, list);
    }
}
