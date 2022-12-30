package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.*;
import fpt.lhlvb.softskillcommunity.exception.DuplicateKeyInDBException;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.request.admin.DeleteRoomRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.DeleteRoomUserRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomRequest;
import fpt.lhlvb.softskillcommunity.model.request.user.RoomSearchRequest;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.RoomAddResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.RoomDetailResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RoomDetailMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.RoomsMapping;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.UserRoomDetailMapping;
import fpt.lhlvb.softskillcommunity.repository.*;
import fpt.lhlvb.softskillcommunity.service.NotificationService;
import fpt.lhlvb.softskillcommunity.service.RoomsService;
import fpt.lhlvb.softskillcommunity.utils.CommonUtils;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomsServiceImpl implements RoomsService {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private RoomsTaskRepository roomsTaskRepository;

    @Autowired
    private RoomTaskServiceImpl roomTaskService;

    @Autowired
    private RoomTaskRegisterInfoRepository roomTaskRegisterInfoRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SoftSkillsRepository softSkillsRepository;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private UserRoomInfoRepository userRoomInfoRepository;

    DateFormat sdf = new SimpleDateFormat(CommonConstant.DATE_FORMAT_YYYY_MM_DD);

    @Override
    public RoomDetailResponse getRoomDetailsByRoomId(Long roomId) {
        RoomDetailResponse roomDetailResponse = new RoomDetailResponse();

        RoomDetailMapping roomDetailMapping = roomsRepository.getRoomDetailsByRoomId(roomId);
        List<UserRoomDetailMapping> listUsersRoom = roomsRepository.getUserRoomDetailByRoomId(roomId);

        roomDetailResponse.setRoomDetail(roomDetailMapping);
        roomDetailResponse.setUserRoomDetails(listUsersRoom);

        return roomDetailResponse;
    }

    private Boolean checkDuplicateRoomName(String roomName) {
        Optional<Rooms> roomOptional = roomsRepository.findByNameAndDeleteFlag(roomName, CommonConstant.DELETE_FLAG_FALSE);

        return roomOptional.isEmpty();
    }

    private String validateRoomRequest(RoomRequest roomReq) {
        Integer roomSize = roomReq.getRoomSize();
        String roomName = roomReq.getRoomName();
        Long softSkillId = roomReq.getSoftSkillId();
        Long taskId = roomReq.getTaskId();
        String startTime = roomReq.getStartTime();
        String description = roomReq.getDescription();

        if (roomName.length() > CommonConstant.MAX_ROOM_NAME) {
            return String.format(MessageUtils.ERROR_STRING_LENGTH, "Tên phòng", CommonConstant.MAX_ROOM_NAME);
        } else if (description == null) {
            return String.format(MessageUtils.ERROR_NULL, "mô tả phòng");
        } else if (roomSize == null || roomSize < CommonConstant.MIN_ROOM_SIZE || roomSize > CommonConstant.MAX_ROOM_SIZE) {
            return MessageUtils.ERROR_ROOM_SIZE;
        } else if (softSkillId == null) {
            return String.format(MessageUtils.ERROR_NULL, "kỹ năng");
        } else if (startTime != null) {
            try {
                sdf.setLenient(false);
                sdf.parse(startTime);
            } catch (ParseException e) {
//                logger.info(CommonConstant.ERROR_MESSAGE + " - " + String.format(MessageUtils.ERROR_DATE, "đặt lịch"));
                return String.format(MessageUtils.ERROR_DATE, "đặt lịch");
            }
            if (taskId == null) {
                return String.format(MessageUtils.ERROR_NULL, "nhiệm vụ");
            }
        }

        return null;
    }

    @Override
    public Map<String, Object> addRoom(RoomRequest roomReq) {
        Map<String, Object> response = new HashMap<>();
        Long softSkillId = roomReq.getSoftSkillId();
        String roomName = roomReq.getRoomName();
        Long taskId = roomReq.getTaskId();

        if (!checkDuplicateRoomName(roomName)) {
            response.put(CommonConstant.ERROR_MESSAGE, String.format(MessageUtils.ERROR_DUPLICATE, "tên phòng"));
            throw new RecordNotFoundException(CommonConstant.RECORD_ALREADY_EXISTS);
        }

        String messageValid = validateRoomRequest(roomReq);
        if (messageValid != null) {
            response.put(CommonConstant.ERROR_MESSAGE, messageValid);
            throw new RecordNotFoundException(CommonConstant.ERROR_MESSAGE);
        }

        softSkillsRepository.findByIdAndDeleteFlag(softSkillId, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        Long userId = commonService.idUserAccountLogin();
        // add to rooms
        Rooms room = new Rooms();
        room.setRoomCode("room" + CommonUtils.randomString(5));
        room.setName(roomName);
        room.setDescription(StringUtils.trimToEmpty(roomReq.getDescription()));

        room.setRoomSize(roomReq.getRoomSize());
        room.setSoftskillId(roomReq.getSoftSkillId());

        room.setIsLock(CommonConstant.ROOM_LOCK_FALSE);
        room.setCommonRegister(userId);

        long roomId = roomsRepository.save(room).getId();
        UserRoomInfo userRoomInfo = new UserRoomInfo();
        userRoomInfo.setRoomId(roomId);
        userRoomInfo.setUserId(userId);
        userRoomInfo.setJoinStatus(CommonConstant.JOIN_STATUS_TRUE);
        userRoomInfo.setIsJoined(CommonConstant.IS_JOINED_TRUE);
        userRoomInfo.setIsOldMember(CommonConstant.IS_OLD_MEMBER_TRUE);
        userRoomInfo.setCommonRegister(userId);
        userRoomInfoRepository.save(userRoomInfo);

        RoomAddResponse roomAddResponse = new RoomAddResponse(
                roomId, room.getSoftskillId(), null, room.getCreatedBy(), room.getName(),room.getRoomCode());
        // add to room_task
//        Timestamp timestampStart = new Timestamp(System.currentTimeMillis());
        if (taskId != null) {
            tasksRepository.findByIdAndDeleteFlag(taskId, CommonConstant.DELETE_FLAG_FALSE)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
            RoomsTask roomsTask = new RoomsTask();
            roomsTask.setRoomId(roomId);
            roomsTask.setTaskId(taskId);
            roomsTask.setSoftSkillId(softSkillId);
            roomsTask.setTaskNo(1);
            roomsTask.setStatusId(CommonConstant.STATUS_TASK_NOT_START);
            roomsTask.setCountSubmit(0);
            roomsTask.setCountCancel(0);
            roomsTask.setCountCompleted(0);
            // start time
            if (StringUtils.trimToNull(roomReq.getStartTime()) != null) {
                Timestamp startTime = CommonUtils.stringToTimestamp(roomReq.getStartTime());
                roomsTask.setStartTime(startTime);
            }
            roomsTask.setCommonRegister(userId);
            long roomTaskId = roomsTaskRepository.save(roomsTask).getId();

            // add to room_task_register
            RoomTaskRegisterInfo roomTaskRegisterInfo = new RoomTaskRegisterInfo();
            roomTaskRegisterInfo.setRoomTaskId(roomTaskId);
            roomTaskRegisterInfo.setCommonRegister(userId);
            roomTaskRegisterInfoRepository.save(roomTaskRegisterInfo);

            roomTaskService.sendNotificationRegisterRoom(roomName, userId,
                    1, roomsTask.getStartTime().getTime() - (1000 * 60 * 30));
            roomTaskService.sendNotificationRegisterRoom(roomName, userId,
                    2, roomsTask.getStartTime().getTime());
        }
        response.put("room", roomAddResponse);     // for unit test
        response.put("taskId", taskId);     // for unit test
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.CREATE_ROOM_SUCCESS);
        return response;
    }

    @Override
    public List<Long> addNewRoomBooking() {
        Map<String, Object> room = addRoom(new RoomRequest(
                CommonUtils.randomString(6),
                "Phòng dành cho giới trẻ",
                Long.parseLong("1"),
                10,
                "2022-10-10",
                Long.parseLong("1")));

        return new ArrayList<>() {
            {
                add(Long.parseLong(room.get("roomId").toString()));
                add(Long.parseLong(room.get("taskId").toString()));
            }
        };
    }

    @Override
    public Map<String, Object> updateRoom(RoomRequest roomRequest) {
        Map<String, Object> response = new HashMap<>();
        Rooms room = roomsRepository.findByIdAndDeleteFlag(roomRequest.getRoomId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        Integer roomSize = roomRequest.getRoomSize();
        if (roomSize == null || roomSize < CommonConstant.MIN_ROOM_SIZE || roomSize > CommonConstant.MAX_ROOM_SIZE) {
            response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.ERROR_ROOM_SIZE);
            return response;
        }
        room.setName(roomRequest.getRoomName() == null ? room.getName() : roomRequest.getRoomName());
        room.setRoomSize(roomSize);
        room.setDescription(roomRequest.getDescription() == null ? room.getDescription() : roomRequest.getDescription());
        room.setPassword(roomRequest.getPassword() == null ? room.getPassword() : roomRequest.getPassword());
        room.setCommonUpdate(commonService.idUserAccountLogin());

        roomsRepository.save(room);
        response.put("roomUpdated", room);
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.UPDATE_ROOM_SUCCESS);
        return response;
    }

    @Override
    public Map<String, Object> deleteRoom(DeleteRoomRequest request) {
        Map<String, Object> response = new HashMap<>();
        Rooms room = roomsRepository.findByIdAndDeleteFlag(request.getRoomId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        Long adminId = commonService.idUserAccountLogin();
        room.setCommonDelete(adminId);
        roomsRepository.save(room);

        if (request.getDeleteRoomMaster()) {
            Long createBy = room.getCreatedBy();

            Users user = usersRepository.findByIdAndDeleteFlag(createBy, CommonConstant.DELETE_FLAG_FALSE)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

            user.setCommonDelete(createBy);
        }
        response.put("deleteRoom: ", request.getRoomId());
        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.DELETE_ROOM);

        return response;
    }

    @Override
    public Map<String, Object> getRoomsBookingBySoftSkillId(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        Long softSkillId = request.getSoftSkillId();
        List<RoomsMapping> roomsBooking = roomsRepository.getRoomsBookingCommunity(userId);

        if (roomsBooking.size() == 0) {
            response.put(CommonConstant.WARNING_MESSAGE, MessageUtils.NOT_ROUND_ROOM_BY_SOFT_SKILL_ID + softSkillId);
        } else {
            response.put("rooms", sortListRoomsMapping(roomsBooking, CommonConstant.ROOM_TYPE_BOOKING));
        }

        return response;
    }

    @Override
    public Map<String, Object> getRoomsActiveBySoftSkillId(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        Long softSkillId = request.getSoftSkillId();
        List<RoomsMapping> roomsActive = roomsRepository.getRoomsActive(softSkillId, userId, null, null);

        if (roomsActive.size() == 0) {
            response.put(CommonConstant.WARNING_MESSAGE, MessageUtils.NOT_ROUND_ROOM_BY_SOFT_SKILL_ID + softSkillId);
        } else {
            response.put("rooms", sortListRoomsMapping(roomsActive, CommonConstant.ROOM_TYPE_ACTIVE));
        }

        return response;
    }

    @Override
    public Map<String, Object> getRoomsActiveForChat(Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        response.put("rooms", roomsRepository.getRoomsActiveForChat(Long.parseLong(String.valueOf(request.get("roomId")))));
        return response;
    }

    @Override
    public Map<String, Object> getRoomsBookingByCreateBy(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long softSkillId = request.getSoftSkillId();
        Long userId = commonService.idUserAccountLogin();
        List<RoomsMapping> roomsBooking = roomsRepository.getRoomsBooking(softSkillId, null, userId, null);

        if (roomsBooking.size() == 0) {
            response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.NOT_CREATED_BOOKING_ROOM);
        } else {
            response.put("rooms", sortListRoomsMapping(roomsBooking, CommonConstant.ROOM_TYPE_BOOKING));
        }

        return response;
    }

    @Override
    public Map<String, Object> getRoomsActiveByCreateBy(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long softSkillId = request.getSoftSkillId();
        Long userId = commonService.idUserAccountLogin();
        List<RoomsMapping> roomsActive = roomsRepository.getRoomsActive(softSkillId, null, userId, null);

        if (roomsActive.size() == 0) {
            response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.NOT_CREATED_ROOM);
        } else {
            response.put("rooms", sortListRoomsMapping(roomsActive, CommonConstant.ROOM_TYPE_ACTIVE));
        }

        return response;
    }

    @Override
    public Map<String, Object> getRoomsBookingByIsJoined(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long softSkillId = request.getSoftSkillId();
        Long userId = commonService.idUserAccountLogin();
        List<RoomsMapping> roomsBooking = roomsRepository.getRoomsBooking(softSkillId, userId, null, userId);

        if (roomsBooking.size() == 0) {
            response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.NOT_REGISTER_ROOM);
        } else {
            response.put("rooms", sortListRoomsMapping(roomsBooking, CommonConstant.ROOM_TYPE_BOOKING));
        }

        return response;
    }

    @Override
    public Map<String, Object> getRoomsActiveByIsJoined(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        Long softSkillId = request.getSoftSkillId();
        Long userId = commonService.idUserAccountLogin();
        List<RoomsMapping> roomsActive = roomsRepository.getActiveRoomIsJoined(userId);

        if (roomsActive.size() == 0) {
            response.put(CommonConstant.ERROR_MESSAGE, MessageUtils.NOT_JOINED_ROOM);
        } else {
            response.put("rooms", sortListRoomsMapping(roomsActive, CommonConstant.ROOM_TYPE_ACTIVE));
        }

        return response;
    }

    @Override
    public Map<String, Object> searchAndFilterForActiveRoom(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        List<RoomsMapping> rooms = commonSearchRoom(request, false);

        if (rooms == null || rooms.size() == 0) {
            response.put(CommonConstant.WARNING_MESSAGE, MessageUtils.SEARCH_AND_FILTER_ROOM_ERROR);
        } else {
            response.put("rooms", sortListRoomsMapping(rooms, CommonConstant.ROOM_TYPE_ACTIVE));
            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.SEARCH_AND_FILTER_ROOM_SUCCESS_1
                    + rooms.size()
                    + MessageUtils.SEARCH_AND_FILTER_ROOM_SUCCESS_2);
        }
        return response;
    }

    private List<RoomsMapping> commonSearchRoom(RoomSearchRequest request, boolean booking) {
        String textSearch = request.getTextSearch();
        String txtFinal = textSearch == null ? "" : textSearch.toLowerCase();
        Integer roomSizeFrom = request.getRoomSizeFrom();
        Integer roomSizeTo = request.getRoomSizeTo();

        Integer fromFinal = roomSizeFrom == null ? CommonConstant.MIN_ROOM_SIZE : roomSizeFrom;
        Integer toFinal = roomSizeTo == null ? CommonConstant.MAX_ROOM_SIZE : roomSizeTo;

        if (booking) {
            return roomsRepository.searchAndFilterForActiveRooom(txtFinal, fromFinal, toFinal);
        }
        return roomsRepository.searchAndFilterForBookingRooom(txtFinal, fromFinal, toFinal,
                CommonUtils.stringToTimestamp(request.getFromTime()), CommonUtils.stringToTimestamp(request.getToTime()));
    }

    @Override
    public Map<String, Object> searchAndFilterForBookingRoom(RoomSearchRequest request) {
        Map<String, Object> response = new HashMap<>();
        List<RoomsMapping> rooms = commonSearchRoom(request, true);

        if (rooms == null || rooms.size() == 0) {
            response.put(CommonConstant.WARNING_MESSAGE, MessageUtils.SEARCH_AND_FILTER_ROOM_ERROR);
        } else {
            response.put("rooms", sortListRoomsMapping(rooms, CommonConstant.ROOM_TYPE_BOOKING));
            response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.SEARCH_AND_FILTER_ROOM_SUCCESS_1
                    + rooms.size()
                    + MessageUtils.SEARCH_AND_FILTER_ROOM_SUCCESS_2);
        }
        return response;
    }

    @Override
    public Map<String, Object> userDeleteRoomBooking(DeleteRoomUserRequest request) {
        Map<String, Object> response = new HashMap<>();
        Rooms rooms = roomsRepository.findByIdAndDeleteFlag(request.getRoomId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        String roomName = rooms.getName();

        RoomsTask roomsTask = roomsTaskRepository.findByRoomIdAndTaskIdAndDeleteFlag(request.getRoomId(),
                        request.getTaskId(), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        Long userId = commonService.idUserAccountLogin();

        List<RoomTaskRegisterInfo> registerInfos = roomTaskRegisterInfoRepository.findByRoomTaskIdAndDeleteFlag(roomsTask.getId(),
                CommonConstant.DELETE_FLAG_FALSE);

        for (RoomTaskRegisterInfo roomTaskRegisterInfo : registerInfos) {
            Long receiverId = roomTaskRegisterInfo.getCreatedBy();

            if (!receiverId.equals(userId)) {
                Map<String, Object> requestNotification = new HashMap<>();

                requestNotification.put("typeId", CommonConstant.NOTIFICATION_REGISTER_ROOM_TASK);
                requestNotification.put("typeRegisterRoom", 3);
                requestNotification.put("roomName", roomName);
                requestNotification.put("userId", receiverId);
                requestNotification.put("senderId", usersRepository.getAdminId(CommonConstant.ROLE_ADMIN));
                requestNotification.put("bookingTime", roomsTask.getStartTime());
                requestNotification.put("startTime", CommonUtils.resultTimestamp().getTime());

                notificationService.addNotification(requestNotification);
            }
        }

        rooms.setCommonDelete(userId);
        roomsRepository.save(rooms);
        registerInfos.forEach(o -> o.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE));
        roomTaskRegisterInfoRepository.saveAll(registerInfos);

        response.put(CommonConstant.SUCCESS_MESSAGE, MessageUtils.DELETE_BOOKING_ROOM);
        return response;
    }

    @Override
    public ApiResponse getRooms() {
        Map<String, Object> response = new HashMap<>();
        response.put("rooms", roomsRepository.getRoomsByDeleteFlag(CommonConstant.DELETE_FLAG_FALSE));

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    private List<RoomsMapping> sortListRoomsMapping(List<RoomsMapping> rooms, Integer type) {
        List<RoomsMapping> roomsFinal = new ArrayList<>();
        List<RoomsMapping> roomsMaxSize, subRooms;
        // Active
        if (Objects.equals(type, CommonConstant.ROOM_TYPE_ACTIVE)) {
            roomsMaxSize = rooms.stream()
                    .filter(r -> r.getCountUserOnline() / r.getRoomSize() == 1)
                    .collect(Collectors.toList());
            subRooms = rooms.stream()
                    .filter(r -> !roomsMaxSize.contains(r))
                    .sorted((o1, o2) -> o2.getCountUserOnline() - o1.getCountUserOnline())
                    .collect(Collectors.toList());
        }
        // booking
        else {
            roomsMaxSize = rooms.stream()
                    .filter(r -> r.getCountUserRegister() / r.getRoomSize() == 1)
                    .collect(Collectors.toList());
            subRooms = rooms.stream()
                    .filter(r -> !roomsMaxSize.contains(r))
                    .sorted((o1, o2) -> o2.getCountUserRegister() - o1.getCountUserRegister())
                    .collect(Collectors.toList());
        }
        roomsFinal.addAll(subRooms);
        roomsFinal.addAll(roomsMaxSize);

        return roomsFinal;
    }
}
