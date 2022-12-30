package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.*;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.user.RegisterRoomTaskRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomTaskActionRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomTaskResetRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.TaskRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.RoomTaskMeetingResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.TaskDetailsInRoomResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.TaskLatestInRoomResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.*;
import fpt.lhlvb.softskillcommunity.repository.*;
import fpt.lhlvb.softskillcommunity.service.NotificationService;
import fpt.lhlvb.softskillcommunity.service.RoomTaskService;
import fpt.lhlvb.softskillcommunity.service.TasksService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomTaskServiceImpl implements RoomTaskService {
    @Autowired
    private RoomsTaskRepository roomsTaskRepository;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UserTaskFavoriteRepository favoriteRepository;

    @Autowired
    private UserRoomInfoRepository userRoomInfoRepository;

    @Autowired
    private UserRoomTaskInfoRepository userRoomTaskInfoRepository;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private RoomTaskRegisterInfoRepository roomTaskRegisterInfoRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RankInfoRepository rankInfoRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TaskRelationRepository taskRelationRepository;

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Autowired
    private GuidelineTaskRepository guidelineTaskRepository;

    @Autowired
    private CommonService commonService;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYYMMDD);

    @Override
    public Map<String, Object> registerRoomTask(RegisterRoomTaskRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        Rooms rooms = roomsRepository.findByIdAndDeleteFlag(request.getRoomId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        RoomsTask roomsTask = roomsTaskRepository.findByRoomIdAndTaskIdAndDeleteFlag(request.getRoomId(), request.getTaskId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        RoomTaskRegisterInfo roomTaskRegisterInfo = new RoomTaskRegisterInfo();
        roomTaskRegisterInfo.setRoomTaskId(roomsTask.getId());
        roomTaskRegisterInfo.setCommonRegister(userId);
        roomTaskRegisterInfoRepository.save(roomTaskRegisterInfo);

        sendNotificationRegisterRoom(rooms.getName(), userId,
                1, roomsTask.getStartTime().getTime() - (1000 * 60 * 30));
        sendNotificationRegisterRoom(rooms.getName(), userId,
                2, roomsTask.getStartTime().getTime());

        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.REGISTER_ROOM_TASK_SUCCESS);
        return response;
    }

    @Override
    public Map<String, Object> voteRoomTask(Long roomId) {
        Map<String, Object> response = new HashMap<>();
        Rooms rooms = roomsRepository.findByIdAndDeleteFlag(roomId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        List<UserRoomInfo> userRoomInfos = userRoomInfoRepository.findByRoomIdAndDeleteFlagAndJoinStatus(roomId,
                CommonConstant.DELETE_FLAG_FALSE, CommonConstant.JOIN_STATUS_TRUE);

        userRoomInfos.forEach(o -> o.setIsOldMember(CommonConstant.IS_OLD_MEMBER_TRUE));
        userRoomInfoRepository.saveAll(userRoomInfos);

        response.put("success", CommonConstant.SUCCESS);
        return response;
    }

    @Override
    public Map<String, Object> submitTask(RoomTaskActionRequest request) {
        Long userId = commonService.idUserAccountLogin();
        Long roomId = request.getRoomId();
        Long softSkillId = request.getSoftSkillId();
        Long taskId = request.getTasksId();
        int countSubmit = 0;
        Map<String, Object> response = new HashMap<>();
        Timestamp timestampNow = CommonUtils.resultTimestamp();

        // get  user joining in room
        Optional<UserRoomInfo> userRoomInfo = userRoomInfoRepository.findByRoomIdAndDeleteFlagAndJoinStatusAndUserId(roomId,
                CommonConstant.DELETE_FLAG_FALSE, CommonConstant.JOIN_STATUS_TRUE, commonService.idUserAccountLogin());
        if (userRoomInfo.isEmpty()) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        userRoomInfo.get().setIsOldMember(CommonConstant.IS_OLD_MEMBER_TRUE);
        userRoomInfoRepository.save(userRoomInfo.get());
//        int countMemberInMeeting = userRoomInfoList.size();

        // getRoomTask
        RoomsTask roomsTask =
                roomsTaskRepository.findByRoomIdAndSoftSkillIdAndTaskIdAndDeleteFlag(roomId, softSkillId, taskId, CommonConstant.DELETE_FLAG_FALSE);

        // roomTask == null -> add new RoomTask;
        if (roomsTask == null) {
            roomsTask = new RoomsTask();

            roomsTask.setRoomId(roomId);
            roomsTask.setTaskId(taskId);
            roomsTask.setSoftSkillId(softSkillId);
            roomsTask.setStatusId(CommonConstant.STATUS_TASK_DOING);
            roomsTask.setCountSubmit(1);
            roomsTask.setCountCompleted(0);
            roomsTask.setCountCancel(0);
            roomsTask.setCommonRegister(userId);
        }
        // != null -> check count submit of all user in roomTask; countSubmit = userInRoom -> submitTask with status = 2
        else {
//            if (countSubmit < countMemberInMeeting) {
//                roomsTask.setCountSubmit(roomsTask.getCountSubmit() + 1);
//                roomsTask.setCommonUpdate(userId);
//
//                countSubmit = roomsTask.getCountSubmit();
//                if (countSubmit == countMemberInMeeting) {
//                    roomsTask.setCountSubmit(countSubmit);
            roomsTask.setStatusId(CommonConstant.STATUS_TASK_DOING);
            roomsTask.setCommonUpdate(userId);
//                }
//            }
        }

        Date dateNow = CommonUtils.getDateNow();
        if (roomsTaskRepository.findByRoomIdAndSoftSkillIdAndDeleteFlag(roomId, softSkillId, CommonConstant.DELETE_FLAG_FALSE).size() == 0) {
            roomsTask.setTaskNo(1);
        } else {
            if (roomsTaskRepository.findByRoomIdAndSoftSkillIdAndTaskIdAndDeleteFlag(roomId, softSkillId, taskId, CommonConstant.DELETE_FLAG_FALSE) == null) {
                roomsTask.setTaskNo(roomsTaskRepository.getMaxTaskNo(roomId, softSkillId) + 1);
            }
        }
        roomsTaskRepository.save(roomsTask);

        // add/update to userRoomTaskInfo
//        if (countSubmit == countMemberInMeeting) {
//        List<Long> usersIdInRoom = userRoomInfoList
//                .stream()
//                .map(UserRoomInfo::getUserId)
//                .collect(Collectors.toList());

        Long subTask = taskRelationRepository.getSubTaskByTaskId(taskId);
        if (subTask != null) {
            addNewUserTask(commonService.idUserAccountLogin(), subTask);
        }

        // if user not exists -> add new userRoomTaskInfo
        if (userRoomTaskInfoRepository.getUserRoomTaskInfoByUserIdAndTaskIdAndRoomId(commonService.idUserAccountLogin(), taskId).isEmpty()) {
            setNewUserRoomTaskInfo(roomId, taskId, commonService.idUserAccountLogin(), CommonConstant.IS_CLICKED_FALSE, CommonConstant.IS_CLICKED_FALSE);
        }

        // set countTaskDoing for Statistic
        Statistic statistic = statisticRepository.getByUserIdAndCreatedDate(commonService.idUserAccountLogin(), dateNow);
        statistic.setCountTaskDoing(statistic.getCountTaskCompleted() + 1);
        statistic.setCommonUpdate(usersRepository.getAdminId(CommonConstant.ROLE_ADMIN));
        statisticRepository.save(statistic);
        response.put("roomTaskAdded", taskId);
        response.put("usersRoomTaskAdded", commonService.idUserAccountLogin());
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.ADD_ROOM_TASK_SUCCESS);
//        } else {
//            response.put("submitTask", taskId);
//            response.put(CommonConstant.WARNING_MESSAGE, MessageUtils.ADD_ROOM_TASK_WAITING);
//        }
        return response;
    }

    @Override
    public Map<String, Object> changeLockRoom(Long roomId) {
        Long userId = commonService.idUserAccountLogin();
        Map<String, Object> response = new HashMap<>();

        Rooms room = roomsRepository.findByIdAndDeleteFlag(roomId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        if (room.getIsLock() == CommonConstant.ROOM_LOCK_FALSE) {
            room.setIsLock(CommonConstant.ROOM_LOCK_TRUE);
            response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.LOCK_ROOM);
        } else {
            room.setIsLock(CommonConstant.ROOM_LOCK_FALSE);
            response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.UNLOCK_ROOM);
        }
        room.setCommonUpdate(userId);

        response.put("lockRoomId",
                roomsRepository.save(room).getId());
        return response;
    }

    @Override
    public Map<String, Object> completedRoomTask(RoomTaskActionRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        Long taskId = request.getTasksId();
        Long roomId = request.getRoomId();
        Timestamp timestampNow = CommonUtils.resultTimestamp();

        RoomsTask roomsTask = roomsTaskRepository.findByRoomIdAndSoftSkillIdAndTaskIdAndDeleteFlagAndStatusId(roomId,
                request.getSoftSkillId(), taskId, CommonConstant.DELETE_FLAG_FALSE,2L);
        int countCompleted = roomsTask.getCountCompleted();

        UserRoomTaskInfo userRoomTaskInfo = userRoomTaskInfoRepository.findByRoomIdAndTaskIdAndUserIdAndDeleteFlag(roomId,
                taskId, userId, CommonConstant.DELETE_FLAG_FALSE);
        if (!userRoomTaskInfo.getIsClickCompleted()) {
            countCompleted += 1;
            userRoomTaskInfo.setIsClickCompleted(CommonConstant.IS_CLICKED_TRUE);
            userRoomTaskInfo.setIsClickCancel(CommonConstant.IS_CLICKED_FALSE);
            userRoomTaskInfoRepository.save(userRoomTaskInfo);
        }

        List<UserRoomInfo> userRoomInfoList = userRoomInfoRepository.findByRoomIdAndJoinStatusAndIsOldMemberAndDeleteFlag(roomId,
                CommonConstant.JOIN_STATUS_TRUE, CommonConstant.IS_OLD_MEMBER_TRUE, CommonConstant.DELETE_FLAG_FALSE);

        roomsTask.setCountCompleted(countCompleted);
        roomsTaskRepository.save(roomsTask);

        if (countCompleted < userRoomInfoList.size()) {
            response.put(CommonConstant.WARNING_MESSAGE, MessageUtils.CHOOSE_STATUS_ROOM_TASK);
        } else if (countCompleted == userRoomInfoList.size()) {
            List<UserTaskFavorite> usersTaskFavorite = new ArrayList<>();
            List<Statistic> statistics = new ArrayList<>();
            List<Users> usersRank = new ArrayList<>();

            List<UserRoomTaskInfo> usersRoomTask = userRoomTaskInfoRepository.findByRoomIdAndTaskIdAndDeleteFlag(roomId,
                    taskId, CommonConstant.DELETE_FLAG_FALSE);

            if (usersRoomTask.size() > 0) {
                RoomDetailMapping rooms = roomsRepository.getRoomDetailsByRoomId(roomId);
                userRoomInfoList.forEach(o -> {
                    if (o.getUserId() != rooms.getCreateById()) {
                        o.setIsOldMember(CommonConstant.IS_OLD_MEMBER_FALSE);
                    }
                });
                userRoomInfoList.forEach(o -> o.setCommonUpdate(o.getUserId()));
                userRoomInfoRepository.saveAll(userRoomInfoList);

                usersRoomTask.forEach(o -> o.setStatusTaskId(CommonConstant.STATUS_TASK_COMPLETED));
                usersRoomTask.forEach(o -> o.setCommonUpdate(o.getUserId()));
                userRoomTaskInfoRepository.saveAll(usersRoomTask);

                for (UserRoomTaskInfo userRoomTask : usersRoomTask) {
                    UserTaskFavorite userTaskFavorite = favoriteRepository.findByTaskIdAndCreatedByAndDeleteFlag(taskId,
                            userRoomTask.getUserId(), CommonConstant.DELETE_FLAG_FALSE);
                    if (userTaskFavorite != null) {
                        userTaskFavorite.setCommonUpdate(userRoomTask.getUserId());
                        userTaskFavorite.setStatusId(CommonConstant.STATUS_TASK_COMPLETED);
//                        favoriteRepository.save(userTaskFavorite);
                        usersTaskFavorite.add(userTaskFavorite);
                    }

                    Map<String, Object> addCompletedTaskToStatistic =
                            addCompletedTaskToStatistic(userRoomTask.getCreatedAt(), timestampNow, userRoomTask.getUserId());

                    statistics.add((Statistic) addCompletedTaskToStatistic.get("statistic"));
                    if (addCompletedTaskToStatistic.get("userRank") != null) {
                        usersRank.add((Users) addCompletedTaskToStatistic.get("userRank"));
                    }
                }

                statisticRepository.saveAll(statistics);
                if (usersRank.size() > 0) {
                    usersRepository.saveAll(usersRank);
                } else if (usersTaskFavorite.size() > 0) {
                    favoriteRepository.saveAll(usersTaskFavorite);
                }
            }

            roomsTask.setStatusId(CommonConstant.STATUS_TASK_COMPLETED);
            roomsTask.setCommonUpdate(userId);
            roomsTask.setCountSubmit(0);
            roomsTask.setCountCompleted(0);
            roomsTaskRepository.save(roomsTask);

            response.put("updateStatusUserRoomTaskToCompleted", taskId);
            response.put("updateStatusUserRoomTaskFavoriteToCompleted", taskId);
            response.put("updateStatusRoomTask", roomsTask.getRoomId());
            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.COMPLETED_ROOM_TASK);
        }

        return response;
    }

    @Override
    public Map<String, Object> cancelRoomTask(RoomTaskActionRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        Long taskId = request.getTasksId();
        Long roomId = request.getRoomId();

        RoomsTask roomsTask = roomsTaskRepository.findByRoomIdAndSoftSkillIdAndTaskIdAndDeleteFlag(roomId,
                request.getSoftSkillId(), taskId, CommonConstant.DELETE_FLAG_FALSE);
        int countCancel = roomsTask.getCountCancel();

        UserRoomTaskInfo userRoomTaskInfo = userRoomTaskInfoRepository.findByRoomIdAndTaskIdAndUserIdAndDeleteFlag(roomId,
                taskId, userId, CommonConstant.DELETE_FLAG_FALSE);
        if (!userRoomTaskInfo.getIsClickCancel()) {
            countCancel += 1;
            userRoomTaskInfo.setIsClickCompleted(CommonConstant.IS_CLICKED_FALSE);
            userRoomTaskInfo.setIsClickCancel(CommonConstant.IS_CLICKED_TRUE);
            userRoomTaskInfo.setCommonUpdate(userId);
            userRoomTaskInfoRepository.save(userRoomTaskInfo);

            roomsTask.setCountCancel(countCancel);
        }

        List<UserRoomInfo> userRoomInfoList = userRoomInfoRepository.findByRoomIdAndJoinStatusAndIsOldMemberAndDeleteFlag(roomId,
                CommonConstant.JOIN_STATUS_TRUE, CommonConstant.IS_OLD_MEMBER_TRUE, CommonConstant.DELETE_FLAG_FALSE);

        if (roomsTask.getCountCancel() < userRoomInfoList.size()) {
            response.put(CommonConstant.WARNING_MESSAGE, MessageUtils.CHOOSE_STATUS_ROOM_TASK);
        } else if (roomsTask.getCountCancel() == userRoomInfoList.size()) {
            userRoomInfoList.forEach(o -> o.setIsOldMember(CommonConstant.IS_OLD_MEMBER_FALSE));
            userRoomInfoRepository.saveAll(userRoomInfoList);

            roomsTask.setCountCompleted(0);
            roomsTask.setCountSubmit(0);
            roomsTask.setCountCancel(0);
            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.CANCEL_ROOM_TASK);
        }
        roomsTask.setCommonUpdate(userId);
        roomsTaskRepository.save(roomsTask);
        return response;
    }

    @Override
    public Map<String, Object> getRoomTaskMeetingDetail(Long roomId) {
        Rooms rooms = roomsRepository.findByIdAndDeleteFlag(roomId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        Long userId = commonService.idUserAccountLogin();
        Long softSkillId = rooms.getSoftskillId();

        Map<String, Object> response = new HashMap<>();
        RoomTaskMeetingResponse roomTaskMeeting = new RoomTaskMeetingResponse();
        TaskLatestMapping taskLatestMapping;
        boolean isRoomBooking = false;
        boolean isOldMember = false;
        long roomTaskId = 0;
        UserRoomInfo memberJoined = null;
        Integer statusTaskLatest = null;
        Integer countCancel = null;

        roomTaskMeeting.setIsRoomMaster(rooms.getCreatedBy().equals(userId));
        roomTaskMeeting.setIsLockRoom(rooms.getIsLock());
        roomTaskMeeting.setRoomId(roomId);
        roomTaskMeeting.setSoftSkillId(softSkillId);

        Optional<TaskLatestMapping> taskLatestOptional = tasksRepository.getTaskLatestByRoomId(roomId);
        if (taskLatestOptional.isPresent()) {
            taskLatestMapping = taskLatestOptional.get();
            statusTaskLatest = taskLatestMapping.getStatusTask();

            // là phòng booking -> đổi status sang DOING nếu status là NOT_START (chỉ đổi lần đầu tiên)
            if (taskLatestMapping.getStartTime() != null) {
                isRoomBooking = true;
                RoomsTask roomsTask = roomsTaskRepository.getByRoomIdAndTaskIdAndDeleteFlag(roomId, taskLatestMapping.getTaskId(), CommonConstant.DELETE_FLAG_FALSE);

                if (statusTaskLatest.equals(CommonConstant.STATUS_TASK_NOT_START)) {
                    roomsTask.setStatusId(CommonConstant.STATUS_TASK_DOING);
                    roomsTaskRepository.save(roomsTask);

                    statusTaskLatest = CommonConstant.STATUS_TASK_DOING;
                }
                roomTaskId = roomsTask.getId();
            }

            countCancel = taskLatestMapping.getCountCancel();
        }

        Optional<UserRoomInfo> memberJoinedOptional = userRoomInfoRepository.findByUserIdAndRoomIdAndDeleteFlag(userId, roomId, CommonConstant.DELETE_FLAG_FALSE);
        if (memberJoinedOptional.isPresent()) {
            memberJoined = memberJoinedOptional.get();
//            roomTaskMeeting.setIsOldMember(CommonConstant.IS_OLD_MEMBER_TRUE);
        }

        // isOldMember ? cập nhật join_status trong userRoomInfo : thêm dòng mới trong userRoomInfo
        if (memberJoined != null) {
            memberJoined.setJoinStatus(CommonConstant.JOIN_STATUS_TRUE);
            memberJoined.setCommonUpdate(userId);

            userRoomInfoRepository.save(memberJoined);
            roomTaskMeeting.setIsOldMember(memberJoined.getIsOldMember());
        } else {
            isOldMember = setNewMemberInRoom(roomId, roomTaskId, userId, isRoomBooking);
            roomTaskMeeting.setIsOldMember(isOldMember);
        }

        // list user doing in room
        List<UserRoomDetailMapping> usersRoom = roomsRepository.getUserRoomDetailByRoomId(roomId);
        roomTaskMeeting.setUsersRoom(usersRoom);

        // setListTask in RoomMeeting
        // trường hợp task cuối của room đã hoàn thành || chưa làm task nào || task cuối all member đều đã cancel-> hiển thị all task
        if (taskLatestOptional.isEmpty() || statusTaskLatest.equals(CommonConstant.STATUS_TASK_COMPLETED)) {
            TaskRequest taskRequest = new TaskRequest();
            taskRequest.setSoftSkillId(softSkillId);

            LocalDate localDate = LocalDate.now();
            List<Date> timestamps = CommonUtils.getStartAndEndDateInMonth(localDate.getMonthValue(), localDate.getYear());
            Date startTime = timestamps.get(0);
            Date endTime = timestamps.get(1);

            List<TasksMapping> allTasks =
                    tasksRepository.getTasksWithSoftSkillAndTimeAndSortByStatusGroup(softSkillId, startTime, endTime, CommonConstant.STATUS_TASK_DOING);

            roomTaskMeeting.setTasks(allTasks);
        }
        // trường hợp task cuối của room chưa hoàn thành hoặc tất cả chưa bấm bỏ -> hiển thị task gần nhất
        else if (statusTaskLatest.equals(CommonConstant.STATUS_TASK_DOING) || countCancel > 0) {
            TaskLatestInRoomResponse taskDetailsInRoomResponse = new TaskLatestInRoomResponse();

            TaskLatestInRoomMapping taskLatestDetail = tasksRepository.getTaskLatestDetailsMappingByRoomId(roomId, userId);
            taskDetailsInRoomResponse.setTaskLatestInRoomMapping(taskLatestDetail);
            taskDetailsInRoomResponse.setGuidelineTasks(guidelineTaskRepository.findByTaskIdAndDeleteFlagCustom(taskLatestDetail.getTaskId(), CommonConstant.DELETE_FLAG_FALSE));

            UserRoomTaskInfo userRoomTaskInfo = userRoomTaskInfoRepository.findByRoomIdAndTaskIdAndUserIdAndDeleteFlag(roomId,
                    taskLatestDetail.getTaskId(), userId, CommonConstant.DELETE_FLAG_FALSE);
//
//            Map<String, Object> checkAcceptByRoomMaster =
//                    acceptDoRoomTask(new RoomTaskActionRequest(taskLatestDetail.getTaskId(), roomId, rooms.getSoftskillId(), userId));

            if (isOldMember && userRoomTaskInfo == null) {
                setNewUserRoomTaskInfo(roomId, taskLatestDetail.getTaskId(), userId, CommonConstant.IS_CLICKED_FALSE, CommonConstant.IS_CLICKED_FALSE);

                roomTaskMeeting.setIsClickCompleted(CommonConstant.IS_CLICKED_FALSE);
                roomTaskMeeting.setIsClickCancel(CommonConstant.IS_CLICKED_FALSE);
            } else if (userRoomTaskInfo != null) {
                roomTaskMeeting.setIsClickCompleted(userRoomTaskInfo.getIsClickCompleted());
                roomTaskMeeting.setIsClickCancel(userRoomTaskInfo.getIsClickCancel());
            }
            roomTaskMeeting.setTaskDetails(taskDetailsInRoomResponse);
        }
        Timestamp now = CommonUtils.resultTimestamp();

        Statistic statistic = statisticRepository.getByUserIdAndCreatedDate(userId, CommonUtils.getDateNow());
        Long adminId = usersRepository.getAdminId(CommonConstant.ROLE_ADMIN);

        if (statistic == null) {
            addStatisticForUser(userId);
        } else {
            statistic.setJoinTime(now);
            statistic.setCommonUpdate(adminId);
            statisticRepository.save(statistic);
        }

        response.put("roomMeeting", roomTaskMeeting);
        return response;
    }

    @Override
    public Map<String, Object> acceptDoRoomTask(RoomTaskActionRequest request) {
        Map<String, Object> response = new HashMap<>();
        long roomId = request.getRoomId();
        long taskId = request.getTasksId();
        long userId = request.getUserId();

        // set lại status trong userRoomInfo: oldMember -> TRUE
        // add new userRoomTaskInfo
        UserRoomInfo userRoomInfo = userRoomInfoRepository.findByUserIdAndRoomIdAndDeleteFlag(userId, roomId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        userRoomInfo.setIsOldMember(CommonConstant.IS_OLD_MEMBER_TRUE);
        userRoomInfoRepository.save(userRoomInfo);

        setNewUserRoomTaskInfo(roomId, taskId, userId, CommonConstant.IS_CLICKED_FALSE, CommonConstant.IS_CLICKED_FALSE);
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.ACCEPT_USER_ROOM_TASK_SUCCESS);
        response.put("checkAccept", CommonConstant.IS_ACCEPT_DO_ROOM_TASK);

        return response;
    }

    private void setNewUserRoomTaskInfo(long roomId, long taskId, long userId, boolean submitCompleted, boolean submitCancel) {
//        UserRoomTaskInfo userRoomTaskInfo = new UserRoomTaskInfo();
        UserRoomTaskInfo userRoomTaskInfo = userRoomTaskInfoRepository.findByRoomIdAndTaskIdAndUserIdAndDeleteFlag(roomId, taskId, userId, CommonConstant.DELETE_FLAG_FALSE);

        if (userRoomTaskInfo == null) {
            userRoomTaskInfo = new UserRoomTaskInfo();
        } else {
            return;
        }

        userRoomTaskInfo.setRoomId(roomId);
        userRoomTaskInfo.setTaskId(taskId);
        userRoomTaskInfo.setUserId(userId);
        userRoomTaskInfo.setStatusTaskId(CommonConstant.STATUS_TASK_DOING);
        if (submitCompleted) {
            userRoomTaskInfo.setIsClickCompleted(CommonConstant.IS_CLICKED_TRUE);
        } else {
            userRoomTaskInfo.setIsClickCompleted(CommonConstant.IS_CLICKED_FALSE);
        }
        if (submitCancel) {
            userRoomTaskInfo.setIsClickCancel(CommonConstant.IS_CLICKED_TRUE);
        } else {
            userRoomTaskInfo.setIsClickCancel(CommonConstant.IS_CLICKED_FALSE);
        }

        userRoomTaskInfo.setCommonRegister(userId);

        userRoomTaskInfoRepository.save(userRoomTaskInfo);
    }

    private void addStatisticForUser(long userId) {
        Long adminId = usersRepository.getAdminId(CommonConstant.ROLE_ADMIN);
        Timestamp now = CommonUtils.resultTimestamp();
        Statistic statistic = new Statistic();
        statistic.setUserId(userId);
        statistic.setJoinTime(now);
        statistic.setCommonRegister(adminId);
        statistic.setCountTaskDoing(0);
        statistic.setCountTaskCompleted(0);
        statistic.setTaskCompletedTime((float) 0);
        statistic.setTimeRoom((float) 0);

        statisticRepository.save(statistic);
    }

    private void addNewUserTask(Long usersId, Long subTaskId) {
        List<UserTask> userTasks = new ArrayList<>();
        Optional<UserTask> userTask =
                userTaskRepository.findByUserIdAndTaskIdAndDeleteFlag(usersId, subTaskId, CommonConstant.DELETE_FLAG_FALSE);

        if (userTask.isEmpty()) {
            UserTask info = new UserTask();

            info.setTaskId(subTaskId);
            info.setUserId(usersId);
            info.setCommonRegister(usersId);
            info.setStatusId(CommonConstant.STATUS_TASK_DOING);

            userTasks.add(info);
        }

        if (userTasks.size() > 0) {
            userTaskRepository.saveAll(userTasks);
        }
    }

    private List<TasksMapping> sortTaskInRoomMeeting(List<TasksMapping> tasks) {
        return null;
    }

    private boolean setNewMemberInRoom(long roomId, long roomTaskId, long userId, boolean isRoomBooking) {
        UserRoomInfo userRoomInfo = new UserRoomInfo();
        RoomTaskRegisterInfo roomTaskRegisterInfo = null;

        userRoomInfo.setRoomId(roomId);
        userRoomInfo.setUserId(userId);
        userRoomInfo.setIsJoined(CommonConstant.IS_JOINED_TRUE);
        userRoomInfo.setJoinStatus(CommonConstant.JOIN_STATUS_TRUE);

        Optional<RoomTaskRegisterInfo> registerInfoOptional =
                roomTaskRegisterInfoRepository.findByRoomTaskIdAndCreatedByAndDeleteFlag(roomTaskId, userId, CommonConstant.DELETE_FLAG_FALSE);
        if (registerInfoOptional.isPresent()) {
            roomTaskRegisterInfo = registerInfoOptional.get();
        }

        // trong trường hợp là phòng đặt lịch và là người đã từng đăng kí phòng đó -> member khi vào phòng thì auto là oldMember
        if ((isRoomBooking && roomTaskRegisterInfo != null)) {
            userRoomInfo.setIsOldMember(CommonConstant.IS_OLD_MEMBER_TRUE);
        } else {
            userRoomInfo.setIsOldMember(CommonConstant.IS_OLD_MEMBER_FALSE);
        }

        userRoomInfo.setCommonRegister(userId);
        userRoomInfoRepository.save(userRoomInfo);

        return userRoomInfo.getIsOldMember();
    }

    private Map<String, Object> addCompletedTaskToStatistic(Timestamp startTimeTask, Timestamp timestampNow, Long userId) {
        Map<String, Object> response = new HashMap<>();
//        Timestamp startTimeTask = userRoomTask.getCreatedAt();
        float timeRoom = (float) timestampNow.getTime() - startTimeTask.getTime();
        float timeRoomFinal = timeRoom / (1000 * 60 * 60);

        Statistic statistic = statisticRepository.getByUserIdAndCreatedDate(userId, CommonUtils.getDateNow());
        statistic.setCountTaskDoing(statistic.getCountTaskDoing() - 1);
        statistic.setCountTaskCompleted(statistic.getCountTaskCompleted() + 1);
        statistic.setTaskCompletedTime(statistic.getTaskCompletedTime() + (float) Math.round(timeRoomFinal * 100) / 100);
        statistic.setCommonUpdate(usersRepository.getAdminId(CommonConstant.ROLE_ADMIN));
//        statisticRepository.save(statistic);
        response.put("statistic", statistic);

        List<RankInfo> rankInfos = rankInfoRepository.findAllByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
        List<RankInfo> rankInfo = rankInfos.stream()
                .filter(r -> r.getConditionTask().equals(statistic.getCountTaskCompleted()))
                .collect(Collectors.toList());

        if (rankInfo.size() > 0) {
            Users user = usersRepository.findByIdAndDeleteFlag(userId, CommonConstant.DELETE_FLAG_FALSE)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
            user.setRankId(rankInfo.get(0).getId());
            response.put("userRank", user);
        }

        return response;
    }

    @Override
    public Map<String, Object> outRoomMeeting(RoomTaskActionRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();

        Rooms rooms = roomsRepository.findByIdAndDeleteFlag(request.getRoomId(), CommonConstant.DELETE_FLAG_FALSE).get();

        UserRoomInfo userRoomInfo = userRoomInfoRepository.findByUserIdAndRoomIdAndJoinStatusAndDeleteFlag(userId, request.getRoomId(),
                        CommonConstant.JOIN_STATUS_TRUE, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        if (userId != rooms.getCreatedBy()) {
            userRoomInfo.setIsOldMember(CommonConstant.IS_OLD_MEMBER_FALSE);
        } else {
            userRoomInfo.setIsOldMember(CommonConstant.IS_OLD_MEMBER_TRUE);
        }
        userRoomInfo.setJoinStatus(CommonConstant.JOIN_STATUS_FALSE);
        if (userRoomInfo.getUserId() != userId) {
            userRoomInfo.setIsOldMember(CommonConstant.IS_OLD_MEMBER_FALSE);
        }
        userRoomInfo.setCommonUpdate(userId);
        userRoomInfoRepository.save(userRoomInfo);

        if (request.getTasksId() != null) {
            UserRoomTaskInfo userRoomTaskInfo = userRoomTaskInfoRepository.findByRoomIdAndTaskIdAndUserIdAndDeleteFlag(
                    request.getRoomId(), request.getTasksId(), userId, CommonConstant.DELETE_FLAG_FALSE);
            if (userRoomTaskInfo != null) {
                userRoomTaskInfo.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
                userRoomTaskInfoRepository.save(userRoomTaskInfo);
            }
        }

        Long adminId = usersRepository.getAdminId(CommonConstant.ROLE_ADMIN);
        Timestamp now = CommonUtils.resultTimestamp();

        Statistic statistic = statisticRepository.getByUserIdAndCreatedDate(userId, CommonUtils.getDateNow());

        Timestamp joinTime = statistic.getJoinTime();
        float timeRoom = (float) now.getTime() - joinTime.getTime();
        float timeRoomFinal = timeRoom / (1000 * 60 * 60);

        statistic.setTimeRoom(statistic.getTimeRoom() + (float) Math.round(timeRoomFinal * 100) / 100);
        statistic.setCommonUpdate(adminId);
        statisticRepository.save(statistic);

        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.OUT_ROOM_TASK);

        return response;
    }

    public void sendNotificationRegisterRoom(String roomName, long userId, int typeRegister, long startTime) {
        Map<String, Object> requestNotification = new HashMap<>();
        requestNotification.put("typeId", CommonConstant.NOTIFICATION_REGISTER_ROOM_TASK);
        requestNotification.put("typeRegisterRoom", typeRegister);
        requestNotification.put("roomName", roomName);
        requestNotification.put("userId", userId);
        requestNotification.put("senderId", usersRepository.getAdminId(CommonConstant.ROLE_ADMIN));
        requestNotification.put("startTime", startTime);
        notificationService.addNotification(requestNotification);
    }

    @Override
    public Map<String, Object> getUserRoomTaskInfo(RoomTaskActionRequest roomTasksRequest) {
        Map<String, Object> response = new HashMap<>();
        List<UserRoomTaskInfo> usersRoomTask = userRoomTaskInfoRepository.findByRoomIdAndTaskIdAndDeleteFlag(roomTasksRequest.getRoomId(),
                roomTasksRequest.getTasksId(), CommonConstant.DELETE_FLAG_FALSE);
        response.put("usersRoomTask", usersRoomTask);
        return response;
    }

    @Override
    public ApiResponse resetUserRoomTaskInfo(Long taskId) {
        Optional<UserRoomTaskInfo> usersRoomTask = userRoomTaskInfoRepository.findByTaskIdAndUserIdAndDeleteFlag(taskId,
                commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE);
        if (usersRoomTask.isEmpty()) {
            throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        usersRoomTask.get().setIsClickCancel(CommonConstant.IS_CLICKED_FALSE);
        usersRoomTask.get().setIsClickCompleted(CommonConstant.IS_CLICKED_FALSE);
        usersRoomTask.get().setCommonUpdate(commonService.idUserAccountLogin());
        userRoomTaskInfoRepository.save(usersRoomTask.get());
        return new ApiResponse(CommonConstant.HTTP_CODE_200,
                CommonConstant.SUCCESS, null);
    }
}
