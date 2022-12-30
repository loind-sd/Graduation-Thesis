package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.*;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.manager.ManagerTaskRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.TaskRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.UpdateFavoriteTaskRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.manager.GroupTaskInfoResponse;
import fpt.lhlvb.softskillcommunity.model.response.manager.GuidelineResponse;
import fpt.lhlvb.softskillcommunity.model.response.manager.TasksBySoftSkillMapping;
import fpt.lhlvb.softskillcommunity.model.response.manager.mapping.GroupTaskInfoMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.TaskDetailsInRoomResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.TasksResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.*;
import fpt.lhlvb.softskillcommunity.repository.*;
import fpt.lhlvb.softskillcommunity.service.SoftSkillsService;
import fpt.lhlvb.softskillcommunity.service.TasksService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TaskRelationRepository taskRelationRepository;

    @Autowired
    private UserRoomTaskInfoRepository userRoomTaskInfoRepository;

    @Autowired
    private GuidelineTaskRepository guidelineTaskRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private SoftSkillsService softSkillsService;

    @Autowired
    private SoftSkillsRepository softSkillsRepository;

    @Autowired
    private UserTaskFavoriteRepository userTaskFavoriteRepository;


    SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYYMMDD);

    @Override
    public Map<String, Object> getTasksNotStartBySoftSkillId(Long softSkillId) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();

        List<TasksMapping> tasksNotStartMappings
                = tasksRepository.getTasksNotStartBySoftSkillIdAndUserId(softSkillId, userId, CommonConstant.STATUS_TASK_NOT_START);

        List<UserRoomTaskOtherMapping> others = userRoomTaskInfoRepository.getUsersRoomTaskOtherNotStartBySoftSkillId(userId, softSkillId);

        response.put("tasksNotStart", getTasksByConditions(tasksNotStartMappings, others, userId, false));
        return response;
    }

    @Override
    public Map<String, Object> getTasksByCondition(TaskRequest taskRequest) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        List<TasksMapping> tasksByCondition = tasksRepository.getTasksByCondition(taskRequest.getSoftSkillId(), userId, taskRequest.getStatusId());
        List<UserRoomTaskOtherMapping> others = userRoomTaskInfoRepository.getUsersRoomTaskOtherByCondition(userId,
                taskRequest.getSoftSkillId(),
                taskRequest.getStatusId());

        response.put("tasksByCondition", getTasksByConditions(tasksByCondition, others, userId, true));
        return response;
    }

    @Override
    public ApiResponse getTasksFavorite() {
        List<Object> list = new ArrayList<>();
        Long userId = commonService.idUserAccountLogin();
        List<SoftSkills> softSkills = softSkillsService.getAllSoftSkill();

        softSkills.forEach(data -> {
            Map<String, Object> mappingTask = new HashMap<>();

            List<TasksMapping> tasksFavourite = tasksRepository.getTasksFavourite(data.getId(), userId);
            List<UserRoomTaskOtherMapping> others = userRoomTaskInfoRepository.getOthersFavourite(data.getId(), userId);
            mappingTask.put("softSkill", data);
            mappingTask.put("data", getTasksByConditions(tasksFavourite, others, userId, true));
            list.add(mappingTask);
        });
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, list);
    }

    private List<TasksResponse> getTasksByConditions(List<TasksMapping> tasksByCondition, List<UserRoomTaskOtherMapping> others, Long userId, Boolean check) {
        if (tasksByCondition == null) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        int countUsers = 0;
        List<TasksResponse> tasksResponses = new ArrayList<>();
        List<Long> usersId = usersRepository.othersUserId(userId);
//        List<RoomShortMapping> roomShortMappings = roomsRepository.getRoomsShortMapping();

        for (TasksMapping tasksMapping : tasksByCondition) {
            TasksResponse tasksResponse = new TasksResponse();

            List<UserRoomTaskOtherMapping> otherFilterByTaskId = others.stream()
                    .filter(o -> o.getTaskId().equals(tasksMapping.getTaskId()))
                    .collect(Collectors.toList());
            List<Long> otherIdFilterByTaskId = otherFilterByTaskId.stream().map(UserRoomTaskOtherMapping::getUserId).collect(Collectors.toList());

//            List<RoomShortMapping> topRoomShortByTask = roomShortMappings.stream()
//                    .filter(o -> o.getTaskId().equals(tasksMapping.getTaskId()))
//                    .sorted(Comparator.comparingInt(RoomShortMapping::getCountUserOnline).reversed()).limit(3)
//                    .collect(Collectors.toList());

            if (!check) {
                List<Long> otherIdFilter = usersId.stream().filter(o -> !otherIdFilterByTaskId.contains(o))
                        .collect(Collectors.toList());
                countUsers = otherIdFilter.size();
            } else {
                countUsers = otherIdFilterByTaskId.size();
            }

            tasksResponse.setTasksMapping(tasksMapping);
            tasksResponse.setCountUsers(countUsers);
//            tasksResponse.setRoomShortMapping(topRoomShortByTask);

            tasksResponses.add(tasksResponse);
        }
        return tasksResponses;
    }

    @Override
    public List<TasksSearchMapping> getAllTaskUserRoomTask() {
        Long userId = commonService.idUserAccountLogin();
        List<TasksSearchMapping> tasksMappings = tasksRepository.getAllTaskUserRoom(userId);
        if (tasksMappings == null) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }

        return tasksMappings;
    }

    @Override
    public List<TasksSearchMapping> searchTaskByName(String taskName) {
        List<TasksSearchMapping> tasksMappings = getAllTaskUserRoomTask().stream()
                .filter(o -> o.getTaskName().toLowerCase().contains(taskName.toLowerCase()))
                .collect(Collectors.toList());

        return tasksMappings;
    }

    @Override
    public List<TasksMapping> getAllTaskBySoftSkillAndTimeForUser(TaskRequest taskRequest) {
        LocalDate localDate = LocalDate.now();

        List<Date> timestamps = CommonUtils.getStartAndEndDateInMonth(localDate.getMonthValue(), localDate.getYear());
        Date startTime = timestamps.get(0);
        Date endTime = timestamps.get(1);

        List<TasksMapping> tasksMappings
                = tasksRepository.getAllTaskBySoftSkill(taskRequest.getSoftSkillId(), startTime, endTime);
        return tasksMappings;
    }

    @Override
    public Map<String, Object> getTaskDetailsInRoom(Long taskId) {
        Map<String, Object> response = new HashMap<>();

        TaskDetailsInRoomResponse taskDetailsInRoomResponse = new TaskDetailsInRoomResponse();

        TaskDetailsInRoomMapping taskDetailsInRoomMapping = tasksRepository.getTaskDetailsInRoomMapping(taskId)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        List<GuidelineTaskMapping> guidelineTasks = guidelineTaskRepository.findByTaskIdAndDeleteFlagCustom(taskId, CommonConstant.DELETE_FLAG_FALSE);

        taskDetailsInRoomResponse.setTaskDetailsInRoomMapping(taskDetailsInRoomMapping);
        taskDetailsInRoomResponse.setGuidelineTasks(guidelineTasks);

        response.put("taskDetails", taskDetailsInRoomResponse);
        return response;
    }

    @Override
    public List<TasksBySoftSkillMapping> getAllTaskBySoftSkillAndTimeForManager(ManagerTaskRequest taskRequest) {
        String[] monthYear = taskRequest.getTime().split("-");
        if (monthYear.length < 2) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }

        int month = Integer.parseInt(monthYear[1]);
        int year = Integer.parseInt(monthYear[0]);
        List<Date> timestamps = CommonUtils.getStartAndEndDateInMonth(month, year);
        Date startTime = timestamps.get(0);
        Date endTime = timestamps.get(1);
        try {
            List<TasksBySoftSkillMapping> tasksMappings
                    = tasksRepository.getAllTaskBySoftSkillForManager(taskRequest.getSoftSkillId(),
                    sdf.parse(sdf.format(startTime)),
                    sdf.parse(sdf.format(endTime)),
                    taskRequest.getStatusId());
            return tasksMappings;
        } catch (Exception e) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
    }

    @Override
    public TasksMapping getByUserIdAndTaskId(Long taskId) {
        Long userId = commonService.idUserAccountLogin();
        TasksMapping tasksMapping = tasksRepository.findByUserIdAndTaskId(userId, taskId)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        return tasksMapping;
    }

    @Override
    public ApiResponse getTaskById(Long id) {
        Optional<Tasks> response = tasksRepository.findByIdAndDeleteFlag(id, CommonConstant.DELETE_FLAG_FALSE);
        if (response.isEmpty()) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response.get());
    }

    // unit test
    public Map<String, Object> addTask() {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();

        Tasks task = new Tasks();
        task.setName(CommonUtils.randomString(5));
        task.setContent(CommonUtils.randomString(10));
        task.setCommonRegister(userId);

        Long taskId = tasksRepository.save(task).getId();

        response.put("taskId", taskId);
//        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.ADD_TASK_SUCCESS);
        return response;
    }

    // unit test
    @Override
    public Map<String, Object> deleteTask(Long taskId) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();

        Tasks task = tasksRepository.findByIdAndDeleteFlag(taskId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        task.setCommonDelete(userId);
        tasksRepository.save(task);

        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.DELETE_TASK_SUCCESS);
        return response;
    }

    @Override
    public ApiResponse getGroupTaskInfo() {
        List<GroupTaskInfoMapping> mappings = tasksRepository.getGroupTaskInfo();
        List<GroupTaskInfoResponse> responses = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        int m = CommonConstant.NUMBER_1, n = CommonConstant.NUMBER_1;
        List<GuidelineResponse> guidelineResponses = new ArrayList<>();
        for (int i = 0; i < mappings.size(); i++) {
            GroupTaskInfoMapping mp = mappings.get(i);
            if (i != mappings.size() - 1 && mp.getId().equals(mappings.get(i + 1).getId())) {
                if (guidelineResponses.isEmpty()) {
                    guidelineResponses.add(new GuidelineResponse(m++, mp.getGuideName()));
                    guidelineResponses.add(new GuidelineResponse(m++, mappings.get(i + 1).getGuideName()));
                } else {
                    guidelineResponses.add(new GuidelineResponse(m++, mappings.get(i + 1).getGuideName()));
                }
            } else if (i == mappings.size() - 1 || !mp.getId().equals(mappings.get(i + 1).getId())) {
                if (guidelineResponses.isEmpty() && !"".equals(mp.getGuideName().trim())) {
                    guidelineResponses.add(new GuidelineResponse(CommonConstant.NUMBER_1, mp.getGuideName()));
                }
                responses.add(new GroupTaskInfoResponse(
                        mp.getId(), n++, mp.getTaskName(), mp.getSoftSkillId(),
                        mp.getSoftSkillName(), mp.getStatus(), sdf.format(mp.getStartDate()), sdf.format(mp.getEndDate()),
                        sdf.format(mp.getCreatedDate()), mp.getCreatedName(), mp.getTaskContent(), CommonConstant.DELETE_FLAG_FALSE, guidelineResponses,
                        mp.getDoingNumber(), mp.getDoneNumber(), mp.getFavoriteNumber(),
                        mp.getRequiredTask(), mp.getTaskImage(), mp.getGuidelineImage())
                );
                m = CommonConstant.NUMBER_1;
                guidelineResponses = new ArrayList<>();
            }
        }
        res.put("data", responses);
        res.put("personalTask", taskRelationRepository.getPersonalTask());
        res.put("softSkill", softSkillsService.getSoftSkills());
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, res);
    }

    @Override
    public ApiResponse addGroupTask(MultipartFile fileTask,
                                    MultipartFile fileGuideline,
                                    Integer softSkillId,
                                    String taskName,
                                    String taskContent,
                                    Integer requiredTask,
                                    String startDate,
                                    String endDate,
                                    String guideline,
                                    String status) throws IOException {
        Map<String, Object> response = new HashMap<>();
        Tasks task = new Tasks();
        TasksRelation tasksRelation = new TasksRelation();

        // add task
        task.setName(taskName);
        task.setImageData(fileTask.isEmpty() ? null : fileTask.getBytes());
        task.setContent(taskContent);
        task.setCommonRegister(commonService.idUserAccountLogin());
        if (CommonConstant.TRUE_FLG.equals(status)) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        } else if (CommonConstant.FALSE_FLG.equals(status)) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
        }
        Long taskId = tasksRepository.save(task).getId();
        response.put("addTask", taskId);

        // add task relation
        tasksRelation.setTaskId(taskId);
        tasksRelation.setGeneral(CommonConstant.DELETE_FLAG_TRUE);
        tasksRelation.setSubTask(requiredTask == -1 ? null : Long.parseLong(String.valueOf(requiredTask)));
        tasksRelation.setSoftSkillId(Long.parseLong(String.valueOf(softSkillId)));
        tasksRelation.setStartTime(java.sql.Date.valueOf(String.valueOf(startDate)));
        tasksRelation.setEndTime(java.sql.Date.valueOf(String.valueOf(endDate)));
        tasksRelation.setCommonRegister(commonService.idUserAccountLogin());
        response.put("addTaskRelation", taskRelationRepository.save(tasksRelation).getId());

        // add guideline task
        addGuidelineTask(guideline, taskId, fileGuideline, null);
        response.put("addGuidelineTask", "done");
        response.put("groupTask", responseAfterAdd(fileTask, fileGuideline, softSkillId, taskName, taskContent, requiredTask, startDate, endDate, guideline, status, taskId));

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }


    public GroupTaskInfoResponse responseAfterAdd(MultipartFile fileTask,
                                                  MultipartFile fileGuideline,
                                                  Integer softSkillId,
                                                  String taskName,
                                                  String taskContent,
                                                  Integer requiredTask,
                                                  String startDate,
                                                  String endDate,
                                                  String guideline,
                                                  String status, Long taskId) throws IOException {
        GroupTaskInfoResponse response = new GroupTaskInfoResponse();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        response.setId(taskId);
        response.setNo(CommonConstant.NUMBER_1);
        response.setTaskName(taskName);
        response.setSoftSkillId(Long.parseLong(String.valueOf(softSkillId)));
        response.setSoftSkillName(softSkillsRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(softSkillId)), CommonConstant.DELETE_FLAG_FALSE).get().getName());
        response.setStatus(status);
        response.setStartDate(startDate);
        response.setEndDate(endDate);
        response.setCreatedDate(sdf.format(CommonUtils.resultTimestamp()));
        response.setCreatedName(usersRepository.findByIdAndDeleteFlag(commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE).get().getName());
        response.setTaskContent(taskContent);
        response.setDoingNumber(CommonConstant.NONE_COMPLETE);
        response.setDoneNumber(CommonConstant.NONE_COMPLETE);
        response.setFavoriteNumber(CommonConstant.NONE_COMPLETE);
        response.setRequiredTask(Long.parseLong(String.valueOf(requiredTask)));
        response.setTaskImage(fileTask == null || fileTask.isEmpty() ? null : fileTask.getBytes());
        response.setGuidelineImage(fileGuideline == null || fileGuideline.isEmpty() ? null : fileGuideline.getBytes());

        List<GuidelineResponse> guidelineResponseList = new ArrayList<>();
        String[] guidelineResponses = guideline.split("\\|\\|\\|");
        int a = CommonConstant.NUMBER_1;
        if (guidelineResponses.length == CommonConstant.NUMBER_1 && guidelineResponses[0].trim().isBlank()) {
        } else {
            for (String m : guidelineResponses) {
                guidelineResponseList.add(new GuidelineResponse(a++, m));
            }
        }
        response.setGuideline(guidelineResponseList);
        response.setGuidelineChange(CommonConstant.DELETE_FLAG_FALSE);
        return response;
    }

    @Override
    public ApiResponse updateGroupTask(MultipartFile fileTask,
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
                                       Integer no) throws IOException {
        Long userId = commonService.idUserAccountLogin();

        Tasks task = tasksRepository.findById(Long.parseLong(String.valueOf(id)))
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        TasksRelation tasksRelation = taskRelationRepository.findByTaskId(task.getId())
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        task.setName(taskName);
        if (fileTask != null && !fileTask.isEmpty()) {
            task.setImageData(fileTask.getBytes());
        }
        task.setContent(taskContent);
        if (CommonConstant.TRUE_FLG.equals(status)) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        } else if (CommonConstant.FALSE_FLG.equals(status)) {
            task.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
            tasksRelation.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
        }
        task.setCommonUpdate(userId);
        tasksRepository.save(task);

        tasksRelation.setSubTask(Long.parseLong(String.valueOf(requiredTask)));
        tasksRelation.setSoftSkillId(Long.parseLong(String.valueOf(softSkillId)));
        tasksRelation.setStartTime(java.sql.Date.valueOf(startDate));
        tasksRelation.setEndTime(java.sql.Date.valueOf(endDate));
        tasksRelation.setCommonUpdate(userId);
        taskRelationRepository.save(tasksRelation).getId();

        if (Boolean.parseBoolean(String.valueOf(guidelineChange))) {
            byte[] original = null;
            List<GuidelineTask> guidelineTasks = guidelineTaskRepository.findByTaskIdAndDeleteFlag(task.getId(), CommonConstant.DELETE_FLAG_FALSE);
            if (!guidelineTasks.isEmpty() && fileGuideline.isEmpty()) {
                original = guidelineTasks.get(0).getImageData();
            }
            for (GuidelineTask g : guidelineTasks) {
                g.setCommonDelete(userId);
                guidelineTaskRepository.save(g);
            }
            addGuidelineTask(guideline, task.getId(), fileGuideline, original);
        }

        List<GroupTaskInfoMapping> mappings = tasksRepository.getGroupTaskInfoById(id);
        GroupTaskInfoResponse response = null;
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);
        int m = CommonConstant.NUMBER_1;
        List<GuidelineResponse> guidelineResponses = new ArrayList<>();
        for (GroupTaskInfoMapping mp : mappings) {
            guidelineResponses.add(new GuidelineResponse(m++, mp.getGuideName()));
        }
        if (!mappings.isEmpty()) {
            GroupTaskInfoMapping mp = mappings.get(0);
            response = new GroupTaskInfoResponse(
                    mp.getId(), no, mp.getTaskName(), mp.getSoftSkillId(),
                    mp.getSoftSkillName(), mp.getStatus(), sdf.format(mp.getStartDate()), sdf.format(mp.getEndDate()),
                    sdf.format(mp.getCreatedDate()), mp.getCreatedName(), mp.getTaskContent(), CommonConstant.DELETE_FLAG_FALSE, guidelineResponses,
                    mp.getDoingNumber(), mp.getDoneNumber(), mp.getFavoriteNumber(),
                    mp.getRequiredTask(), mp.getTaskImage(), mp.getGuidelineImage());
        }

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse deleteGroupTask(Long[] ids) {
        for (Long id : ids) {
            Tasks task = tasksRepository.findById(id)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
            TasksRelation tasksRelation = taskRelationRepository.findByTaskId(id)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

            task.setCommonDelete(commonService.idUserAccountLogin());
            tasksRelation.setCommonDelete(commonService.idUserAccountLogin());
            tasksRepository.save(task);
            taskRelationRepository.save(tasksRelation);
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse getAllTasksNotStart() {
        List<Object> list = new ArrayList<>();
        Long userId = commonService.idUserAccountLogin();
        List<SoftSkills> softSkills = softSkillsService.getAllSoftSkill();

        softSkills.forEach(data -> {
            Map<String, Object> mappingTask = new HashMap<>();

            List<TasksMapping> tasksNotStartMappings
                    = tasksRepository.getTasksNotStartBySoftSkillIdAndUserId(data.getId(), userId, CommonConstant.STATUS_TASK_NOT_START);
            List<UserRoomTaskOtherMapping> others = userRoomTaskInfoRepository.getUsersRoomTaskOtherNotStartBySoftSkillId(userId, data.getId());
            mappingTask.put("softSkill", data);
            mappingTask.put("data", getTasksByConditions(tasksNotStartMappings, others, userId, false));
            list.add(mappingTask);
        });
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, list);
    }

    @Override
    public ApiResponse updateFavoriteTaskById(UpdateFavoriteTaskRequest req) {
        Optional<UserTaskFavorite> userTaskFavorite = userTaskFavoriteRepository.findByTaskIdAndDeleteFlagAndCreatedBy(
                req.getTaskId(), CommonConstant.DELETE_FLAG_FALSE, commonService.idUserAccountLogin());
        if (userTaskFavorite.isEmpty()) {
            UserTaskFavorite userTaskFavoriteAdd = new UserTaskFavorite();
            userTaskFavoriteAdd.setTaskId(req.getTaskId());
            userTaskFavoriteAdd.setFavorite(req.getIsFavorite());
            userTaskFavoriteAdd.setStatusId(req.getStatus());
            userTaskFavoriteAdd.setCommonRegister(commonService.idUserAccountLogin());
            userTaskFavoriteRepository.save(userTaskFavoriteAdd);
        } else {
            userTaskFavorite.get().setCommonDelete(commonService.idUserAccountLogin());
            userTaskFavoriteRepository.save(userTaskFavorite.get());
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, null);
    }

    @Override
    public ApiResponse getAllTaskByCondition(Integer statusId) {
        List<Object> list = new ArrayList<>();
        Long userId = commonService.idUserAccountLogin();
        List<SoftSkills> softSkills = softSkillsService.getAllSoftSkill();

        softSkills.forEach(data -> {
            Map<String, Object> mappingTask = new HashMap<>();
            List<TasksMapping> tasksByCondition = tasksRepository.getTasksByCondition(data.getId(), userId, statusId);
            List<UserRoomTaskOtherMapping> others = userRoomTaskInfoRepository.getUsersRoomTaskOtherByCondition(userId,
                    data.getId(),
                    statusId);
            mappingTask.put("softSkill", data);
            mappingTask.put("data", getTasksByConditions(tasksByCondition, others, userId, true));
            list.add(mappingTask);
        });
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, list);
    }

    public void addGuidelineTask(String guideline, Long taskId, MultipartFile fileGuideline, byte[] original) throws IOException {
        String[] guidelineResponses = guideline.split("\\|\\|\\|");
        int index = CommonConstant.NUMBER_1;
        for (String g : guidelineResponses) {
            if (!g.isBlank()) {
                GuidelineTask guidelineTask = new GuidelineTask();
                guidelineTask.setTaskId(taskId);
                guidelineTask.setCommonRegister(commonService.idUserAccountLogin());
                guidelineTask.setContent(g);
                guidelineTask.setTitle("Bước " + index++);
                if (fileGuideline != null && !fileGuideline.isEmpty()) {
                    guidelineTask.setImageData(fileGuideline.isEmpty() ? null : fileGuideline.getBytes());
                } else if (fileGuideline == null && original != null) {
                    guidelineTask.setImageData(original);
                }
                guidelineTaskRepository.save(guidelineTask);
            }
        }
    }

}
