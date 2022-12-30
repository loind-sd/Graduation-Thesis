package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.Notification;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.NotificationResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.NotificationMapping;
import fpt.lhlvb.softskillcommunity.repository.NotificationRepository;
import fpt.lhlvb.softskillcommunity.repository.NotificationTypeRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import fpt.lhlvb.softskillcommunity.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public ApiResponse getNotifications(int page) {
        List<NotificationMapping> notifications = notificationRepository.findNotification(
                commonService.idUserAccountLogin(),
                CommonConstant.DELETE_FLAG_FALSE,
                page * CommonConstant.SIZE_PER_PAGE);
        List<NotificationResponse> response = new ArrayList<>();
        for (NotificationMapping n : notifications) {
            response.add(new NotificationResponse(
                    n.getId(),
                    n.getContent(),
                    n.getStatus(),
                    n.getDelivered(),
                    n.getTimeCreated(),
                    n.getSenderId(),
                    n.getTypeId(),
                    n.getTypeName(),
                    n.getPicture()
            ));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public Map<String, Object> addNotification(Map<String, Object> req) {
        Map<String, Object> response = new HashMap<>();
        Long userId = commonService.idUserAccountLogin();
        long senderId = Long.parseLong(String.valueOf(req.get("senderId")));
        boolean check = true;

        Notification notification = new Notification();
        String typeId = String.valueOf(req.get("typeId"));
        notification.setNotificationType(notificationTypeRepository
                .findByIdAndDeleteFlag(Long.parseLong(typeId), CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND)));
        notification.setSenderId(senderId);
        //set content
        StringBuilder builder = new StringBuilder();
        if (CommonConstant.NOTIFICATION_ADD_FRIEND.equals(Integer.parseInt(typeId))) {
            Optional<Users> users = usersRepository.findByIdAndDeleteFlag(senderId, CommonConstant.DELETE_FLAG_FALSE);
            if (users.isPresent()) {
                builder.append(users.get().getName());
                builder.append(CommonConstant.FRIEND_REQUEST_NOTIFICATION);
                notification.setPicture(users.get().getPicture());
            }
        } else if (CommonConstant.NOTIFICATION_INVITE_ROOM.equals(Integer.parseInt(typeId))) {
            Optional<Notification> optional = notificationRepository.findBySenderIdAndUserIdAndDeleteFlagAndNotificationType(senderId, Long.parseLong(String.valueOf(req.get("userId"))), CommonConstant.DELETE_FLAG_FALSE, notificationTypeRepository.findByIdAndDeleteFlag(Long.parseLong(typeId), CommonConstant.DELETE_FLAG_FALSE).get());
            if (optional.isEmpty()) {
                Optional<Users> users = usersRepository.findByIdAndDeleteFlag(senderId, CommonConstant.DELETE_FLAG_FALSE);
                if (users.isPresent()) {
                    builder.append(users.get().getName());
                    builder.append(CommonConstant.ROOM_INVITE_NOTIFICATION);
                    builder.append(users.get().getGender() == null ? "họ" : users.get().getGender() == 0 ? "cô ấy" : "anh ấy").append(" ||| ").append(req.get("roomId"));
                    notification.setPicture(users.get().getPicture());
                }
            } else {
                response.put("error", "already send invite before");
                return response;
            }
        } else if (CommonConstant.NOTIFICATION_REGISTER_ROOM_TASK.equals(Integer.parseInt(typeId))) {
            check = false;
            int typeRegister = Integer.parseInt(String.valueOf(req.get("typeRegisterRoom")));
            if (typeRegister == 1) {
                builder.append(CommonConstant.NOTIFICATION_JOIN_ROOM_1);
                builder.append(req.get("roomName"));
            } else if (typeRegister == 2) {
                builder.append(CommonConstant.NOTIFICATION_JOIN_ROOM_2);
                builder.append(req.get("roomName"));
                builder.append(CommonConstant.NOTIFICATION_JOIN_ROOM_3);
            } else if (typeRegister == 3) {
                builder.append("Phòng ").append(req.get("roomName")).append(" bắt đầu công việc vào lúc ").append(req.get("bookingTime"));
                builder.append(CommonConstant.NOTIFICATION_JOIN_ROOM_4);
            }

            long startTime = Long.parseLong(String.valueOf(req.get("startTime")));
            notification.setCreatedBy(senderId);
            notification.setUpdatedBy(senderId);
            notification.setDeleteFlag(CommonConstant.DELETE_FLAG_FALSE);
            notification.setCreatedAt(new Timestamp(startTime));
            notification.setUpdatedAt(new Timestamp(startTime));
        } else if (CommonConstant.NOTIFICATION_ADD_NEW_CONTACT.equals(Integer.parseInt(typeId))) {
            Optional<Users> users = usersRepository.findByIdAndDeleteFlag(senderId, CommonConstant.DELETE_FLAG_FALSE);
            if (users.isPresent()) {
                builder.append("Bạn đã được ");
                builder.append(users.get().getName());
                builder.append(" thêm vào phòng chat ").append(req.get("roomName"));
                notification.setPicture(users.get().getPicture());
            }
        }
        notification.setContent(builder.toString());
        notification.setUserId(Long.parseLong(String.valueOf(req.get("userId"))));
        notification.setStatus(CommonConstant.JOIN_STATUS_FALSE);
        notification.setDelivered(CommonConstant.DELETE_FLAG_FALSE);

        if (check) {
            notification.setCommonRegister(userId);
        }
        notificationRepository.save(notification);
        response.put("addNotification", "done");
        return response;
    }

    @Override
    public Map<String, Object> updateNotificationStatus(Long id) {
        Notification notification = notificationRepository.findByIdAndDeleteFlag(id, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        return new HashMap<>(updateNotificationStatus(notification));
    }

    @Override
    public Map<String, Object> updateNotificationStatus(Notification notification) {
        Map<String, Object> response = new HashMap<>();
        notification.setStatus(CommonConstant.JOIN_STATUS_TRUE);
        if (notification.getNotificationType().getId().equals(Long.valueOf(CommonConstant.NOTIFICATION_ADD_FRIEND))) {
            notification.setContent(MessageUtils.ACCEPT_FRIEND);
        }
        notification.setCommonUpdate(commonService.idUserAccountLogin());
        Long idSave = notificationRepository.save(notification).getId();

        response.put("updateNotificationStatus", "done");
        response.put("id", idSave);
        return response;
    }

    @Override
    public ApiResponse deleteNotification(Long id) {
        Map<String, Object> response = new HashMap<>();

        Notification notification = notificationRepository.findByIdAndDeleteFlag(id, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));

        notification.setCommonDelete(commonService.idUserAccountLogin());
        Long idSave = notificationRepository.save(notification).getId();

        response.put("deleteNotification", "done");
        response.put("id", idSave);
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    private List<Notification> getNotify(Long userID) {
        List<Notification> getNotify = notificationRepository.findForFlux(userID);
        getNotify.forEach(x -> x.setDelivered(true));
        notificationRepository.saveAll(getNotify);
        return getNotify;
    }

    @Override
    public Flux<ServerSentEvent<List<Notification>>> getNotificationsByUserToID(Long id) {
        if (id != null) {
            return Flux.interval(Duration.ofSeconds(5))
                    .onBackpressureDrop()
                    .publishOn(Schedulers.boundedElastic())
                    .map(sequence -> ServerSentEvent.<List<Notification>>builder()
                            .id(String.valueOf(sequence))
                            .event("user-list-event")
                            .data(getNotify(id))
                            .build());
        }
        return Flux.interval(Duration.ofSeconds(5)).map(sequence -> ServerSentEvent.<List<Notification>>builder()
                .id(String.valueOf(sequence)).event("user-list-event").data(new ArrayList<>()).build());
    }
}
