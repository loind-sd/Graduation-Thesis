package fpt.lhlvb.softskillcommunity.service.impl;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.common.CommonService;
import fpt.lhlvb.softskillcommunity.entity.FriendRelation;
import fpt.lhlvb.softskillcommunity.entity.Notification;
import fpt.lhlvb.softskillcommunity.entity.Users;
import fpt.lhlvb.softskillcommunity.exception.RecordNotFoundException;
import fpt.lhlvb.softskillcommunity.model.response.ApiResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.FriendResponse;
import fpt.lhlvb.softskillcommunity.model.response.user.mapping.FriendMapping;
import fpt.lhlvb.softskillcommunity.repository.FriendRelationRepository;
import fpt.lhlvb.softskillcommunity.repository.NotificationRepository;
import fpt.lhlvb.softskillcommunity.repository.NotificationTypeRepository;
import fpt.lhlvb.softskillcommunity.repository.UsersRepository;
import fpt.lhlvb.softskillcommunity.service.FriendRelationService;
import fpt.lhlvb.softskillcommunity.service.NotificationService;
import fpt.lhlvb.softskillcommunity.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class FriendRelationServiceImpl implements FriendRelationService {

    @Autowired
    private FriendRelationRepository friendRelationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CommonService commonService;

    @Override
    public ApiResponse getAllFriend() {
        Long currentLogin = commonService.idUserAccountLogin();
        List<FriendResponse> response = new ArrayList<>();
        List<FriendMapping> mappings = friendRelationRepository.getAllFriend(
                CommonConstant.STATUS_1,
                CommonConstant.DELETE_FLAG_FALSE,
                currentLogin);

        for (FriendMapping u : mappings) {
            response.add(new FriendResponse(u.getId(), u.getName(), u.getNickName(), u.getPicture(), u.getStatus()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse getAllFriendEvenRequest() {
        List<FriendResponse> response = new ArrayList<>();
        List<FriendMapping> mappings = friendRelationRepository.getAllFriendEvenRequest(commonService.idUserAccountLogin());

        for (FriendMapping u : mappings) {
            response.add(new FriendResponse(u.getId(), u.getName(), u.getNickName(), u.getPicture()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse addFriend(Long id) {
        Map<String, Object> response = new HashMap<>();

        if (!friendRelationRepository.checkExistAddFriend(commonService.idUserAccountLogin(), id)) {
            FriendRelation friendRelation = new FriendRelation();
            friendRelation.setUserSentId(commonService.idUserAccountLogin());
            friendRelation.setUserRecivedId(id);
            friendRelation.setStatus(CommonConstant.STATUS_0);
            friendRelation.setCommonRegister(commonService.idUserAccountLogin());

            Long idSave = friendRelationRepository.save(friendRelation).getId();
            response.put("addFriendRequest", "done");
            response.put("friendRelationId", idSave);

            Map<String, Object> request = new HashMap<>();
            request.put("typeId", CommonConstant.STATUS_1);
            request.put("userId", id);
            request.put("senderId", commonService.idUserAccountLogin());
            response.putAll(notificationService.addNotification(request));
        }

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse actionFriend(@RequestBody Map<String, Object> req) {
        FriendRelation relation = friendRelationRepository.findByUserSentIdAndUserRecivedIdAndStatusAndDeleteFlag(Long.parseLong(String.valueOf(req.get("id"))), commonService.idUserAccountLogin(), CommonConstant.STATUS_0, CommonConstant.DELETE_FLAG_FALSE)
                .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND));
        Map<String, Object> response = new HashMap<>();
        if (CommonConstant.ACTION_ACCEPT.equals(String.valueOf(req.get("action")))) {
            relation.setStatus(CommonConstant.STATUS_1);
            relation.setCommonUpdate(commonService.idUserAccountLogin());
            Notification notification = notificationRepository.findBySenderIdAndUserIdAndStatusAndDeleteFlagAndNotificationType(relation.getUserSentId(), relation.getUserRecivedId(), CommonConstant.JOIN_STATUS_FALSE, CommonConstant.DELETE_FLAG_FALSE, notificationTypeRepository.findByIdAndDeleteFlag(Long.valueOf(CommonConstant.NOTIFICATION_ADD_FRIEND), CommonConstant.DELETE_FLAG_FALSE).get()).get();
            response = new HashMap<>(notificationService.updateNotificationStatus(notification));

            notification.setStatus(CommonConstant.JOIN_STATUS_TRUE);
            notification.setContent("Bạn và " + usersRepository.findByIdAndDeleteFlag(Long.parseLong(String.valueOf(req.get("id"))), CommonConstant.DELETE_FLAG_FALSE).get().getName() + MessageUtils.ACCEPT_FRIEND);
            notification.setCommonUpdate(commonService.idUserAccountLogin());
            notificationRepository.save(notification);

            // add new response notification
            Notification noti = new Notification();
            Users users = usersRepository.findByIdAndDeleteFlag(commonService.idUserAccountLogin(), CommonConstant.DELETE_FLAG_FALSE).get();
            noti.setNotificationType(notificationTypeRepository
                    .findByIdAndDeleteFlag(Long.valueOf(CommonConstant.NUMBER_1), CommonConstant.DELETE_FLAG_FALSE)
                    .orElseThrow(() -> new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND)));
            noti.setSenderId(commonService.idUserAccountLogin());
            noti.setContent(users.getName() + " đã chấp nhận lời mời kết bạn của bạn");
            noti.setUserId(Long.parseLong(String.valueOf(req.get("id"))));
            noti.setStatus(CommonConstant.JOIN_STATUS_TRUE);
            noti.setPicture(users.getPicture());
            noti.setDelivered(CommonConstant.DELETE_FLAG_FALSE);
            noti.setCommonRegister(commonService.idUserAccountLogin());
            notificationRepository.save(noti);
        } else if (CommonConstant.ACTION_DENY.equals(String.valueOf(req.get("action")))) {
            relation.setDeleteFlag(CommonConstant.DELETE_FLAG_TRUE);
            relation.setCommonUpdate(commonService.idUserAccountLogin());

            Notification notification = notificationRepository.findBySenderIdAndUserIdAndStatusAndDeleteFlagAndNotificationType(relation.getUserSentId(), relation.getUserRecivedId(), CommonConstant.JOIN_STATUS_FALSE, CommonConstant.DELETE_FLAG_FALSE, notificationTypeRepository.findByIdAndDeleteFlag(Long.valueOf(CommonConstant.NOTIFICATION_ADD_FRIEND), CommonConstant.DELETE_FLAG_FALSE).get()).get();
            notification.setStatus(CommonConstant.JOIN_STATUS_TRUE);
            notification.setContent(MessageUtils.DENY_FRIEND);
            notification.setCommonDelete(commonService.idUserAccountLogin());
            notificationRepository.save(notification);
        }
        friendRelationRepository.save(relation);
        response.put("actionFriend", "done");
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.CREATE_SUCCESS, response);
    }

    @Override
    public ApiResponse searchToAddFriend(String search) {
        List<FriendMapping> mapping = friendRelationRepository.searchToAddFriend(CommonConstant.DELETE_FLAG_FALSE, search, commonService.idUserAccountLogin());
        List<FriendResponse> response = new ArrayList<>();

        for (FriendMapping m : mapping) {
            response.add(new FriendResponse(m.getId(), m.getName(), m.getNickName(), m.getPicture(), m.getStatus()));
        }
        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.SUCCESS, response);
    }

    @Override
    public ApiResponse deleteFriend(Long id) {
        Optional<FriendRelation> friendRelation = friendRelationRepository
                .getFriendRelationById(commonService.idUserAccountLogin(), id);
        if (friendRelation.isEmpty()){
            throw  new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
        }
        friendRelation.get().setStatus(0);
        friendRelation.get().setDeleteFlag(true);
        friendRelation.get().setCommonDelete(commonService.idUserAccountLogin());
        friendRelationRepository.save(friendRelation.get());

        // remove notification
        Optional<Notification> notification = notificationRepository.findBySenderIdAndUserIdAndDeleteFlagAndNotificationType(commonService.idUserAccountLogin(), id, CommonConstant.JOIN_STATUS_FALSE, notificationTypeRepository.findByIdAndDeleteFlag(Long.valueOf(CommonConstant.NOTIFICATION_ADD_FRIEND), CommonConstant.DELETE_FLAG_FALSE).get());
        if (notification.isEmpty()) {
            notification = notificationRepository.findBySenderIdAndUserIdAndDeleteFlagAndNotificationType(id, commonService.idUserAccountLogin(), CommonConstant.JOIN_STATUS_FALSE, notificationTypeRepository.findByIdAndDeleteFlag(Long.valueOf(CommonConstant.NOTIFICATION_ADD_FRIEND), CommonConstant.DELETE_FLAG_FALSE).get());
            if (notification.isEmpty()) {
                throw new RecordNotFoundException(CommonConstant.RECORD_NOT_FOUND);
            } else {
                notification.get().setCommonDelete(commonService.idUserAccountLogin());
            }
        } else {
            notification.get().setCommonDelete(commonService.idUserAccountLogin());
        }
        notificationRepository.save(notification.get());

        return new ApiResponse(CommonConstant.HTTP_CODE_200, CommonConstant.DELETE_SUCCESS, null);
    }
}
