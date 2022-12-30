package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.SoftSkills;
import fpt.lhlvb.softskillcommunity.entity.Tasks;
import fpt.lhlvb.softskillcommunity.entity.TasksRelation;
import fpt.lhlvb.softskillcommunity.entity.UserTask;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.manager.IndividualTaskInfoResponse;
import fpt.lhlvb.softskillcommunity.model.response.manager.mapping.IndividualTaskInfoMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserTaskMapping;
import fpt.lhlvb.softskillcommunity.repository.*;
import fpt.lhlvb.softskillcommunity.service.SoftSkillsService;
import fpt.lhlvb.softskillcommunity.service.TasksService;
import fpt.lhlvb.softskillcommunity.service.UserTaskService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserTaskServiceImpl implements UserTaskService {
    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private TasksService tasksService;
    @Autowired
    private CommonService commonService;

    @Autowired
    private SoftSkillsService softSkillsService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SoftSkillsRepository softSkillsRepository;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TaskRelationRepository taskRelationRepository;

    @Override
    public ApiResponse getAllUserTasks(Integer statusId) {
        List<Object> response = new ArrayList<>();
        Long userId = commonService.idUserAccountLogin();

        List<SoftSkills> softSkills = softSkillsRepository.findByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);

        softSkills.forEach(skill -> {
            Map<String, Object> mappingTask = new HashMap<>();
            List<UserTaskMapping> userTasks = null;
            if (Objects.equals(statusId, CommonConstant.STATUS_TASK_DOING)) {
                userTasks = userTaskRepository.getAllUserTasksDoing(userId, skill.getId());
            } else if (Objects.equals(statusId, CommonConstant.STATUS_TASK_COMPLETED)) {
                userTasks = userTaskRepository.getAllUserTasksDone(userId, skill.getId());
            }

            mappingTask.put("softSkill", skill);
            mappingTask.put("userTasks", userTasks);
            response.add(mappingTask);
        });
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public Long addNewIndividualTask() {
        UserTask userTask = new UserTask();

        Long taskId = Long.parseLong("10");
        userTask.setTaskId(taskId);
        userTask.setUserId(commonService.idUserAccountLogin());
        userTask.setStatusId(CommonConstant.STATUS_TASK_DOING);
        userTask.setCommonRegister(commonService.idUserAccountLogin());
        userTaskRepository.save(userTask);
        return taskId;
    }

    @Override
    public void deleteIndividualTask(Long taskId) {
        Optional<UserTask> userTask = userTaskRepository.findByUserIdAndTaskIdAndDeleteFlag(commonService.idUserAccountLogin(), taskId, CommonConstant.DELETE_FLAG_FALSE);
        userTask.get().setCommonDelete(commonService.idUserAccountLogin());
        userTaskRepository.save(userTask.get());
    }

    @Override
    public Map<String, Object> updateStatusUserTask(Long taskId) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        Optional<UserTask> userTasks = userTaskRepository.findByUserIdAndTaskIdAndDeleteFlag(userId, taskId, CommonConstant.DELETE_FLAG_FALSE);

        if (userTasks.isPresent()) {
            userTasks.get().setStatusId(CommonConstant.STATUS_TASK_COMPLETED);
            userTasks.get().setCommonUpdate(userId);
            userTaskRepository.save(userTasks.get());
        } else {
            UserTask userTask = new UserTask();
            userTask.setTaskId(taskId);
            userTask.setUserId(commonService.idUserAccountLogin());
            userTask.setStatusId(CommonConstant.STATUS_TASK_COMPLETED);
            userTask.setCommonRegister(commonService.idUserAccountLogin());
            userTaskRepository.save(userTask);
        }

        response.put("updatedUserTask", taskId);
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.COMPLETED_USER_ROOM_TASK);
        return response;
    }

    @Override
    public ApiResponse getIndividualTaskInfo() {
        List<IndividualTaskInfoMapping> mappings = userTaskRepository.getIndividualTaskInfo(CommonConstant.USERS_TASK_DONT_CARE);
        List<IndividualTaskInfoResponse> responses = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        int i = CommonConstant.NUMBER_1;

        for (IndividualTaskInfoMapping m : mappings) {
            responses.add(new IndividualTaskInfoResponse(
                    m.getId(), i++, m.getTaskName(), m.getSoftSkillId(), m.getSoftSkillName(), m.getStatus(),
                    sdf.format(m.getStartDate()), sdf.format(m.getEndDate()), sdf.format(m.getCreatedDate()), m.getCreatedName(), m.getTaskContent(), m.getDoneNumber()
            ));
        }
        res.put("data", responses);
        res.put("softSkill", softSkillsService.getSoftSkills());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, res);
    }

    @Override
    public ApiResponse addIndividualTask(Map<String, Object> req) {
        Tasks task = new Tasks();
        TasksRelation tasksRelation = new TasksRelation();
        Map<String, Object> response = new HashMap<>();

        // add task
        task.setImageData(null);
        task.setName(String.valueOf(req.get("taskName")));
        task.setContent(String.valueOf(req.get("taskContent")));
        task.setCommonRegister(commonService.idUserAccountLogin());
        if (CommonConstant.TRUE_FLG.equals(String.valueOf(req.get("status")))) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        } else if (CommonConstant.FALSE_FLG.equals(String.valueOf(req.get("status")))) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
        }
        Long taskId = tasksRepository.save(task).getId();

        // add task relation
        tasksRelation.setTaskId(taskId);
        tasksRelation.setGeneral(CommonConstant.DELETE_FLAG_FALSE);
        tasksRelation.setSoftSkillId(Long.parseLong(String.valueOf(req.get("softSkillId"))));
        tasksRelation.setStartTime(java.sql.Date.valueOf(String.valueOf(req.get("startDate"))));
        tasksRelation.setEndTime(java.sql.Date.valueOf(String.valueOf(req.get("endDate"))));
        tasksRelation.setCommonRegister(commonService.idUserAccountLogin());
        taskRelationRepository.save(tasksRelation).getId();
        response.put("individualTask", responseAfterAdd(req, taskId));
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse updateIndividualTask(Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();

        Tasks task = tasksRepository.findById(Long.parseLong(String.valueOf(req.get("id"))))
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        TasksRelation tasksRelation = taskRelationRepository.findByTaskId(task.getId())
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        task.setName(String.valueOf(req.get("taskName")));
        task.setImageData(null);
        task.setContent(String.valueOf(req.get("taskContent")));
        if (CommonConstant.TRUE_FLG.equals(String.valueOf(req.get("status")))) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        } else if (CommonConstant.FALSE_FLG.equals(String.valueOf(req.get("status")))) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
        }
        task.setCommonUpdate(userId);
        response.put("updateTask", tasksRepository.save(task).getId());

        tasksRelation.setSoftSkillId(Long.parseLong(String.valueOf(req.get("softSkillId"))));
        tasksRelation.setStartTime(java.sql.Date.valueOf(String.valueOf(req.get("startDate"))));
        tasksRelation.setEndTime(java.sql.Date.valueOf(String.valueOf(req.get("endDate"))));
        tasksRelation.setCommonUpdate(userId);
        response.put("updateTask", taskRelationRepository.save(tasksRelation).getId());

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse deleteIndividualTask(Long[] ids) {
        return tasksService.deleteGroupTask(ids);
    }

    public IndividualTaskInfoResponse responseAfterAdd(Map<String, Object> req, Long taskId) {
        IndividualTaskInfoResponse response = new IndividualTaskInfoResponse();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        response.setId(taskId);
        response.setNo(CommonConstant.NUMBER_1);
        response.setTaskName(String.valueOf(req.get("taskName")));
        response.setSoftSkillId(Long.parseLong(String.valueOf(req.get("softSkillId"))));
        response.setSoftSkillName(softSkillsRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("softSkillId"))), CommonConstant.DELETE_FLAG_FALSE).get().getName());
        response.setStatus(String.valueOf(req.get("status")));
        response.setStartDate(String.valueOf(req.get("startDate")));
        response.setEndDate(String.valueOf(req.get("endDate")));
        response.setCreatedDate(sdf.format(CommonUtils.resultTimestamp()));
        response.setCreatedName(usersRepository.findByIdAndDeleteFlag(commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE).get().getName());
        response.setTaskContent(String.valueOf(req.get("taskContent")));
        response.setDoneNumber(CommonConstant.NONE_COMPLETE);

        return response;
    }
}
